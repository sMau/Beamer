/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.netprojectev.GUI.Preferences;

import java.io.File;

import de.netprojectev.GUI.Main.ManagerFrame;
import de.netprojectev.Media.Priority;
import de.netprojectev.Media.Theme;
import de.netprojectev.Misc.Misc;
import de.netprojectev.Preferences.PreferencesHandler;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author samu
 */
public class PreferencesFrame extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5332162076303310401L;
	private PreferencesHandler preferenceshandler;
	private Priority selectedPrio;
	private Theme selectedTheme;
	private ManagerFrame managerFrame;
	private File selectedImage;
	
    public File getSelectedImage() {
		return selectedImage;
	}

	public void setSelectedImage(File selectedImage) {
		this.selectedImage = selectedImage;
	}

	public javax.swing.JList getjList2() {
		return jListTheme;
	}

	public void setjList2(javax.swing.JList jList2) {
		this.jListTheme = jList2;
	}

	/**
     * Creates new form PreferencesFrame
     */
    public PreferencesFrame(ManagerFrame managerFrame) {
    	
    	this.managerFrame = managerFrame;
    	this.preferenceshandler = PreferencesHandler.getInstance(); 
    	preferenceshandler.setPreferencesFrame(this);
    	preferenceshandler.setManagerFrame(managerFrame);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooserBgImageTheme = new javax.swing.JFileChooser();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanelTabMain = new javax.swing.JPanel();
        jButtonReset = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabelTickerSpeed = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        jPanelTabPrio = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListPrio = new javax.swing.JList();
        jLabelPrioName = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldPrioName = new javax.swing.JTextField();
        jTextFieldPrioMin = new javax.swing.JTextField();
        jButtonPrioSave = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnAddPrio = new javax.swing.JButton();
        btnRemovePrio = new javax.swing.JButton();
        jPanelTabTheme = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListTheme = new javax.swing.JList();
        jLabelThemeName = new javax.swing.JLabel();
        jLabelThemeBg = new javax.swing.JLabel();
        jTextFieldThemeName = new javax.swing.JTextField();
        jTextFieldThemeBgImg = new javax.swing.JTextField();
        btnSaveTheme = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        btnAddTheme = new javax.swing.JButton();
        btnRemoveTheme = new javax.swing.JButton();
        jButtonChooseBgImage = new javax.swing.JButton();
        jButtonCancelPrefs = new javax.swing.JButton();
        jButtonApplyPrefs = new javax.swing.JButton();

        jFileChooserBgImageTheme.setDialogTitle("Theme background");
        jFileChooserBgImageTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooserBgImageThemeActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Preferences");

        jButtonReset.setText("Reset Data");

        jCheckBox1.setText("Load always on start");
        jCheckBox1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jCheckBox2.setText("Save always on close");
        jCheckBox2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabelTickerSpeed.setText("Ticker Speed");

        javax.swing.GroupLayout jPanelTabMainLayout = new javax.swing.GroupLayout(jPanelTabMain);
        jPanelTabMain.setLayout(jPanelTabMainLayout);
        jPanelTabMainLayout.setHorizontalGroup(
            jPanelTabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabMainLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonReset))
                    .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelTabMainLayout.createSequentialGroup()
                        .addComponent(jLabelTickerSpeed)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 193, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelTabMainLayout.setVerticalGroup(
            jPanelTabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTabMainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelTabMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelTabMainLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabelTickerSpeed)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 141, Short.MAX_VALUE)
                .addComponent(jButtonReset)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Main", jPanelTabMain);

        jListPrio.setModel(new PriorityListModel());
        jListPrio.getSelectionModel().addListSelectionListener(
            new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent event) {
                    int viewRow = jListPrio.getSelectedIndex();
                    if(viewRow != -1) {
                        selectedPrio = preferenceshandler.getListOfPriorities().get(viewRow);
                        jTextFieldPrioName.setText(selectedPrio.getName());
                        jTextFieldPrioMin.setText(Integer.toString(selectedPrio.getMinutesToShow()));
                    }
                }
            }
        );
        jScrollPane1.setViewportView(jListPrio);

        jLabelPrioName.setText("Name");

        jLabel2.setText("Minutes");

        jButtonPrioSave.setText("Save");
        jButtonPrioSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrioSaveActionPerformed(evt);
            }
        });

        btnAddPrio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/plus_2.png"))); // NOI18N
        btnAddPrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPrioActionPerformed(evt);
            }
        });

        btnRemovePrio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/delete_2.png"))); // NOI18N
        btnRemovePrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemovePrioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTabPrioLayout = new javax.swing.GroupLayout(jPanelTabPrio);
        jPanelTabPrio.setLayout(jPanelTabPrioLayout);
        jPanelTabPrioLayout.setHorizontalGroup(
            jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabPrioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanelTabPrioLayout.createSequentialGroup()
                        .addGroup(jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanelTabPrioLayout.createSequentialGroup()
                                        .addComponent(jLabelPrioName)
                                        .addGap(38, 38, 38)
                                        .addComponent(jTextFieldPrioName, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanelTabPrioLayout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(23, 23, 23)
                                        .addComponent(jTextFieldPrioMin)))
                                .addComponent(jButtonPrioSave))
                            .addGroup(jPanelTabPrioLayout.createSequentialGroup()
                                .addComponent(btnAddPrio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnRemovePrio)))
                        .addGap(0, 137, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelTabPrioLayout.setVerticalGroup(
            jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabPrioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTabPrioLayout.createSequentialGroup()
                        .addGroup(jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelPrioName)
                            .addComponent(jTextFieldPrioName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextFieldPrioMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonPrioSave)
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                        .addGroup(jPanelTabPrioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRemovePrio, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAddPrio, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Priority", jPanelTabPrio);

        jListTheme.setModel(new ThemeListModel());
        jListTheme.getSelectionModel().addListSelectionListener(
            new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent event) {
                    int viewRow = jListTheme.getSelectedIndex();
                    if(viewRow != -1) {
                    	selectedTheme = preferenceshandler.getListOfThemes().get(viewRow);
                    	jTextFieldThemeName.setText(selectedTheme.getName());
                        jTextFieldThemeBgImg.setText(selectedTheme.getBackgroundImage().getAbsolutePath());
                    }

                }
            }
        );
        jScrollPane2.setViewportView(jListTheme);

        jLabelThemeName.setText("Name");

        jLabelThemeBg.setText("Background");

        jTextFieldThemeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldThemeNameActionPerformed(evt);
            }
        });

        jTextFieldThemeBgImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldThemeBgImgActionPerformed(evt);
            }
        });

        btnSaveTheme.setText("Save");
        btnSaveTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveThemeActionPerformed(evt);
            }
        });

        btnAddTheme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/plus_2.png"))); // NOI18N
        btnAddTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddThemeActionPerformed(evt);
            }
        });

        btnRemoveTheme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/delete_2.png"))); // NOI18N
        btnRemoveTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveThemeActionPerformed(evt);
            }
        });

        jButtonChooseBgImage.setText("Choose");
        jButtonChooseBgImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseBgImageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTabThemeLayout = new javax.swing.GroupLayout(jPanelTabTheme);
        jPanelTabTheme.setLayout(jPanelTabThemeLayout);
        jPanelTabThemeLayout.setHorizontalGroup(
            jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabThemeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(jPanelTabThemeLayout.createSequentialGroup()
                        .addGroup(jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelTabThemeLayout.createSequentialGroup()
                                .addComponent(btnAddTheme)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnRemoveTheme))
                            .addGroup(jPanelTabThemeLayout.createSequentialGroup()
                                .addGroup(jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnSaveTheme)
                                    .addGroup(jPanelTabThemeLayout.createSequentialGroup()
                                        .addGroup(jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabelThemeName)
                                            .addComponent(jLabelThemeBg))
                                        .addGap(23, 23, 23)
                                        .addGroup(jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jTextFieldThemeBgImg)
                                            .addComponent(jTextFieldThemeName, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonChooseBgImage)))
                        .addGap(0, 37, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelTabThemeLayout.setVerticalGroup(
            jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTabThemeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTabThemeLayout.createSequentialGroup()
                        .addGroup(jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelThemeName)
                            .addComponent(jTextFieldThemeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelThemeBg)
                            .addComponent(jTextFieldThemeBgImg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonChooseBgImage))
                        .addGap(11, 11, 11)
                        .addComponent(btnSaveTheme)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelTabThemeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRemoveTheme, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAddTheme, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Theme", jPanelTabTheme);

        jButtonCancelPrefs.setText("Cancel");
        jButtonCancelPrefs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelPrefsActionPerformed(evt);
            }
        });

        jButtonApplyPrefs.setText("Apply");
        jButtonApplyPrefs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApplyPrefsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonCancelPrefs)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonApplyPrefs)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonCancelPrefs)
                    .addComponent(jButtonApplyPrefs))
                .addGap(0, 14, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelPrefsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelPrefsActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonCancelPrefsActionPerformed

    private void btnAddPrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPrioActionPerformed
    	
    	selectedPrio = null;
    	jTextFieldPrioName.setText("New Priority");
    	jTextFieldPrioMin.setText("5");	
    	jListPrio.clearSelection();
    	
    }//GEN-LAST:event_btnAddPrioActionPerformed

    private void btnAddThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddThemeActionPerformed
    	selectedTheme = null;
    	jTextFieldThemeName.setText("New Theme");
    	jTextFieldThemeBgImg.setText("-");
    	jListTheme.clearSelection();
    }//GEN-LAST:event_btnAddThemeActionPerformed

    private void jTextFieldThemeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldThemeNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldThemeNameActionPerformed

    private void jTextFieldThemeBgImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldThemeBgImgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldThemeBgImgActionPerformed

    private void jButtonPrioSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrioSaveActionPerformed
        
    	Boolean alreadyExists = false;
    	
    	if(selectedPrio == null) { //Creation of new Priority
    		if(!jTextFieldPrioName.getText().isEmpty() && !jTextFieldPrioMin.getText().isEmpty()) {
        		
        		for(int i = 0; i < preferenceshandler.getListOfPriorities().size(); i++) {
        			
        			if(preferenceshandler.getListOfPriorities().get(i).getName().equals(jTextFieldPrioName.getText())) {
        				System.out.println("if reached");
        				alreadyExists = true;
        			}

        		}
        		
        		if(!alreadyExists) {
        			Priority newPriority = new Priority(jTextFieldPrioName.getText(), Integer.parseInt(jTextFieldPrioMin.getText()));
                	preferenceshandler.addPriority(newPriority);
                	jListPrio.setSelectedIndex(preferenceshandler.getListOfPriorities().size() - 1);
        		} else {
        			// TODO Error Dialog
        		}
        		
        	}
    	} else { //Editing the selected Priority
    		selectedPrio.setName(jTextFieldPrioName.getText());
    		selectedPrio.setMinutesToShow(Integer.parseInt(jTextFieldPrioMin.getText()));
    		preferenceshandler.refreshPrioListModel();
    	}
    	
    	managerFrame.refreshComboBoxCellEditor();
    	
    }//GEN-LAST:event_jButtonPrioSaveActionPerformed

    private void btnRemovePrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemovePrioActionPerformed
        int[] selectedIndices = jListPrio.getSelectedIndices();
    	preferenceshandler.removePriorities(Misc.indexListToPriorities(selectedIndices));
    	
    }//GEN-LAST:event_btnRemovePrioActionPerformed

    private void jButtonApplyPrefsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApplyPrefsActionPerformed
        
    	//TODO die unter main gesetzten booleans speichern
    	dispose();
    }//GEN-LAST:event_jButtonApplyPrefsActionPerformed

    private void jButtonChooseBgImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChooseBgImageActionPerformed
        jFileChooserBgImageTheme.showDialog(this, null);
        jFileChooserBgImageTheme.setVisible(true);
    }//GEN-LAST:event_jButtonChooseBgImageActionPerformed

    private void btnSaveThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveThemeActionPerformed
    	    	
    	Boolean alreadyExists = false;
    	selectedImage = new File(jTextFieldThemeBgImg.getText());
    	
    	
    	if(selectedTheme == null) { //Creation of new Priority
    		if(!jTextFieldThemeName.getText().isEmpty() && selectedImage.exists()) {
        		
    			
	        	for(int i = 0; i < preferenceshandler.getListOfThemes().size(); i++) {
	        		
	        		if(preferenceshandler.getListOfThemes().get(i).getName().equals(jTextFieldThemeName.getText())) {
	        			System.out.println("if reached");
	        			alreadyExists = true;
	        		}
	
    			}
        		
        		if(!alreadyExists) {
        			Theme newTheme = new Theme(jTextFieldThemeName.getText(), selectedImage);
                	preferenceshandler.addTheme(newTheme);
                	jListTheme.setSelectedIndex(preferenceshandler.getListOfThemes().size() - 1);
        		} else {
        			// TODO Error Dialog
        		}
        		
        	}
    	} else { //Editing the selected Theme
    		selectedTheme.setName(jTextFieldThemeName.getText());
    		if(selectedImage.exists()) {
    			selectedTheme.setBackgroundImage(selectedImage);	
    		} else {
    			//TODO Error Dialog
    		}
    		preferenceshandler.refreshThemeListModel();
    	}
    	
    }//GEN-LAST:event_btnSaveThemeActionPerformed

    private void btnRemoveThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveThemeActionPerformed
    	
    	int[] selectedIndices = jListTheme.getSelectedIndices(); 	
     	preferenceshandler.removeThemes(Misc.indexListToThemes(selectedIndices));
     	
    }//GEN-LAST:event_btnRemoveThemeActionPerformed

    private void jFileChooserBgImageThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooserBgImageThemeActionPerformed
        selectedImage = jFileChooserBgImageTheme.getSelectedFile();
    	jTextFieldThemeBgImg.setText(selectedImage.getAbsolutePath());
    	
    }//GEN-LAST:event_jFileChooserBgImageThemeActionPerformed

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
            java.util.logging.Logger.getLogger(PreferencesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PreferencesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PreferencesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PreferencesFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new PreferencesFrame(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddPrio;
    private javax.swing.JButton btnAddTheme;
    private javax.swing.JButton btnRemovePrio;
    private javax.swing.JButton btnRemoveTheme;
    private javax.swing.JButton btnSaveTheme;
    private javax.swing.JButton jButtonApplyPrefs;
    private javax.swing.JButton jButtonCancelPrefs;
    private javax.swing.JButton jButtonChooseBgImage;
    private javax.swing.JButton jButtonPrioSave;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JFileChooser jFileChooserBgImageTheme;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelPrioName;
    private javax.swing.JLabel jLabelThemeBg;
    private javax.swing.JLabel jLabelThemeName;
    private javax.swing.JLabel jLabelTickerSpeed;
    private javax.swing.JList jListPrio;
    private javax.swing.JList jListTheme;
    private javax.swing.JPanel jPanelTabMain;
    private javax.swing.JPanel jPanelTabPrio;
    private javax.swing.JPanel jPanelTabTheme;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextFieldPrioMin;
    private javax.swing.JTextField jTextFieldPrioName;
    private javax.swing.JTextField jTextFieldThemeBgImg;
    private javax.swing.JTextField jTextFieldThemeName;
    // End of variables declaration//GEN-END:variables

	public javax.swing.JList getjListPrio() {
		return jListPrio;
	}

	public void setjListPrio(javax.swing.JList jListPrio) {
		this.jListPrio = jListPrio;
	}

	public ManagerFrame getManagerFrame() {
		return managerFrame;
	}

	public void setManagerFrame(ManagerFrame managerFrame) {
		this.managerFrame = managerFrame;
	}
}
