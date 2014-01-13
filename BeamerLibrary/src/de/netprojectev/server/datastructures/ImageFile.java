package de.netprojectev.server.datastructures;

import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.server.ConstantsServer;
import de.netprojectev.utils.HelperMethods;

public class ImageFile extends ServerMediaFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6972824512907132864L;
	
	private transient BufferedImage image;
	private File pathOnDisk;

	public ImageFile(String name, Priority priority, int[][] rgbaValues) {
		super(name, priority);
		this.pathOnDisk = new File(ConstantsServer.SAVE_PATH + ConstantsServer.CACHE_PATH_IMAGES + getId());
		this.image = null;

		BufferedImage tmpImage = readRGBAValues(rgbaValues);
		HelperMethods.writeImageToDiskAsPNG(tmpImage, pathOnDisk);
	}

	/**
	 * 
	 * @return a compatible buffered image represented by this media image file
	 * @throws IOException
	 */
	public BufferedImage get() throws IOException {
		if(image == null) {
			image = HelperMethods.loadCompatibleImage(pathOnDisk);
		}
		return image;
	}
	
	public void clearMemory() {
		image = null;
	}
	
	private BufferedImage readRGBAValues(int[][] rbgaValues) {

		if(rbgaValues == null || rbgaValues.length < 1 || rbgaValues[0].length < 1) {
			throw new IllegalArgumentException("The given int array have to be at least 1x1");
		}
		
		image = HelperMethods.getConfiguration().createCompatibleImage(rbgaValues.length, rbgaValues[0].length, Transparency.TRANSLUCENT);

		for (int i = 0; i < rbgaValues.length; i++) {
			for (int j = 0; j < rbgaValues[0].length; j++) {
				image.setRGB(i, j, rbgaValues[i][j]);
			}
		}
		
		return image;

	}

}
