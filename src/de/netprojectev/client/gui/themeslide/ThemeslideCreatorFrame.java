/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.netprojectev.client.gui.themeslide;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Insets;
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
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
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

import old.de.netprojectev.client.gui.dialogs.ColorPickerDialog;

import de.netprojectev.client.ConstantsClient;
import de.netprojectev.client.gui.models.PriorityComboBoxModel;
import de.netprojectev.client.model.PreferencesModelClient;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.datastructures.media.Theme;
import de.netprojectev.exceptions.PriorityDoesNotExistException;
import de.netprojectev.exceptions.ThemeDoesNotExistException;
import de.netprojectev.misc.Misc;
import de.netprojectev.server.datastructures.ImageFile;
import de.netprojectev.server.datastructures.ServerMediaFile;
import de.netprojectev.server.datastructures.Themeslide;


/**
 * This is a GUI class to create and design a new Themeslide.
 * It contains a small text editor and the possibilty to select a Theme (background).
 * @author samu
 *
 */
public class ThemeslideCreatorFrame extends javax.swing.JFrame {
	
	private static final long serialVersionUID = -3653577264825548156L;
	
	private final PreferencesModelClient prefs;
	private final ClientMessageProxy proxy;
	
	private Boolean evtFromGUIupdateFontSize = false;
	private Boolean evtFromGUIupdateFontFamily = false;

	private Boolean lastEventCaretPosChanged = false;
	private int lastEventCaretPos = 0;
	
	private Color selectedColorMain;
		
    public ThemeslideCreatorFrame(Frame parent, ClientMessageProxy proxy) throws ThemeDoesNotExistException, PriorityDoesNotExistException {
    	
    	this.prefs = proxy.getPrefs();
    	this.proxy = proxy;
    	setLocationRelativeTo(parent);
    	initComponents();
    	   
    	
    	Theme[] themes = prefs.themesAsArray();
    	
    	//TODO make the same for themes like for prios conecering the model
        for(int i = 0; i < themes.length; i++) {
        	jComboBoxTheme.addItem(themes[i].getName());
        }
        
        jComboBoxPriority.setModel(new PriorityComboBoxModel(prefs));
        
        String[] font = ConstantsClient.FONT_FAMILIES;
        for(int i = 0; i < font.length; i++) {
        	jComboBoxFontType.addItem(font[i]);
        }
        
        //TODO check all props used here for consistency
        
        jComboBoxFontType.setSelectedItem(PreferencesModelClient.getClientPropertyByKey(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTTYPE));
        
        for(int i = 0; i < ConstantsClient.FONT_SIZES.length; i++) {
        	jComboBoxFontSize.addItem(ConstantsClient.FONT_SIZES[i]);
        }
        jComboBoxFontSize.setSelectedItem(PreferencesModelClient.getClientPropertyByKey(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTSIZE));

        jTextFieldMarginLeft.setText(PreferencesModelClient.getClientPropertyByKey(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINLEFT));
        jTextFieldMarginTop.setText(PreferencesModelClient.getClientPropertyByKey(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_MARGINTOP));
        
        jComboBoxFontTypeActionPerformed(new ActionEvent(textPaneThemeslide, 0, ""));
        jComboBoxFontSizeActionPerformed(new ActionEvent(textPaneThemeslide, 0, ""));
        
    	selectedColorMain = new Color(Integer.parseInt(PreferencesModelClient.getClientPropertyByKey(ConstantsClient.PROP_THEMESLIDECREATOR_PRESETTINGS_FONTCOLOR)));
    	System.out.println(selectedColorMain);
        jButtonColorPicker.setBackground(new Color(selectedColorMain.getRGB()));
        jButtonColorPicker.setForeground(new Color(selectedColorMain.getRGB()));
        new FontColorAction(new Color(selectedColorMain.getRGB())).actionPerformed(null);
        applyAsParagraphAttributes();
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
        textPanePanel1 = new de.netprojectev.client.gui.themeslide.TextPanePanel();
        textPaneThemeslide = new de.netprojectev.client.gui.themeslide.TextPaneThemeslide();
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
    	boolean success = false;
		try {
			success = addThemeslide();
		} catch (PriorityDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ThemeDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(success) {
    		dispose();
    	}
    }                                          

    private void jButtonAddAndShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddAndShowActionPerformed
    	boolean success = false;
		try {
			success = addThemeslide();
		} catch (PriorityDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ThemeDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(success) {
    		//TODO Add and show
    		dispose();
    	}
    }//GEN-LAST:event_jButtonAddAndShowActionPerformed

    /**
     * on selection change, change the background image to the new selected themes background
     * @param evt
     */
    private void jComboBoxThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxThemeActionPerformed
    	try {
			textPaneThemeslide.setThemeBackground(prefs.getThemeAt(jComboBoxTheme.getSelectedIndex()).getBackgroundImage());
		} catch (ThemeDoesNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }//GEN-LAST:event_jComboBoxThemeActionPerformed

    /**
     * changing the selected text to bold
     * @param evt
     */
    private void jToggleButtonBoldActionPerformed(java.awt.event.ActionEvent evt) {                                                   
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
    	ColorPickerDialog colorPicker = new ColorPickerDialog(this, true);
    	colorPicker.setVisible(true);
    	selectedColorMain = colorPicker.getChoosenColor();
        jButtonColorPicker.setBackground(selectedColorMain);
        jButtonColorPicker.setForeground(selectedColorMain);
    	new FontColorAction(selectedColorMain).actionPerformed(null);
    }//GEN-LAST:event_jButtonColorPickerActionPerformed

    /**
     * handles the adding of the new themeslide to the media handler.
     * checks all data and adds it if everything is fine, else showing an error dialog
     * @throws PriorityDoesNotExistException 
     * @throws ThemeDoesNotExistException 
     */
    private boolean addThemeslide() throws PriorityDoesNotExistException, ThemeDoesNotExistException {
    	
    	Priority priority = null;
    	Theme theme = null;
    	String name = null;

    	if(!jTextFieldThemeSlideName.getText().isEmpty() && jTextFieldThemeSlideName.getText() != null) {
    		name = jTextFieldThemeSlideName.getText();
    	} else {
    		JOptionPane.showMessageDialog(this, "Please enter a name.", "Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    	if(jComboBoxPriority.getSelectedIndex() >= 0) {
    		priority = prefs.getPriorityAt(jComboBoxPriority.getSelectedIndex());
    	} else {
    		JOptionPane.showMessageDialog(this, "Please select a priority.", "Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    	if(jComboBoxTheme.getSelectedIndex() >= 0) {
    		theme = prefs.getThemeAt(jComboBoxTheme.getSelectedIndex());
    	} else {
    		JOptionPane.showMessageDialog(this, "Please select a theme.", "Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}
    	    	
    	if(name != null && priority != null && theme != null) {

    		Themeslide themeslide = new Themeslide(name, theme.getId(), priority);
    		themeslide.setImageRepresantation(new ImageFile(name, new ImageIcon(generatePNGRepresentation(themeslide)), priority));
    		
    		proxy.sendAddThemeSlide(themeslide);
    		
    		/*
    		ServerMediaFile[] themeSlides = new ServerMediaFile[1];
    		themeSlides[0] = new Themeslide(name, priority, theme);
    		generatePNGRepresentation(themeSlides[0]);
    		MediaHandlerOld.getInstance().add(themeSlides);
    		
    		((Themeslide) themeSlides[0]).createNewImageFileRepresentation();*/
    		return true;
    	} else {
    		JOptionPane.showMessageDialog(this, "Error while reading data.", "Error", JOptionPane.ERROR_MESSAGE);
    		return false;
    	}

    }

	private BufferedImage generatePNGRepresentation(Themeslide themeSlide) {
    	
    	String fileName = themeSlide.getId().toString() + ".png";
    	
        int w = textPaneThemeslide.getWidth();
        int h = textPaneThemeslide.getHeight();
        
        BufferedImage bufImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D tmpG2D = bufImage.createGraphics();

        tmpG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        tmpG2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        tmpG2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        tmpG2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        tmpG2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        tmpG2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        textPaneThemeslide.paint(tmpG2D);
        
        /*
        Iterator<ImageWriter> itereratorImageWriter = ImageIO.getImageWritersByFormatName("png");
        ImageWriter writer = (ImageWriter) itereratorImageWriter.next();
        ImageWriteParam writeParams = writer.getDefaultWriteParam();

        try {
            FileImageOutputStream fos = new FileImageOutputStream(new File(savePath + fileName));
            writer.setOutput(fos);
            IIOImage img = new IIOImage((RenderedImage) bufImage, null, null);
            writer.write(null, img, writeParams);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        */
        tmpG2D.dispose();
        return bufImage;
	}
    
    
    /**
     * when margin left textfield changed, this method changes the left margin of the JTextPane
     */
	private void marginLeftModified() {
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
				
				StyledEditorKit kit = getStyledEditorKit(editor);
				MutableAttributeSet attr = kit.getInputAttributes();
				boolean bold = (StyleConstants.isBold(attr)) ? false : true;
				SimpleAttributeSet sas = new SimpleAttributeSet();
				StyleConstants.setBold(sas, bold);
				setCharacterAttributes(editor, sas, false);
				textPaneThemeslide.requestFocus();

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
				
				StyledEditorKit kit = getStyledEditorKit(editor);
				MutableAttributeSet attr = kit.getInputAttributes();
				boolean italic = (StyleConstants.isItalic(attr)) ? false : true;
				SimpleAttributeSet sas = new SimpleAttributeSet();
				StyleConstants.setItalic(sas, italic);
				setCharacterAttributes(editor, sas, false);

				textPaneThemeslide.requestFocus();
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
				
				StyledEditorKit kit = getStyledEditorKit(editor);
				MutableAttributeSet attr = kit.getInputAttributes();
				boolean underline = (StyleConstants.isUnderline(attr)) ? false : true;
				SimpleAttributeSet sas = new SimpleAttributeSet();
				StyleConstants.setUnderline(sas, underline);
				setCharacterAttributes(editor, sas, false);
				textPaneThemeslide.requestFocus();
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

		
		//TODO Bug: Setting font size then font type leads to reset of size, maybe the other way round too
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
				
				MutableAttributeSet attr = new SimpleAttributeSet();
				StyleConstants.setFontFamily(attr, selectedFamily);
				setCharacterAttributes(editor, attr, false);
				textPaneThemeslide.requestFocus();
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
				
				MutableAttributeSet attr = new SimpleAttributeSet();
	            attr.addAttribute(StyleConstants.Size, selectedFontSize);
				setCharacterAttributes(editor, attr, false);

				textPaneThemeslide.requestFocus();
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
		
		private Color selectedColor = selectedColorMain;
		
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
			if (editor != null && selectedColor != null) {
				
				MutableAttributeSet attr = new SimpleAttributeSet();
				StyleConstants.setForeground(attr, selectedColor);
				setCharacterAttributes(editor, attr, false);
				textPaneThemeslide.requestFocus();
			}

		}

	}

	/**
	 * This method is called when the Caret Position changes.
	 * Two cases are managed. The first one is the general one it simply calls the updateGuitoStyleAttr method.
	 * The second case handles the event if the caret was at its last position and does not changed the position since last event invokation
	 * Only then a update to the GUI is performed with the styling attributes of the last character in the document.
	 * 
	 * @param e CaretEvent
	 */
	public void caretPositionUpdated(CaretEvent e) {
		
		//TODO fix focus request after selecting something with keyboard from comboboxes (fires the "requestfocus")
		
		int currentParagraphLength = textPaneThemeslide.getStyledDocument().getParagraphElement(textPaneThemeslide.getCaretPosition()).getDocument().getLength();
		//TODO 
		/*
		 * last worked here, trying to fix the themeslide creator behavior
		 * at the moment there are false syncs and due to para attr setting, sometimes smaller 
		 * fonts needs much more space
		 * Maybe correcting para attr with setAtr(null) to the right times
		 * 
		 * setting the "logicalStyle" of the TextPane could be another solution
		 */
		
		if(lastEventCaretPos == textPaneThemeslide.getCaretPosition()) {
			lastEventCaretPosChanged = false;
		} else {
			lastEventCaretPosChanged = true;
		}
		lastEventCaretPos = textPaneThemeslide.getCaretPosition();

		AttributeSet attr = (textPaneThemeslide.getStyledDocument().getCharacterElement(textPaneThemeslide.getCaretPosition() - 1)).getAttributes();
		if(attr != null) {

			if(!(textPaneThemeslide.getCaretPosition() == textPaneThemeslide.getDocument().getEndPosition().getOffset() - 1)) {	
				
				updateGUItoStyleAttr(attr);
				
			} else {
				if(lastEventCaretPosChanged) {
					if(currentParagraphLength == 0) {
					
						applyAsParagraphAttributes();
						
					} else {
						Element elt = textPaneThemeslide.getStyledDocument().getCharacterElement(textPaneThemeslide.getDocument().getEndPosition().getOffset() - 2);
						attr = elt.getAttributes();	
						updateGUItoStyleAttr(attr);
					}
				}
						
			}
			
		}
		
	}

	private void applyAsParagraphAttributes() {
		final MutableAttributeSet attr = new SimpleAttributeSet();
		StyleConstants.setFontFamily(attr, (String) jComboBoxFontType.getSelectedItem());
		attr.addAttribute(StyleConstants.Size, (String) jComboBoxFontSize.getSelectedItem());
		StyleConstants.setForeground(attr, new Color(selectedColorMain.getRGB()));
		StyleConstants.setUnderline(attr, jToggleButtonUnderline.isSelected());
		StyleConstants.setBold(attr, jToggleButtonBold.isSelected());
		StyleConstants.setItalic(attr, jToggleButtonItalic.isSelected());
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				textPaneThemeslide.setParagraphAttributes(attr, false);
				
			}
		});

	}

	/**
	 * This method performs GUI updates to all relevant styling elements represented by the GUI e.g. the "bold button".
	 * It is invoked on every caret position changed.
	 * 
	 * @param attr {@link AttributeSet} which contains the attributes the GUI should sync to
	 */
	private void updateGUItoStyleAttr(final AttributeSet attr) {
				
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
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

				String[] sizes = ConstantsClient.FONT_SIZES;
				
				for(int i = 0; i < sizes.length; i++) {

					int currentSize;
					try {
						currentSize = Integer.parseInt(sizes[i].substring(0, sizes[i].length() - 2));
						if(attr.containsAttribute(StyleConstants.FontSize, currentSize)) {
							evtFromGUIupdateFontSize = true;
							jComboBoxFontSize.setSelectedItem(sizes[i]);
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
					
				}
				
				String[] fonts = ConstantsClient.FONT_FAMILIES;
				
				for(int i = 0; i < fonts.length; i++) {
					
					if(attr.containsAttribute(StyleConstants.FontFamily, fonts[i])) {
						evtFromGUIupdateFontFamily = true;
						jComboBoxFontType.setSelectedItem(fonts[i]);
					}

				}
				
			}
		});
		
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
                try {
					new ThemeslideCreatorFrame(null, null).setVisible(true);
				} catch (ThemeDoesNotExistException | PriorityDoesNotExistException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
    private de.netprojectev.client.gui.themeslide.TextPanePanel textPanePanel1;
    private de.netprojectev.client.gui.themeslide.TextPaneThemeslide textPaneThemeslide;
    // End of variables declaration//GEN-END:variables


	
	public void setTextPanePanel1(de.netprojectev.client.gui.themeslide.TextPanePanel textPanePanel1) {
		this.textPanePanel1 = textPanePanel1;
		repaint();
		
	}


}
