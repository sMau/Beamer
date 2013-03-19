package de.netprojectev.client.datastructures;

import de.netprojectev.datastructures.media.MediaFile;
import de.netprojectev.server.datastructures.Countdown;
import de.netprojectev.server.datastructures.ImageFile;
import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.datastructures.Themeslide;
import de.netprojectev.server.datastructures.VideoFile;

public class ClientMediaFile extends MediaFile {

	private final MediaType type;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2896809877450716652L;

	public ClientMediaFile(ServerMediaFile serverMediaFile) {
		super(serverMediaFile.getName(), serverMediaFile.getPriority(), serverMediaFile.getId());
		
		if(serverMediaFile instanceof Countdown) {
			type = MediaType.Countdown;
		} else if(serverMediaFile instanceof ImageFile) {
			type = MediaType.Image;
		} else if(serverMediaFile instanceof VideoFile) {
			type = MediaType.Video;
		} else if(serverMediaFile instanceof Themeslide) {
			type = MediaType.Themeslide;
		} else {
			type = MediaType.Unknown;
		}
		
		
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

}
