package de.netprojectev.client.datastructures;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;

import de.netprojectev.datastructures.media.MediaFile;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.server.datastructures.Countdown;
import de.netprojectev.server.datastructures.ImageFile;
import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.datastructures.Themeslide;
import de.netprojectev.server.datastructures.VideoFile;
import de.netprojectev.utils.HelperMethods;

public class ClientMediaFile extends MediaFile {

	public interface ClientMediaFilePreview {
		
	}
	
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
		// TODO make preview possible for videos and countdown
		
		this.showCount = serverMediaFile.getShowCount();
		this.current = serverMediaFile.isCurrent();
		
		int widthToScaleTo = ConstantsServer.DEFAULT_PREVIEW_SCAL_WIDTH;
		
		
		if (serverMediaFile instanceof Countdown) {
			type = MediaType.Countdown;
			preview = null;
		} else if (serverMediaFile instanceof ImageFile) {
			type = MediaType.Image;
			preview = HelperMethods.getScaledImageIconFromBufImg(((ImageFile) serverMediaFile).get(), widthToScaleTo);
		} else if (serverMediaFile instanceof VideoFile) {
			type = MediaType.Video;
			preview = null;
		} else if (serverMediaFile instanceof Themeslide) {
			type = MediaType.Themeslide;
			preview = HelperMethods.getScaledImageIconFromBufImg(((Themeslide) serverMediaFile).get(), widthToScaleTo);
			
		} else {
			type = MediaType.Unknown;
			preview = null;
		}

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
