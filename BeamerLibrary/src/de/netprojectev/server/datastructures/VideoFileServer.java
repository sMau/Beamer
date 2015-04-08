package de.netprojectev.server.datastructures;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import de.netprojectev.client.datastructures.MediaType;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.datamodel.PreferencesModelServer;

public class VideoFileServer extends MediaFileServer {

	/**
	 *
	 */
	private static final long serialVersionUID = 2033435655791192980L;
	private File videoFile;

	public VideoFileServer(String name, File videoFile) throws IOException {
		super(name, PreferencesModelServer.getDefaultPriority());
		Path srcPath = Paths.get(videoFile.getAbsolutePath());
		Path destPath = Paths.get(ConstantsServer.SAVE_PATH, ConstantsServer.CACHE_PATH_VIDEOS, this.id.toString());
		Files.move(srcPath, destPath, StandardCopyOption.ATOMIC_MOVE);
		this.videoFile = destPath.toFile();
	}

	@Override
	public MediaType determineMediaType() {
		return MediaType.Video;
	}

	@Override
	public byte[] determinePreview() throws IOException {
		return super.getNoPreviewImage();
	}

	public File getVideoFile() {
		return this.videoFile;
	}

	public void setVideoFile(File videoFile) {
		this.videoFile = videoFile;
	}
}
