package de.netprojectev.server.datastructures;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import de.netprojectev.client.datastructures.MediaType;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.model.PreferencesModelServer;
import de.netprojectev.utils.Misc;

public class Themeslide extends ServerMediaFile {

	/**
	 *
	 */
	private static final long serialVersionUID = -14234502199126341L;
	private UUID themeId;
	private ImageFile imageRepresantation;

	public Themeslide(String name, UUID themeId, UUID priorityID, ImageFile imageRepresentation) {
		super(name, priorityID);
		this.themeId = themeId;
		this.imageRepresantation = imageRepresentation;
	}

	@Override
	public MediaType determineMediaType() {
		return MediaType.Themeslide;
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
		return this.imageRepresantation.get();
	}

	public ImageFile getImageRepresantation() {
		return this.imageRepresantation;
	}

	public UUID getThemeId() {
		return this.themeId;
	}
}
