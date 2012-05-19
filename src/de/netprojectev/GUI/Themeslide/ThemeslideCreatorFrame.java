/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.netprojectev.GUI.Themeslide;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import de.netprojectev.Media.MediaFile;
import de.netprojectev.Media.Priority;
import de.netprojectev.Media.Theme;
import de.netprojectev.Media.Themeslide;
import de.netprojectev.MediaHandler.MediaHandler;
import de.netprojectev.Misc.Constants;
import de.netprojectev.Misc.Misc;
import de.netprojectev.Preferences.PreferencesHandler;

/**
 *
 * 
 */
public class ThemeslideCreatorFrame extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3653577264825548156L;
	
	private StyledDocument styledDoc;
	private Style style;
	
	private int anchorWidth = 0;
	private int anchorHeight = 0;
	

	/**
     * Creates new form ThemeslideCreator
     */
    public ThemeslideCreatorFrame() {
    	initComponents();
    	
        for(int i = 0; i < PreferencesHandler.getInstance().getListOfThemes().size(); i++) {
        	jComboBoxTheme.addItem(PreferencesHandler.getInstance().getListOfThemes().get(i).getName());
        }
        for(int i = 0; i < PreferencesHandler.getInstance().getListOfPriorities().size(); i++) {
        	jComboBoxPriority.addItem(PreferencesHandler.getInstance().getListOfPriorities().get(i).getName());
        }
        String[] font = Constants.fontsFamilies;
        for(int i = 0; i < font.length; i++) {
        	jComboBoxFontType.addItem(font[i]);
        }

        for(int i = 0; i < Constants.fontSizes.length; i++) {
        	jComboBoxFontSize.addItem(Constants.fontSizes[i]);
        }
        
        jComboBoxFontType.setSelectedItem(jTextPaneText.getFont().getFamily());
        jComboBoxFontSize.setSelectedItem(jTextPaneText.getFont().getSize() + "px");
        
        styledDoc = jTextPaneText.getStyledDocument();
        
        style=jTextPaneText.addStyle("bold",null);
    	StyleConstants.setBold(style,true);
    	
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
        jScrollPane2 = new javax.swing.JScrollPane();
        previewThemeslideComponent1 = new de.netprojectev.GUI.Themeslide.PreviewThemeslideComponent();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPaneText = new javax.swing.JTextPane();
        jComboBoxFontSize = new javax.swing.JComboBox();
        jComboBoxFontType = new javax.swing.JComboBox();
        jToggleButtonBold = new javax.swing.JToggleButton();
        jLabelAnchor = new javax.swing.JLabel();
        jTextFieldAnchorWidth = new javax.swing.JTextField();
        jTextFieldAnchorHeight = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Themeslide Creator");

        jLabel3.setText("Choose Theme");

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

        jComboBoxPriority.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxPriorityActionPerformed(evt);
            }
        });

        jScrollPane3.setBorder(null);
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane3.setOpaque(false);

        jTextPaneText.setBorder(null);
        jTextPaneText.setContentType("text/html");
        jTextPaneText.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jTextPaneText.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jTextPaneText.setMaximumSize(previewThemeslideComponent1.getSize());
        jTextPaneText.setOpaque(false);
        jScrollPane3.setViewportView(jTextPaneText);

        javax.swing.GroupLayout previewThemeslideComponent1Layout = new javax.swing.GroupLayout(previewThemeslideComponent1);
        previewThemeslideComponent1.setLayout(previewThemeslideComponent1Layout);
        previewThemeslideComponent1Layout.setHorizontalGroup(
            previewThemeslideComponent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1027, Short.MAX_VALUE)
            .addGroup(previewThemeslideComponent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1027, Short.MAX_VALUE))
        );
        previewThemeslideComponent1Layout.setVerticalGroup(
            previewThemeslideComponent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 511, Short.MAX_VALUE)
            .addGroup(previewThemeslideComponent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
        );

        jScrollPane3.getViewport().setOpaque(false);

        jScrollPane2.setViewportView(previewThemeslideComponent1);

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
        jToggleButtonBold.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButtonBoldActionPerformed(evt);
            }
        });

        jLabelAnchor.setText("Anchor");

        jTextFieldAnchorWidth.setText("0");
        jTextFieldAnchorWidth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAnchorWidthActionPerformed(evt);
            }
        });

        jTextFieldAnchorHeight.setText("0");
        jTextFieldAnchorHeight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAnchorHeightActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToggleButtonBold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAddAndShow)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBoxFontSize, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxFontType, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelAnchor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldAnchorWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldAnchorHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
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
                    .addComponent(jScrollPane2)
                    .addComponent(jSeparator1))
                .addContainerGap())
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
                    .addComponent(jTextFieldAnchorWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAnchorHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancel)
                    .addComponent(jButtonAdd)
                    .addComponent(jButtonAddAndShow))
                .addContainerGap())
        );

        jTextFieldAnchorWidth.getDocument().addDocumentListener(new DocumentListener() {@Override
            public void insertUpdate(DocumentEvent e) {
                anchorWidthModified();

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                anchorWidthModified();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                anchorWidthModified();

            }});
            jTextFieldAnchorHeight.getDocument().addDocumentListener(new DocumentListener() {@Override
                public void insertUpdate(DocumentEvent e) {
                    anchorHeightModified();

                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    anchorHeightModified();

                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    anchorHeightModified();

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

    private void jComboBoxThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxThemeActionPerformed

    	previewThemeslideComponent1.setThemeBackground(PreferencesHandler.getInstance().getListOfThemes().get(jComboBoxTheme.getSelectedIndex()).getBackgroundImage());
    	
    }//GEN-LAST:event_jComboBoxThemeActionPerformed

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

    private void jComboBoxPriorityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxPriorityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxPriorityActionPerformed

    private void jToggleButtonBoldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButtonBoldActionPerformed

    	if(jTextPaneText.getSelectedText() != null && !jTextPaneText.getSelectedText().isEmpty()) {
    		int length = jTextPaneText.getSelectedText().length();
        	styledDoc.setCharacterAttributes(jTextPaneText.getSelectionStart(), length, jTextPaneText.getStyle("bold"), false);
    	}
    	
    }//GEN-LAST:event_jToggleButtonBoldActionPerformed

    private void jComboBoxFontSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFontSizeActionPerformed

    	if(jTextPaneText.getSelectedText() != null && !jTextPaneText.getSelectedText().isEmpty()) {
    		int length = jTextPaneText.getSelectedText().length();
        	SimpleAttributeSet sattr = new SimpleAttributeSet();
            sattr.addAttribute(StyleConstants.Size, (String) jComboBoxFontSize.getSelectedItem());
            styledDoc.setCharacterAttributes(jTextPaneText.getSelectionStart(), length, sattr, false);
    	}

    }//GEN-LAST:event_jComboBoxFontSizeActionPerformed

    private void jComboBoxFontTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFontTypeActionPerformed
        
    	if(jTextPaneText.getSelectedText() != null && !jTextPaneText.getSelectedText().isEmpty()) {
	    	int length = jTextPaneText.getSelectedText().length();
	    	SimpleAttributeSet sas = new SimpleAttributeSet();
	        StyleConstants.setFontFamily(sas, (String) jComboBoxFontType.getSelectedItem());
	        styledDoc.setCharacterAttributes(jTextPaneText.getSelectionStart(), length, sas, false);
    	}

    }//GEN-LAST:event_jComboBoxFontTypeActionPerformed

    private void jTextFieldAnchorWidthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAnchorWidthActionPerformed
    	
    	anchorWidthModified();

    }//GEN-LAST:event_jTextFieldAnchorWidthActionPerformed

	private void anchorWidthModified() {
		//TODO not working if frame smaller than set anchor
		if(!jTextFieldAnchorWidth.getText().isEmpty()) {
	    	try {
				if(Integer.parseInt(jTextFieldAnchorWidth.getText()) > 0) {

					jTextPaneText.setBounds(Integer.parseInt(jTextFieldAnchorWidth.getText()), (int) jTextPaneText.getLocation().getY(), ((int) jTextPaneText.getBounds().getWidth()) - Integer.parseInt(jTextFieldAnchorWidth.getText()), (int) jTextPaneText.getBounds().getHeight());
					
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}

    private void jTextFieldAnchorHeightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAnchorHeightActionPerformed
        
    	anchorHeightModified();
    	
    	
    }//GEN-LAST:event_jTextFieldAnchorHeightActionPerformed

	private void anchorHeightModified() {
		//TODO not working if frame smaller than set anchor (setting anchor needs a resize of textpane)
		if(!jTextFieldAnchorHeight.getText().isEmpty()) {
			try {
				if(Integer.parseInt(jTextFieldAnchorHeight.getText()) > 0) {

					jTextPaneText.setBounds((int) jTextPaneText.getLocation().getX(), Integer.parseInt(jTextFieldAnchorHeight.getText()),(int) jTextPaneText.getBounds().getWidth(), ((int) jTextPaneText.getBounds().getHeight()) - Integer.parseInt(jTextFieldAnchorHeight.getText()));
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
    	
    	
	}
    
    private void addThemeslide() {
    	Priority priority = null;
    	Theme theme = null;
    	String name = null;
    	
    	try {
			anchorHeight = Integer.parseInt(jTextFieldAnchorHeight.getText());
		} catch (NumberFormatException e) {
			anchorHeight = 0;
			//e.printStackTrace();
		}
    	try {
			anchorWidth = Integer.parseInt(jTextFieldAnchorWidth.getText());
		} catch (NumberFormatException e) {
			anchorWidth = 0;
			//e.printStackTrace();
		}
    	
    	
    	
    	if(!jTextFieldThemeSlideName.getText().isEmpty() && jTextFieldThemeSlideName.getText() != null) {
    		name  = jTextFieldThemeSlideName.getText();
    	} else {
    		//TODO Error dialog
    	}
    	if(jComboBoxPriority.getSelectedIndex() >= 0) {
    		priority = PreferencesHandler.getInstance().getListOfPriorities().get(jComboBoxPriority.getSelectedIndex());
    	} else {
    		//TODO Error dialog
    	}
    	if(jComboBoxTheme.getSelectedIndex() >= 0) {
    		theme = PreferencesHandler.getInstance().getListOfThemes().get(jComboBoxTheme.getSelectedIndex());
    	} else {
    		//TODO Error dialog
    	}
    	    	
    	if(name != null && priority != null && theme != null) {
    		MediaFile[] themeSlides = new MediaFile[1];
    		themeSlides[0] = new Themeslide(name, priority, theme, jTextPaneText, new Point(anchorWidth, anchorHeight));
    		MediaHandler.getInstance().add(themeSlides);
    	} else {
    		//TODO Error dialog
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
    private javax.swing.JComboBox jComboBoxFontSize;
    private javax.swing.JComboBox jComboBoxFontType;
    private javax.swing.JComboBox jComboBoxPriority;
    private javax.swing.JComboBox jComboBoxTheme;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelAnchor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextFieldAnchorHeight;
    private javax.swing.JTextField jTextFieldAnchorWidth;
    private javax.swing.JTextField jTextFieldThemeSlideName;
    private javax.swing.JTextPane jTextPaneText;
    private javax.swing.JToggleButton jToggleButtonBold;
    private de.netprojectev.GUI.Themeslide.PreviewThemeslideComponent previewThemeslideComponent1;
    // End of variables declaration//GEN-END:variables


	public int getAnchorHeight() {
		return anchorHeight;
	}

	public void setAnchorHeight(int anchorHeight) {
		this.anchorHeight = anchorHeight;
		jTextFieldAnchorHeight.setText(Integer.toString(anchorHeight));
	}

	public int getAnchorWidth() {
		return anchorWidth;
	}

	public void setAnchorWidth(int anchorWidth) {
		this.anchorWidth = anchorWidth;
		jTextFieldAnchorWidth.setText(Integer.toString(anchorWidth));
	}
}
