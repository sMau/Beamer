package de.netprojectev.client.datastructures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;

import de.netprojectev.datastructures.media.MediaFile;
import de.netprojectev.misc.Misc;
import de.netprojectev.server.datastructures.Countdown;
import de.netprojectev.server.datastructures.ImageFile;
import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.datastructures.Themeslide;
import de.netprojectev.server.datastructures.VideoFile;

public class ClientMediaFile extends MediaFile {

	private final MediaType type;
	private ImageIcon preview;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2896809877450716652L;

	private ClientMediaFile(ClientMediaFile clientMediaFile) {
		super(clientMediaFile.getName(), clientMediaFile.getPriority(), clientMediaFile.getId());
		this.type = clientMediaFile.type;
	}
	
	public ClientMediaFile(ServerMediaFile serverMediaFile) throws FileNotFoundException, IOException {
		super(serverMediaFile.getName(), serverMediaFile.getPriority(), serverMediaFile.getId());
		// TODO maybe make previews possible for the others too
		if (serverMediaFile instanceof Countdown) {
			type = MediaType.Countdown;
			preview = null;
		} else if (serverMediaFile instanceof ImageFile) {
			type = MediaType.Image;
			preview = generatePreview(((ImageFile) serverMediaFile).getImage());
		} else if (serverMediaFile instanceof VideoFile) {
			type = MediaType.Video;
			preview = null;
		} else if (serverMediaFile instanceof Themeslide) {
			type = MediaType.Themeslide;
			preview = generatePreview(((Themeslide) serverMediaFile).getImageRepresantation().getImage());
		} else {
			type = MediaType.Unknown;
			preview = null;
		}

	}

	private ImageIcon generatePreview(ImageIcon original) throws FileNotFoundException, IOException {
		BufferedImage bi = new BufferedImage(original.getIconWidth(), original.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.createGraphics();
		original.paintIcon(null, g, 0, 0);
		g.dispose();
		
		int widthToScaleTo = 640; // TODO receive this from the props
		ImageIcon scaled = new ImageIcon(Misc.getScaledImageInstanceFast(bi, widthToScaleTo , (int) (widthToScaleTo * bi.getHeight(null))/bi.getWidth(null)));
		bi = null;
		
		return scaled;
	}
	
	public ClientMediaFile copy() {
		return new ClientMediaFile(this);
	}

	@Override
	public String toString() {
		String res = "";

		res += "Name: " + name + " ";
		res += "UUID: " + getId() + " ";
		res += "Type: " + type + " ";
		res += "Priority: " + priority + " ";

		return res;
	}

	public MediaType getType() {
		return type;
	}

	public ImageIcon getPreview() {
		return preview;
	}

	public void setPreview(ImageIcon preview) {
		this.preview = preview;
	}

}
