package de.netprojectev.networking;

import java.io.Serializable;
import java.util.UUID;

public class VideoFileData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4461125291371755069L;
	private final byte[] byteBuffer;
	private final int msgNumber;
	private final UUID videoFileUUID;
	
	public VideoFileData(byte[] byteBuffer, UUID videoFileUUID, int msgNumber) {
		this.byteBuffer = byteBuffer;
		this.videoFileUUID = videoFileUUID;
		this.msgNumber = msgNumber;
	}

	public byte[] getByteBuffer() {
		return byteBuffer;
	}

	public int getMsgNumber() {
		return msgNumber;
	}

	public UUID getVideoFileUUID() {
		return videoFileUUID;
	}
	
	@Override
	public String toString() {
		return "Video File Data Message. #" + msgNumber + "video file UUID: " + videoFileUUID;
	}
	
}
