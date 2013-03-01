/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.netprojectev.server.gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import de.netprojectev.misc.Misc;
import de.netprojectev.old.server.datastructures.media.Countdown;
import de.netprojectev.old.server.datastructures.media.ImageFile;
import de.netprojectev.old.server.datastructures.media.Themeslide;
import de.netprojectev.old.server.datastructures.media.VideoFile;
import de.netprojectev.server.networking.MessageProxyServer;

/**
 * GUI class frame, to store the viewing components, as the live ticker and the image and themeslide showing component.
 * @author samu
 */
public class DisplayFrame extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 863589702184282724L;
	private static final Logger log = Misc.getLoggerAll(DisplayFrame.class.getName());

	private final MessageProxyServer proxy;
	private boolean fullscreen;
	private int screenNumberDisplayFrame; 
	
    public DisplayFrame(MessageProxyServer proxy) {
        this.proxy = proxy;
    	initComponents();
        fullscreen = false;
        screenNumberDisplayFrame = 0;
    }

    public void showImageFile(ImageFile image) {
    	// TODO
    }
    
    public void showVideoFile(VideoFile video) {
    	// TODO
    }
    
    public void showThemeslide(Themeslide themeslide) {
    	// TODO
    }
    
    public void showCountdown(Countdown countdown) {
    	// TODO
    }
    
    public void startLiveTicker() {
    	// TODO
    }
    
    public void updateLiveTIcker(String tickerText) {
    	// TODO
    }
    
    public void stopLiveTicker() {
    	// TODO
    }
    
    public void clearDisplay() {
    	// TODO
    }
    
    
    /**
     * Setting the display frame as fullscreen exclusive window
     * @param screenNumber the number of the screen to show on
     */
    public void enterFullscreen(int screenNumber) {
    	
    	if(!fullscreen) {
    		log.log(Level.INFO, "entering fullscreen mode");
    		screenNumberDisplayFrame = screenNumber;
	    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice[] myDevices = ge.getScreenDevices();
			dispose();
			this.setUndecorated(true);
			setVisible(true);
			if (screenNumber >= 0 && screenNumber < myDevices.length) {
				myDevices[screenNumber].setFullScreenWindow(this);
		    	fullscreen = true;
		    	log.log(Level.INFO, "entering fullscreen mode successful");
		    	//TODO handle fullscreen change, because the resolution of images have to change
		    	// MediaHandlerOld.getInstance().generateNewDisplayImages();
			} else {
				log.log(Level.SEVERE, "error entering fullscreen mode");
				JOptionPane.showMessageDialog(this, "Error during entering fullscreen exclusive mode. \nCheck the choosen screen.", "Error", JOptionPane.ERROR_MESSAGE);
				dispose();
				this.setUndecorated(false);
				setVisible(true);
				fullscreen = false;
			}
    	}

    }
    
    /**
     * lets the display window exiting the fullscreen
     */
    public void exitFullscreen() {
    	
    	if(fullscreen) {
    		log.log(Level.INFO, "exiting fullscreen mode");
	    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice[] myDevices = ge.getScreenDevices();
			dispose();
			this.setUndecorated(false);
			setVisible(true);
			myDevices[screenNumberDisplayFrame].setFullScreenWindow(null);
			pack();
    		fullscreen = false;
    		//TODO handle fullscreen change, because the resolution of images have to change
    		//MediaHandlerOld.getInstance().generateNewDisplayImages();
    	}
    }

	/**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        displayMainComponent = new de.netprojectev.server.gui.DisplayMainComponent();
        tickerComponent = new de.netprojectev.server.gui.TickerComponent();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        displayMainComponent.setDoubleBuffered(true);

        tickerComponent.setDoubleBuffered(true);
        tickerComponent.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N

        javax.swing.GroupLayout tickerComponentLayout = new javax.swing.GroupLayout(tickerComponent);
        tickerComponent.setLayout(tickerComponentLayout);
        tickerComponentLayout.setHorizontalGroup(
            tickerComponentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 848, Short.MAX_VALUE)
        );
        tickerComponentLayout.setVerticalGroup(
            tickerComponentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 61, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout displayMainComponentLayout = new javax.swing.GroupLayout(displayMainComponent);
        displayMainComponent.setLayout(displayMainComponentLayout);
        displayMainComponentLayout.setHorizontalGroup(
            displayMainComponentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tickerComponent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        displayMainComponentLayout.setVerticalGroup(
            displayMainComponentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, displayMainComponentLayout.createSequentialGroup()
                .addContainerGap(416, Short.MAX_VALUE)
                .addComponent(tickerComponent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayMainComponent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(displayMainComponent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DisplayFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DisplayFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DisplayFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DisplayFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                //new DisplayMainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.netprojectev.server.gui.DisplayMainComponent displayMainComponent;
    private de.netprojectev.server.gui.TickerComponent tickerComponent;
    // End of variables declaration//GEN-END:variables
	
}
