package de.netprojectev.client.gui.main;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.jdesktop.swingx.util.GraphicsUtilities;

import de.netprojectev.client.networking.MessageProxyClient;

public class Misc {
	public static ImageIcon getScaledImageIconFromBufImg(BufferedImage original, int widthToScaleTo) throws FileNotFoundException, IOException {

		ImageIcon scaled = new ImageIcon(Misc.getScaledImageInstanceFast(original, widthToScaleTo, widthToScaleTo * original.getHeight(null) / original.getWidth(null)));
		original = null;

		return scaled;
	}

	public static ImageIcon getScaledImageIcon(ImageIcon original, int widthToScaleTo) throws FileNotFoundException, IOException {
		BufferedImage bi = imageIconToBufferedImage(original);
		ImageIcon scaled = new ImageIcon(Misc.getScaledImageInstanceFast(bi, widthToScaleTo, widthToScaleTo * bi.getHeight(null) / bi.getWidth(null)));

		return scaled;
	}

	public static BufferedImage imageIconToBufferedImage(ImageIcon iconToConvert) {

		BufferedImage bi = createCompatibleTranslucentImage(iconToConvert.getIconWidth(), iconToConvert.getIconHeight());
		Graphics g = bi.createGraphics();
		iconToConvert.paintIcon(null, g, 0, 0);
		g.dispose();
		return bi;
	}

	/**
	 * This method scales a given {@link BufferedImage} and returns the scaled
	 * instance of it. This method is written in optimizing performance without
	 * big losings in quality. Especially downscaling is very highly optimized
	 * due to the use of {@link GraphicsUtilities}
	 *
	 * @param imageToScale
	 *            the {@link BufferedImage} that should be scaled
	 * @param newWidth
	 *            the new width
	 * @param newHeight
	 *            the new height, if 0 then the height is calculated to keep the
	 *            aspect ratio
	 * @return the scaled image instance as {@link BufferedImage} in compatible
	 *         mode
	 */
	public static BufferedImage getScaledImageInstanceFast(BufferedImage imageToScale, int newWidth, int newHeight) {
		BufferedImage scaledImage;

		int oldWidth = imageToScale.getWidth();
		int oldHeight = imageToScale.getHeight();

		if (oldWidth > newWidth && oldHeight > newHeight) {
			scaledImage = GraphicsUtilities.createThumbnail(imageToScale, newWidth, newHeight);
		} else {
			scaledImage = GraphicsUtilities.createCompatibleImage(newWidth, newHeight);
			Graphics2D g2 = scaledImage.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(imageToScale, 0, 0, newWidth, newHeight, null);
			g2.dispose();
		}

		return scaledImage;
	}

	// This method returns an image that is compatible with the
	// primary display device. If a user has multiple displays
	// with different depths, this may be suboptimal, but it
	// should work in the general case.
	public static GraphicsConfiguration getConfiguration() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	}

	// Creates a compatible image of the same dimension and
	// transparency as the given image
	public static BufferedImage createCompatibleImage(BufferedImage image) {
		return createCompatibleImage(image, image.getWidth(), image.getHeight());
	}

	// Creates a compatible image with the given width and
	// height that has the same transparency as the given image
	public static BufferedImage createCompatibleImage(BufferedImage image, int width, int height) {
		return getConfiguration().createCompatibleImage(width, height, image.getTransparency());
	}

	// Creates an opaque compatible image with the given
	// width and height
	public static BufferedImage createCompatibleImage(int width, int height) {
		return getConfiguration().createCompatibleImage(width, height);
	}

	// Creates a translucent compatible image with the given
	// width and height
	public static BufferedImage createCompatibleTranslucentImage(int width, int height) {
		return getConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
	}

	// Creates a compatible image from the content specified
	// by the resource
	public static BufferedImage loadCompatibleImage(File resource) throws IOException {
		BufferedImage image = ImageIO.read(resource);
		return toCompatibleImage(image);
	}

	// Creates and returns a new compatible image into which
	// the source image is copied

	// If the source image is already compatible, then the
	// source image is returned
	// This version takes a BufferedImage, but it could be
	// extended to take an Image instead
	public static BufferedImage toCompatibleImage(BufferedImage image) {
		GraphicsConfiguration gc = getConfiguration();
		if (image.getColorModel().equals(gc.getColorModel())) {
			return image;
		}
		BufferedImage compatibleImage = gc.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());
		Graphics g = compatibleImage.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return compatibleImage;

	}

	public static byte[] bufferedImageToByteArray(BufferedImage bufImage) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try
		{
			int width = bufImage.getWidth(null);
			int height = bufImage.getHeight(null);
			BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			bi.getGraphics().drawImage(bufImage, 0, 0, null);
			ImageIO.write(bi, "PNG", baos);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return baos.toByteArray();
	}

	/**
	 * handling the programs termination showing up a confirmation dialog and
	 * invokes the serialization
	 */
	public static void quit(final Component parent, final MessageProxyClient proxy) {
		int exit = JOptionPane.showConfirmDialog(parent, "Are you sure you want to exit?", "Quit", JOptionPane.YES_NO_OPTION);
		if (exit == JOptionPane.YES_OPTION) {
			proxy.sendDisconnectRequest();
			/*
			 * try { saveToDisk(); ImageFile.threadPool.shutdownNow(); } catch
			 * (IOException e) {
			 * log.error("Error during saving settings and files.", e); }
			 */

			System.exit(0);
		}

	}

	/**
	 * Restarts the currently running jar file using the process builder.
	 *
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static void restartApplication(Class<?> clazz) throws URISyntaxException, IOException {
		final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
		final File currentJar = new File(clazz.getProtectionDomain().getCodeSource().getLocation().toURI());

		if (!currentJar.getName().endsWith(".jar")) {
			return;
		}

		final ArrayList<String> command = new ArrayList<String>();
		command.add(javaBin);
		command.add("-jar");
		command.add(currentJar.getPath());
		final ProcessBuilder builder = new ProcessBuilder(command);
		builder.start();
		System.exit(0);
	}

	/*
	 * public static void writeImageToDiskAsPNG(BufferedImage image, File path)
	 * { Iterator<ImageWriter> itereratorImageWriter =
	 * ImageIO.getImageWritersByFormatName("png"); ImageWriter writer =
	 * (ImageWriter) itereratorImageWriter.next(); ImageWriteParam writeParams =
	 * writer.getDefaultWriteParam();
	 * 
	 * try { FileImageOutputStream fos = new FileImageOutputStream(path);
	 * writer.setOutput(fos); IIOImage img = new IIOImage((RenderedImage) image,
	 * null, null); writer.write(null, img, writeParams);
	 * 
	 * } catch (Exception ex) { ex.printStackTrace(); } }
	 */

	/**
	 * reading current mouse pointer position
	 *
	 * @return current mouse pointer position.
	 */
	public static Point currentMousePosition() {
		PointerInfo info = MouseInfo.getPointerInfo();
		return info.getLocation();
	}

}
