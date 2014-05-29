package old.de.netprojectev.server.networking;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import de.netprojectev.server.ConstantsServer;

public class VideoFileReceiveHandler {

	private class MessageCounter {

		private final int maxMsgs;
		private int msgsReceived;

		MessageCounter(int maxMsgs) {
			this.maxMsgs = maxMsgs;
		}

		void msgReceived() throws ToManyMessagesException {
			if (this.maxMsgs == this.msgsReceived) {
				throw new ToManyMessagesException("Received more messages than actually calculated");
			}
			this.msgsReceived++;
		}

	}

	public class ToManyMessagesException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1724163896636427295L;

		public ToManyMessagesException(String cause) {
			super(cause);
		}

	}

	private final ArrayList<UUID> receivingFiles;

	private final HashMap<UUID, BufferedOutputStream> outputstreams;

	private final HashMap<UUID, MessageCounter> msgCounter;

	public VideoFileReceiveHandler() {
		this.receivingFiles = new ArrayList<UUID>();
		this.outputstreams = new HashMap<UUID, BufferedOutputStream>();
		this.msgCounter = new HashMap<UUID, MessageCounter>();

	}

	public void finishingReceivingVideo(UUID id) throws IOException {
		this.receivingFiles.remove(id);
		this.msgCounter.remove(id);
		this.outputstreams.get(id).flush();
		this.outputstreams.get(id).close();
		this.outputstreams.remove(id);

	}

	public void receiveData(UUID id, byte[] toWrite) throws IOException, ToManyMessagesException {
		this.msgCounter.get(id).msgReceived();
		this.outputstreams.get(id).write(toWrite);

	}

	public void startingReceivingVideo(UUID id, int maxMsgCount) throws FileNotFoundException {
		this.receivingFiles.add(id);
		this.msgCounter.put(id, new MessageCounter(maxMsgCount));
		FileOutputStream out = new FileOutputStream(new File(ConstantsServer.SAVE_PATH
				+ ConstantsServer.CACHE_PATH_VIDEOS + id.toString()));
		BufferedOutputStream bufOut = new BufferedOutputStream(out);
		this.outputstreams.put(id, bufOut);

	}
}
