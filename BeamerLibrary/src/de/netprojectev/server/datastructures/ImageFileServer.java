package de.netprojectev.server.datastructures;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.imageio.ImageIO;

import de.netprojectev.client.datastructures.MediaType;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.datamodel.PreferencesModelServer;
import de.netprojectev.common.utils.Misc;

public class ImageFileServer extends MediaFileServer {

	/**
	 *
	 */
	private static final long serialVersionUID = -6972824512907132864L;

	private transient byte[] image;
	private File pathOnDisk;

	public ImageFileServer(String name, UUID priorityID, File pathOnDisk) throws IOException {
		super(name, priorityID);
		Path srcPath = Paths.get(pathOnDisk.getAbsolutePath());
		Path destPath = Paths.get(ConstantsServer.SAVE_PATH, ConstantsServer.CACHE_PATH_IMAGES, this.id.toString());
		Files.move(srcPath, destPath, StandardCopyOption.ATOMIC_MOVE);
		this.pathOnDisk = destPath.toFile();
		this.image = null;
	}

	public void clearMemory() {
		this.image = null;
	}

	@Override
	public MediaType determineMediaType() {
		return MediaType.Image;
	}

	@Override
	public byte[] determinePreview() throws IOException {
		InputStream in = new ByteArrayInputStream(get());
		final BufferedImage compImage = ImageIO.read(in);
		double imageAspectRatio = ((double) compImage.getWidth()) / ((double) compImage.getHeight());
		int newWidth = Integer.parseInt(PreferencesModelServer.getPropertyByKey(ConstantsServer.PROP_GENERAL_PREVIEW_WIDTH));
		int newHeight = (int) (newWidth / imageAspectRatio);
		BufferedImage scaled = Misc.getScaledImageInstanceFast(compImage, newWidth, newHeight);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(scaled, "png", out);
		out.flush();
		return out.toByteArray();
	}

	public byte[] get() throws IOException {
		if (this.image == null) {
			this.image = Files.readAllBytes(Paths.get(this.pathOnDisk.getAbsolutePath()));
		}
		return this.image;
	}
}
