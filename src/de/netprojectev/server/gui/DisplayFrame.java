/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.netprojectev.server.gui;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.apache.logging.log4j.Logger;

import de.netprojectev.exceptions.MediaDoesNotExsistException;
import de.netprojectev.exceptions.MediaListsEmptyException;
import de.netprojectev.misc.LoggerBuilder;
import de.netprojectev.misc.Misc;
import de.netprojectev.server.datastructures.Countdown;
import de.netprojectev.server.datastructures.ImageFile;
import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.datastructures.Themeslide;
import de.netprojectev.server.datastructures.VideoFile;
import de.netprojectev.server.networking.MessageProxyServer;

/**
 * GUI class frame, to store the viewing components, as the live ticker and the image and themeslide showing component.
 * @author samu
 */
public class DisplayFrame extends javax.swing.JFrame {

	
	public interface VideoFinishListener {
		public void videoFinished() throws MediaDoesNotExsistException, MediaListsEmptyException;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 863589702184282724L;
	private static final Logger log = LoggerBuilder.createLogger(DisplayFrame.class);

	private final MessageProxyServer proxy;
	private boolean fullscreen;
	private Timer countdownTimer;
	private VideoFinishListener videoFinishedListener;
	
    public DisplayFrame(MessageProxyServer proxy) {
        this.proxy = proxy;
    	initComponents();
        fullscreen = false;
    }

    public void showMediaFileInMainComponent(ServerMediaFile fileToShow) {
    	if(fileToShow instanceof ImageFile) {
    		showImageFile((ImageFile) fileToShow);
    	} else if(fileToShow instanceof Themeslide) {
    		showThemeslide((Themeslide) fileToShow);
    	} else if(fileToShow instanceof VideoFile) {
    		showVideoFile((VideoFile) fileToShow);
    	} else if(fileToShow instanceof Countdown) {
    		showCountdown((Countdown) fileToShow);
    	} else {
    		//TODO throw Exception
    	}
    }
    
    private void showImageFile(ImageFile image) {
    	displayMainComponent.drawImage(Misc.imageIconToBufferedImage(image.getImage()));
    }
    
    private void showVideoFile(VideoFile video) {
    	//TODO maybe check what the timer is doing during automode enabled and video playing
    	//TODO check if the switch works with fullscreen
    	try {
    		final Process vlc = new VlcPlayBackUtility(video.getVideoFile()).startPlay();
    		
    		new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						vlc.waitFor();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						videoFinishedListener.videoFinished();
					} catch (MediaDoesNotExsistException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MediaListsEmptyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
    		
    		
		} catch (Exception e) {
			// TODO make proper exception handling if video fails to play
			e.printStackTrace();
		}

    }
    
    private void showThemeslide(Themeslide themeslide) {
    	displayMainComponent.drawImage(Misc.imageIconToBufferedImage(themeslide.getImageRepresantation().getImage()));
    }
    
    private void showCountdown(final Countdown countdown) {
    	if(countdownTimer != null) {
    		countdownTimer.stop();
    	}
    	
    	displayMainComponent.drawCountdown(countdown);
    	displayMainComponent.repaint();
    	countdownTimer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				countdown.decreaseOneSecond();
				displayMainComponent.repaint();
				if(countdown.getDurationInSeconds() <= 0) {
					countdownTimer.stop();
					displayMainComponent.countdownFinished();
				}
			}
		});
    	countdownTimer.start();
    	
    	//TODO last worked here, make a time sync and make it work enabling autmode while countdown already runningS
    }
    
    public void startLiveTicker() {
    	tickerComponent.setTickerString(proxy.getTickerModel().generateCompleteTickerText());
    	tickerComponent.initLiveTickerAndStart();
    }
    
    public void updateLiveTicker() {
    	tickerComponent.setTickerString(proxy.getTickerModel().generateCompleteTickerText());
    }
    
    public void stopLiveTicker() {
    	tickerComponent.stopLiveTicker();
    }
    
    public void clearDisplay() {
    	displayMainComponent.clear();
    }
    
    
    /**
     * Setting the display frame as fullscreen exclusive window
     * @param screenNumber the number of the screen to show on
     */
    public void enterFullscreen(int screenNumber) {
    	
    	if(!fullscreen) {
    		//log.log(Level.INFO, "entering fullscreen mode");
	    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice[] myDevices = ge.getScreenDevices();
			dispose();
			this.setUndecorated(true);
			setVisible(true);
			if (screenNumber >= 0 && screenNumber < myDevices.length) {
				myDevices[screenNumber].setFullScreenWindow(this);
		    	fullscreen = true;
		    	//log.log(Level.INFO, "entering fullscreen mode successful");
		    	//TODO handle fullscreen change, because the resolution of images have to change
		    	// MediaHandlerOld.getInstance().generateNewDisplayImages();
			} else {
				//log.log(Level.SEVERE, "error entering fullscreen mode");
				//TODO throw Exception
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
    		//log.log(Level.INFO, "exiting fullscreen mode");
	    	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice[] myDevices = ge.getScreenDevices();
			dispose();
			this.setUndecorated(false);
			setVisible(true);
			myDevices[0].setFullScreenWindow(null);
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

	public void setVideoFinishedListener(VideoFinishListener videoFinishedListener) {
		this.videoFinishedListener = videoFinishedListener;
	}
	
}
