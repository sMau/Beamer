/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.netprojectev.GUI.Themeslide;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.print.attribute.standard.Compression;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

import de.netprojectev.Media.MediaFile;
import de.netprojectev.Media.Priority;
import de.netprojectev.Media.Theme;
import de.netprojectev.Media.Themeslide;
import de.netprojectev.MediaHandler.MediaHandler;
import de.netprojectev.Misc.Constants;
import de.netprojectev.Misc.Misc;
import de.netprojectev.Preferences.PreferencesHandler;

/**
 * This is a GUI class to create and design a new Themeslide.
 * It contains a small text editor and the possibilty to select a Theme (background).
 * @author samu
 *
 */
public class ThemeslideCreatorFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = -3653577264825548156L;
	
	private int marginLeft = 0;
	private int marginTop = 0;
	
	private Boolean evtFromGUIupdateFontSize = false;
	private Boolean evtFromGUIupdateFontFamily = false;

	private Boolean lastEventCaretPosChanged = false;
	private int lastEventCaretPos = 0;
	
	
    public ThemeslideCreatorFrame() {
    	initComponents();
    	
        for(int i = 0; i < PreferencesHandler.getInstance().getListOfThemes().size(); i++) {
        	jComboBoxTheme.addItem(PreferencesHandler.getInstance().getListOfThemes().get(i).getName());
        }
        for(int i = 0; i < PreferencesHandler.getInstance().getListOfPriorities().size(); i++) {
        	jComboBoxPriority.addItem(PreferencesHandler.getInstance().getListOfPriorities().get(i).getName());
        }
        String[] font = Constants.FONT_FAMILIES;
        for(int i = 0; i < font.length; i++) {
        	jComboBoxFontType.addItem(font[i]);
        }

        for(int i = 0; i < Constants.FONT_SIZES.length; i++) {
        	jComboBoxFontSize.addItem(Constants.FONT_SIZES[i]);
        }
        
        jComboBoxFontType.setSelectedItem(textPaneThemeslide.getFont().getFamily());
        jComboBoxFontSize.setSelectedItem(textPaneThemeslide.getFont().getSize() + "px");
        
        setLocation(Misc.currentMousePosition());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxTheme = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldThemeSlideName = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jButtonCancel = new javax.swing.JButton();
        jButtonAdd = new javax.swing.JButton();
        jButtonAddAndShow = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxPriority = new javax.swing.JComboBox();
        jComboBoxFontSize = new javax.swing.JComboBox();
        jComboBoxFontType = new javax.swing.JComboBox();
        jToggleButtonBold = new javax.swing.JToggleButton();
        jLabelAnchor = new javax.swing.JLabel();
        jTextFieldMarginLeft = new javax.swing.JTextField();
        jTextFieldMarginTop = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        textPanePanel1 = new de.netprojectev.GUI.Themeslide.TextPanePanel();
        textPaneThemeslide = new de.netprojectev.GUI.Themeslide.TextPaneThemeslide();
        jToggleButtonItalic = new javax.swing.JToggleButton();
        jToggleButtonUnderline = new javax.swing.JToggleButton();
        jButtonColorPicker = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Themeslide Creator");

        jLabel3.setText("Theme");

        jComboBoxTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxThemeActionPerformed(evt);
            }
        });

        jLabel4.setText("Name");

        jTextFieldThemeSlideName.setColumns(16);

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonAdd.setText("Add");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonAddAndShow.setText("Add and show");
        jButtonAddAndShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddAndShowActionPerformed(evt);
            }
        });

        jLabel1.setText("Priority");

        jComboBoxFontSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFontSizeActionPerformed(evt);
            }
        });

        jComboBoxFontType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFontTypeActionPerformed(evt);
            }
        });

        jToggleButtonBold.setText("<html><body><b>B</b></body></html>");
        jToggleButtonBold.setToolTipText("Toggle bold");
        jToggleButtonBold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonBoldActionPerformed(evt);
            }
        });

        jLabelAnchor.setText("Margin");

        jTextFieldMarginLeft.setText("0");

        jTextFieldMarginTop.setText("0");

        jScrollPane1.setDoubleBuffered(true);

        textPaneThemeslide.setDoubleBuffered(true);
        textPaneThemeslide.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                caretPositionUpdated(e);

            }

        });

        javax.swing.GroupLayout textPanePanel1Layout = new javax.swing.GroupLayout(textPanePanel1);
        textPanePanel1.setLayout(textPanePanel1Layout);
        textPanePanel1Layout.setHorizontalGroup(
            textPanePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textPaneThemeslide, javax.swing.GroupLayout.DEFAULT_SIZE, 1039, Short.MAX_VALUE)
        );
        textPanePanel1Layout.setVerticalGroup(
            textPanePanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textPaneThemeslide, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(textPanePanel1);

        jToggleButtonItalic.setText("<html><body><b><i>I</i></b></body></html>");
        jToggleButtonItalic.setToolTipText("Toggle italic");
        jToggleButtonItalic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonItalicActionPerformed(evt);
            }
        });

        jToggleButtonUnderline.setText("<html><body><b><u>U</u></b></body></html>");
        jToggleButtonUnderline.setToolTipText("Toggle underline");
        jToggleButtonUnderline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonUnderlineActionPerformed(evt);
            }
        });

        jButtonColorPicker.setText("Color");
        jButtonColorPicker.setToolTipText("Choose text color");
        jButtonColorPicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonColorPickerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToggleButtonBold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAddAndShow)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToggleButtonItalic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToggleButtonUnderline, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jComboBoxFontSize, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxFontType, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7)
                        .addComponent(jButtonColorPicker)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelAnchor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldMarginLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldMarginTop, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldThemeSlideName, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxTheme, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxPriority, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1))
                .addContainerGap())
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxTheme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldThemeSlideName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxPriority, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxFontSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxFontType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonBold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAnchor)
                    .addComponent(jTextFieldMarginLeft, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldMarginTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonUnderline, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToggleButtonItalic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonColorPicker))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancel)
                    .addComponent(jButtonAdd)
                    .addComponent(jButtonAddAndShow))
                .addContainerGap())
        );

        jTextFieldMarginLeft.getDocument().addDocumentListener(new DocumentListener() {@Override
            public void insertUpdate(DocumentEvent e) {
                marginLeftModified();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                marginLeftModified();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                marginLeftModified();

            }});
            jTextFieldMarginTop.getDocument().addDocumentListener(new DocumentListener() {@Override
                public void insertUpdate(DocumentEvent e) {
                    marginTopModified();

                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    marginTopModified();

                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    marginTopModified();

                }});

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                );
                layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );

                pack();
            }// </editor-fold>//GEN-END:initComponents

    
    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
    	dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {                                            
    	addThemeslide();
    	dispose();
    }                                          

    private void jButtonAddAndShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddAndShowActionPerformed
    	addThemeslide();
    	MediaHandler.getInstance().getDisplayHandler().show(MediaHandler.getInstance().getMediaFiles().getLast());
    	dispose();
    }//GEN-LAST:event_jButtonAddAndShowActionPerformed

    /**
     * on selection change, change the background image to the new selected themes background
     * @param evt
     */
    private void jComboBoxThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxThemeActionPerformed
    	textPaneThemeslide.setThemeBackground(PreferencesHandler.getInstance().getListOfThemes().get(jComboBoxTheme.getSelectedIndex()).getBackgroundImage());
    }//GEN-LAST:event_jComboBoxThemeActionPerformed

    /**
     * changing the selected text to bold
     * @param evt
     */
    private void jToggleButtonBoldActionPerformed(java.awt.event.ActionEvent evt) {                                                   
    	//TODO Button updates synching

    	new BoldAction().actionPerformed(evt);	
    }                                                 

    /**
     * on selection change, changing the selected text to the new font size
     * @param evt
     */
    private void jComboBoxFontSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFontSizeActionPerformed
    	
    	if(!evtFromGUIupdateFontSize) {    		
        	new FontSizeAction((String) jComboBoxFontSize.getSelectedItem()).actionPerformed(evt);
    	}
    	evtFromGUIupdateFontSize = false;
    	
    }//GEN-LAST:event_jComboBoxFontSizeActionPerformed

    /**
     * on selection change, changing the selected text to the new font family
     * @param evt
     */
    private void jComboBoxFontTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFontTypeActionPerformed
    	if(!evtFromGUIupdateFontFamily) {
    		new FontFamilyAction((String) jComboBoxFontType.getSelectedItem()).actionPerformed(evt);
    	}
    	evtFromGUIupdateFontFamily = false;

    }//GEN-LAST:event_jComboBoxFontTypeActionPerformed

    private void jToggleButtonItalicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonItalicActionPerformed
    	new ItalicAction().actionPerformed(evt);
    }//GEN-LAST:event_jToggleButtonItalicActionPerformed

    private void jToggleButtonUnderlineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonUnderlineActionPerformed
    	new UnderlineAction().actionPerformed(evt);
    }//GEN-LAST:event_jToggleButtonUnderlineActionPerformed

    private void jButtonColorPickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonColorPickerActionPerformed
    	(new ColorPickerDialog(this, true)).setVisible(true);
    	
    }//GEN-LAST:event_jButtonColorPickerActionPerformed

    /**
     * handles the adding of the new themeslide to the media handler.
     * checks all data and adds it if everything is fine, else showing an error dialog
     */
    private void addThemeslide() {
    	
    	//TODO
    	/*
    	 * here last worked on
    	 * image export to file works proper, but maybe quality can be still improved, look up jpeg compression settings for java
    	 * furthermore of course have to implement the integration in the application, as save files to a tmp dir, and modify serialization
    	 * and deserialization to be able to store themeslides
    	 * 
    	 * 
    	 */
    	
    	
    	String fileName = "/home/samu/Desktop/test123.jpg";
        int w = textPaneThemeslide.getWidth();
        int h = textPaneThemeslide.getHeight();
        float quality = 1f;
        
        BufferedImage bufImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D tmpG2D = bufImage.createGraphics();

        tmpG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        tmpG2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        tmpG2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        tmpG2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        tmpG2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        tmpG2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        textPaneThemeslide.paint(tmpG2D);

        Iterator<ImageWriter> itereratorImageWriter = ImageIO.getImageWritersByFormatName("jpeg");
        ImageWriter writer = (ImageWriter) itereratorImageWriter.next();
        ImageWriteParam writeParams = writer.getDefaultWriteParam();
        
        
        
        writeParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        writeParams.setProgressiveMode(ImageWriteParam.MODE_DEFAULT);
        writeParams.setCompressionQuality(quality);
        

        try {
            FileImageOutputStream fos = new FileImageOutputStream(new File(fileName));
            writer.setOutput(fos);
            IIOImage img = new IIOImage((RenderedImage) bufImage, null, null);
            writer.write(null, img, writeParams);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        tmpG2D.dispose();
    	

    	Priority priority = null;
    	Theme theme = null;
    	String name = null;
    	
    	try {
			marginTop = Integer.parseInt(jTextFieldMarginTop.getText());
		} catch (NumberFormatException e) {
			marginTop = 0;
			//e.printStackTrace();
		}
    	try {
			marginLeft = Integer.parseInt(jTextFieldMarginLeft.getText());
		} catch (NumberFormatException e) {
			marginLeft = 0;
			//e.printStackTrace();
		}
    	
    	
    	
    	if(!jTextFieldThemeSlideName.getText().isEmpty() && jTextFieldThemeSlideName.getText() != null) {
    		name  = jTextFieldThemeSlideName.getText();
    	} else {
    		JOptionPane.showMessageDialog(this, "Please enter a name.", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    	if(jComboBoxPriority.getSelectedIndex() >= 0) {
    		priority = PreferencesHandler.getInstance().getListOfPriorities().get(jComboBoxPriority.getSelectedIndex());
    	} else {
    		JOptionPane.showMessageDialog(this, "Please select a priority.", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    	if(jComboBoxTheme.getSelectedIndex() >= 0) {
    		theme = PreferencesHandler.getInstance().getListOfThemes().get(jComboBoxTheme.getSelectedIndex());
    	} else {
    		JOptionPane.showMessageDialog(this, "Please select a theme.", "Error", JOptionPane.ERROR_MESSAGE);
    	}
    	    	
    	if(name != null && priority != null && theme != null) {
    		MediaFile[] themeSlides = new MediaFile[1];
    		themeSlides[0] = new Themeslide(name, priority, theme, textPaneThemeslide, new Point(marginLeft, marginTop));
    		MediaHandler.getInstance().add(themeSlides);
    	} else {
    		JOptionPane.showMessageDialog(this, "Error while reading data.", "Error", JOptionPane.ERROR_MESSAGE);
    	}

    }
    
    
        
      
    
    /**
     * when margin left textfield changed, this method changes the left margin of the JTextPane
     */
	private void marginLeftModified() {
		//TODO show user information about entering a value bigger than 0/TODO not working if frame smaller than set anchor
		if(!jTextFieldMarginLeft.getText().isEmpty()) {
	    	try {
				if(Integer.parseInt(jTextFieldMarginLeft.getText()) > 0) {
					Insets oldMargin = textPaneThemeslide.getMargin();
					textPanePanel1.setMarginOfTextPaneThemslide(new Insets(oldMargin.top, Integer.parseInt(jTextFieldMarginLeft.getText()), oldMargin.bottom, oldMargin.right));
				}
			} catch (NumberFormatException e) {
				//e.printStackTrace();
			}
		}
	}

	/**
	 * when margin top textfield changed, this method changes the top margin of the JTextPane
	 */
	private void marginTopModified() {
		//TODO show user information about entering a value bigger than 0
		if(!jTextFieldMarginTop.getText().isEmpty()) {
			try {
				if(Integer.parseInt(jTextFieldMarginTop.getText()) > 0) {
					Insets oldMargin = textPaneThemeslide.getMargin();
					textPanePanel1.setMarginOfTextPaneThemslide(new Insets(Integer.parseInt(jTextFieldMarginTop.getText()), oldMargin.left, oldMargin.bottom, oldMargin.right));				}
			} catch (NumberFormatException e) {
				//e.printStackTrace();
			}
		}
    	
    	
	}
	
	/**
	 * This class handles the event triggerd by bold button, and toggles the bold text-attribute
	 * 
	 * @author samu
	 *
	 */
	class BoldAction extends StyledEditorKit.StyledTextAction {
		
		
		
		private static final long serialVersionUID = 9174670038684056758L;

		public BoldAction() {
			super("font-bold");
		}

		public String toString() {
			return "Bold";
		}

		public void actionPerformed(ActionEvent e) {
			
			JEditorPane editor = getEditor(e);
			if (editor != null) {
				textPaneThemeslide.saveCurrentCaretPosition();
				
				StyledEditorKit kit = getStyledEditorKit(editor);
				MutableAttributeSet attr = kit.getInputAttributes();
				boolean bold = (StyleConstants.isBold(attr)) ? false : true;
				SimpleAttributeSet sas = new SimpleAttributeSet();
				StyleConstants.setBold(sas, bold);
				setCharacterAttributes(editor, sas, false);

				textPaneThemeslide.restoreLastCaretPosition();

			}
		}
	}
	
	
	/**
	 * 
	 * This class handles the event triggerd by italic button, and toggles the italic text-attribute
	 * 
	 * @author samu
	 *
	 */
	class ItalicAction extends StyledEditorKit.StyledTextAction {

		private static final long serialVersionUID = -1428340091100055456L;

		public ItalicAction() {
			super("font-italic");
		}

		public String toString() {
			return "Italic";
		}

		public void actionPerformed(ActionEvent e) {
			JEditorPane editor = getEditor(e);
			if (editor != null) {
				textPaneThemeslide.saveCurrentCaretPosition();
				
				StyledEditorKit kit = getStyledEditorKit(editor);
				MutableAttributeSet attr = kit.getInputAttributes();
				boolean italic = (StyleConstants.isItalic(attr)) ? false : true;
				SimpleAttributeSet sas = new SimpleAttributeSet();
				StyleConstants.setItalic(sas, italic);
				setCharacterAttributes(editor, sas, false);

				textPaneThemeslide.restoreLastCaretPosition();
			}
		}
	}
	
	/**
	 * 
	 * This class handles the event triggerd by underline button, and toggles the underline text-attribute
	 * 
	 * @author samu
	 *
	 */
	class UnderlineAction extends StyledEditorKit.StyledTextAction {

		private static final long serialVersionUID = -1428340091100055456L;

		public UnderlineAction() {
			super("font-underline");
		}

		public String toString() {
			return "Underline";
		}

		public void actionPerformed(ActionEvent e) {
			JEditorPane editor = getEditor(e);
			if (editor != null) {
				textPaneThemeslide.saveCurrentCaretPosition();
				
				StyledEditorKit kit = getStyledEditorKit(editor);
				MutableAttributeSet attr = kit.getInputAttributes();
				boolean underline = (StyleConstants.isUnderline(attr)) ? false : true;
				SimpleAttributeSet sas = new SimpleAttributeSet();
				StyleConstants.setUnderline(sas, underline);
				setCharacterAttributes(editor, sas, false);
				
				textPaneThemeslide.restoreLastCaretPosition();
			}
		}
	}
	
	
	/**
	 * 
	 * This class handles the event triggerd by fontfamily combobox, and resets the selected text to the selected font family
	 * 
	 * @author samu
	 *
	 */
	class FontFamilyAction extends StyledEditorKit.StyledTextAction {

		
		//TODO make Font Family, Font Size, Font Color and Margin presettable
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 3710617413958842313L;
		private String selectedFamily;

		
		public FontFamilyAction(String selectedFamily) {
			super("Font Family");
			this.selectedFamily = selectedFamily;
			
		}
		
		public String toString() {
			return "Font Family";
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JTextPane editor = (JTextPane) getEditor(e);			
			if (editor != null) {
				textPaneThemeslide.saveCurrentCaretPosition();
				
				MutableAttributeSet attr = new SimpleAttributeSet();
				StyleConstants.setFontFamily(attr, selectedFamily);
				setCharacterAttributes(editor, attr, false);
				
				textPaneThemeslide.restoreLastCaretPosition();
			}
			
		}
	}
	
	/**
	 * 
	 * This class handles the event triggerd by fontsize combobox, and resets the selected text to the selected font size
	 * 
	 * @author samu
	 *
	 */
	class FontSizeAction extends StyledEditorKit.StyledTextAction {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 5817346150045790333L;
		
		private String selectedFontSize;
		
		public FontSizeAction(String selectedFontSize) {
			super("Font Size");
			this.selectedFontSize = selectedFontSize;
			
		}
		
		public String toString() {
			return "Font Size";
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JTextPane editor = (JTextPane) getEditor(e);			
			if (editor != null) {
				textPaneThemeslide.saveCurrentCaretPosition();
				
				MutableAttributeSet attr = new SimpleAttributeSet();
	            attr.addAttribute(StyleConstants.Size, selectedFontSize);
				setCharacterAttributes(editor, attr, false);

				textPaneThemeslide.restoreLastCaretPosition();
			}
			
		}
		
	}
	
	
	/**
	 * 
	 * This class handles the event triggerd by color picker, and sets the selected text to the selected color
	 * 
	 * @author samu
	 *
	 */
	class FontColorAction extends StyledEditorKit.StyledTextAction {

		
		/**
		 * 
		 */
		private static final long serialVersionUID = 9005216620313856874L;
		
		private Color selectedColor = Color.BLACK;
		
		public FontColorAction(Color selectedColor) {
			super("Font Color");
			this.selectedColor = selectedColor;
			
		}
		
		public String toString() {
			return "Font Color";
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			JTextPane editor = (JTextPane) getEditor(e);			
			if (editor != null) {
				textPaneThemeslide.saveCurrentCaretPosition();
				
				MutableAttributeSet attr = new SimpleAttributeSet();
				StyleConstants.setForeground(attr, selectedColor);
				setCharacterAttributes(editor, attr, false);
				
				textPaneThemeslide.restoreLastCaretPosition();
			}

		}

	}
	
	/**
	 * generates a new FontColor changed event to handle the selection in the Color Picker
	 * @param textColorToSet
	 */
	public void textColorUpdated(Color textColorToSet) {
		new FontColorAction(textColorToSet).actionPerformed(null);
	}
	
	/**
	 * This method is called when the Caret Posiotion changes.
	 * Two cases are managed. The first one is the general one it simply calls the updateGuitoStyleAttr method.
	 * The second case handles the event if the caret was at its last position and does not changed the position since last event invokation
	 * Only then a update to the Gui is performend with the styling attributes of the last character in the document.
	 * 
	 * @param e CaretEvent
	 */
	public void caretPositionUpdated(CaretEvent e) {
		
		
		if(lastEventCaretPos == textPaneThemeslide.getCaretPosition()) {
			lastEventCaretPosChanged = false;
		} else {
			lastEventCaretPosChanged = true;
		}
		lastEventCaretPos = textPaneThemeslide.getCaretPosition();
		
		
		AttributeSet attr = textPaneThemeslide.getCharacterAttributes();
		if(attr != null) {

			if(!(textPaneThemeslide.getCaretPosition() == textPaneThemeslide.getDocument().getEndPosition().getOffset() - 1)){			
				updateGUItoStyleAttr(attr);
				
			} else {
				if(lastEventCaretPosChanged) {
					Element elt = textPaneThemeslide.getStyledDocument().getCharacterElement(textPaneThemeslide.getDocument().getEndPosition().getOffset() - 2);
					attr = elt.getAttributes();	
					updateGUItoStyleAttr(attr);
				}
						
			}
			
		}
		
	}

	/**
	 * This method performs GUI updates to all relevant styling elements represented by the GUI e.g. the "bold button".
	 * It is invoked on every caret position changed.
	 * 
	 * @param attr {@link AttributeSet} which contains the attributes the GUI should sync to
	 */
	private void updateGUItoStyleAttr(AttributeSet attr) {
		
		//TODO if all text in pane is deleted fall back to the defaults set -> GUI updating
		
		if(StyleConstants.isBold(attr)) {
			jToggleButtonBold.setSelected(true);
		} else {
			jToggleButtonBold.setSelected(false);
		}
		
		if(StyleConstants.isUnderline(attr)) {
			jToggleButtonUnderline.setSelected(true);
		} else {
			jToggleButtonUnderline.setSelected(false);
		}
		
		if(StyleConstants.isItalic(attr)) {
			jToggleButtonItalic.setSelected(true);
		} else {
			jToggleButtonItalic.setSelected(false);
		}

		String[] sizes = Constants.FONT_SIZES;
		
		for(int i = 0; i < sizes.length; i++) {

			int currentSize;
			try {
				currentSize = Integer.parseInt(sizes[i].substring(0, sizes[i].length() - 2));
				if(attr.containsAttribute(StyleConstants.FontSize, currentSize)) {
					evtFromGUIupdateFontSize = true;
					jComboBoxFontSize.setSelectedItem(sizes[i]);
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		String[] fonts = Constants.FONT_FAMILIES;
		
		for(int i = 0; i < fonts.length; i++) {
			
			if(attr.containsAttribute(StyleConstants.FontFamily, fonts[i])) {
				evtFromGUIupdateFontFamily = true;
				jComboBoxFontType.setSelectedItem(fonts[i]);
			}

		}

	}
 

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
            java.util.logging.Logger.getLogger(ThemeslideCreatorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThemeslideCreatorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThemeslideCreatorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThemeslideCreatorFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ThemeslideCreatorFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonAddAndShow;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonColorPicker;
    private javax.swing.JComboBox jComboBoxFontSize;
    private javax.swing.JComboBox jComboBoxFontType;
    private javax.swing.JComboBox jComboBoxPriority;
    private javax.swing.JComboBox jComboBoxTheme;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelAnchor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldMarginLeft;
    private javax.swing.JTextField jTextFieldMarginTop;
    private javax.swing.JTextField jTextFieldThemeSlideName;
    private javax.swing.JToggleButton jToggleButtonBold;
    private javax.swing.JToggleButton jToggleButtonItalic;
    private javax.swing.JToggleButton jToggleButtonUnderline;
    private de.netprojectev.GUI.Themeslide.TextPanePanel textPanePanel1;
    private de.netprojectev.GUI.Themeslide.TextPaneThemeslide textPaneThemeslide;
    // End of variables declaration//GEN-END:variables


}
