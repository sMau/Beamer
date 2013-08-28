package de.netprojectev.server.networking;

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

	private final ArrayList<UUID> receivingFiles;
	private final HashMap<UUID, BufferedOutputStream> outputstreams;
	private final HashMap<UUID, MessageCounter> msgCounter;

	// TODO check for correct path for writing, maybe add a separate video cache
	// folder
	public VideoFileReceiveHandler() {
		receivingFiles = new ArrayList<>();
		outputstreams = new HashMap<>();
		msgCounter = new HashMap<>();

	}

	public void startingReceivingVideo(UUID id, int maxMsgCount) throws FileNotFoundException {
		receivingFiles.add(id);
		msgCounter.put(id, new MessageCounter(maxMsgCount));
		FileOutputStream out = new FileOutputStream(new File(ConstantsServer.SAVE_PATH
				+ ConstantsServer.CACHE_PATH + ConstantsServer.CACHE_PATH_VIDEOS + id.toString()));
		BufferedOutputStream bufOut = new BufferedOutputStream(out);
		outputstreams.put(id, bufOut);

	}

	public void finishingReceivingVideo(UUID id) throws IOException {
		receivingFiles.remove(id);
		msgCounter.remove(id);
		outputstreams.get(id).flush();
		outputstreams.get(id).close();
		outputstreams.remove(id);

	}

	public void receiveData(UUID id, byte[] toWrite) throws IOException, ToManyMessagesException {
		msgCounter.get(id).msgReceived();
		outputstreams.get(id).write(toWrite);

	}

	private class MessageCounter {

		private final int maxMsgs;
		private int msgsReceived;

		MessageCounter(int maxMsgs) {
			this.maxMsgs = maxMsgs;
		}

		void msgReceived() throws ToManyMessagesException {
			if (maxMsgs == msgsReceived) {
				throw new ToManyMessagesException("Received more messages than actually calculated");
			}
			msgsReceived++;
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
}
