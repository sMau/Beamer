/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.netprojectev.GUI;

import de.netprojectev.LiveTicker.LiveTicker;
import de.netprojectev.MediaHandler.MediaHandler;
import de.netprojectev.Misc.Misc;

/**
 *
 * 
 */
public class ManagerFrame extends javax.swing.JFrame {

    
    /**
	 * 
	 */
	private static final long serialVersionUID = 2853526692923366703L;
	private MediaHandler mediaHandler;
	private LiveTicker liveTicker;
    
    /**
     * Creates new form ManagerFrame
     */
    public ManagerFrame() {
        mediaHandler = MediaHandler.getInstance();
        mediaHandler.setManagerFrame(this);
        initComponents();
        
    }
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane = new javax.swing.JTabbedPane();
        filemangerPanel = new javax.swing.JPanel();
        btnPanelFile = new javax.swing.JPanel();
        btnAddFile = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        btnDown = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnUp = new javax.swing.JButton();
        btnSet = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        toggleBtnShuffle = new javax.swing.JToggleButton();
        toogleBtnAuto = new javax.swing.JToggleButton();
        lblTimeleft = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableFileManager = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        livetickerPanel = new javax.swing.JPanel();
        btnPanelLiveticker = new javax.swing.JPanel();
        btnAddFile2 = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        btnEdit2 = new javax.swing.JButton();
        btnRemove2 = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableLiveticker = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemAddFile = new javax.swing.JMenuItem();
        jMenuItemAddThemeslide = new javax.swing.JMenuItem();
        jMenuItemAddTickerElt = new javax.swing.JMenuItem();
        jMenuItemAddCntDwn = new javax.swing.JMenuItem();
        jMenuItemQuit = new javax.swing.JMenuItem();
        jMenuEdit = new javax.swing.JMenu();
        jMenuItemEdit = new javax.swing.JMenuItem();
        jMenuItemRemove = new javax.swing.JMenuItem();
        jMenuItemUp = new javax.swing.JMenuItem();
        jMenuItemDown = new javax.swing.JMenuItem();
        jMenuPrefs = new javax.swing.JMenu();
        jRadioButtonMenuItemAuto = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItemShuffle = new javax.swing.JRadioButtonMenuItem();
        jMenuItemPrefs = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Beamermanager");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("managerframe");

        filemangerPanel.setName("filemanagerTab");
        filemangerPanel.setNextFocusableComponent(livetickerPanel);

        btnAddFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/plus_2.png"))); // NOI18N
        btnAddFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFileActionPerformed(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/arrow_down.png"))); // NOI18N
        btnDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownActionPerformed(evt);
            }
        });

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/pencil_edit.png"))); // NOI18N
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnRemove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/delete_2.png"))); // NOI18N
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/arrow_up.png"))); // NOI18N
        btnUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpActionPerformed(evt);
            }
        });

        btnSet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/play.png"))); // NOI18N
        btnSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetActionPerformed(evt);
            }
        });

        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/previous.png"))); // NOI18N
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/next.png"))); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        toggleBtnShuffle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/shuffle-icon.png"))); // NOI18N
        toggleBtnShuffle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleBtnShuffleActionPerformed(evt);
            }
        });

        toogleBtnAuto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/Clockwise-arrow128.png"))); // NOI18N
        toogleBtnAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toogleBtnAutoActionPerformed(evt);
            }
        });

        lblTimeleft.setText("Timeleft: 00:40");

        javax.swing.GroupLayout btnPanelFileLayout = new javax.swing.GroupLayout(btnPanelFile);
        btnPanelFile.setLayout(btnPanelFileLayout);
        btnPanelFileLayout.setHorizontalGroup(
            btnPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelFileLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddFile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemove)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDown)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSet)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrev)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 287, Short.MAX_VALUE)
                .addComponent(lblTimeleft)
                .addGap(18, 18, 18)
                .addComponent(toogleBtnAuto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(toggleBtnShuffle))
        );
        btnPanelFileLayout.setVerticalGroup(
            btnPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelFileLayout.createSequentialGroup()
                .addGroup(btnPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddFile)
                    .addGroup(btnPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(toggleBtnShuffle)
                        .addComponent(toogleBtnAuto)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(btnPanelFileLayout.createSequentialGroup()
                .addGroup(btnPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(btnPanelFileLayout.createSequentialGroup()
                        .addGroup(btnPanelFileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSet)
                            .addComponent(btnRemove)
                            .addComponent(btnEdit)
                            .addComponent(btnDown)
                            .addComponent(btnUp)
                            .addComponent(btnPrev)
                            .addComponent(btnNext)
                            .addGroup(btnPanelFileLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblTimeleft)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTableFileManager.setModel(new FileManagerTableModel());
        jTableFileManager.setShowHorizontalLines(false);
        jTableFileManager.setShowVerticalLines(false);
        jTableFileManager.getColumnModel().getColumn(0).setMaxWidth(12);
        jTableFileManager.getColumnModel().getColumn(1).setMaxWidth(12);
        jTableFileManager.getColumnModel().getColumn(0).setMinWidth(12);
        jTableFileManager.getColumnModel().getColumn(1).setMinWidth(12);
        jTableFileManager.getColumnModel().getColumn(0).setResizable(false);
        jTableFileManager.getColumnModel().getColumn(1).setResizable(false);
        jScrollPane1.setViewportView(jTableFileManager);

        jLabel1.setText("no File selected");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Preview"));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTextArea1.setBackground(new java.awt.Color(240, 240, 240));
        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setRows(5);
        jTextArea1.setText("no File selected");
        jTextArea1.setToolTipText("");
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder("Info"));
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout filemangerPanelLayout = new javax.swing.GroupLayout(filemangerPanel);
        filemangerPanel.setLayout(filemangerPanelLayout);
        filemangerPanelLayout.setHorizontalGroup(
            filemangerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnPanelFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator3)
            .addComponent(jScrollPane1)
            .addGroup(filemangerPanelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        filemangerPanelLayout.setVerticalGroup(
            filemangerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filemangerPanelLayout.createSequentialGroup()
                .addComponent(btnPanelFile, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(filemangerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane.addTab("Filemanager", filemangerPanel);
        filemangerPanel.getAccessibleContext().setAccessibleName("filemanagerTab");
        filemangerPanel.getAccessibleContext().setAccessibleParent(jTabbedPane);

        livetickerPanel.setName("livetickerTab");
        livetickerPanel.setNextFocusableComponent(filemangerPanel);

        btnAddFile2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/plus_2.png"))); // NOI18N

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        btnEdit2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/pencil_edit.png"))); // NOI18N

        btnRemove2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/netprojectev/GFX/delete_2.png"))); // NOI18N

        javax.swing.GroupLayout btnPanelLivetickerLayout = new javax.swing.GroupLayout(btnPanelLiveticker);
        btnPanelLiveticker.setLayout(btnPanelLivetickerLayout);
        btnPanelLivetickerLayout.setHorizontalGroup(
            btnPanelLivetickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelLivetickerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAddFile2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEdit2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRemove2)
                .addContainerGap())
            .addComponent(jSeparator7, javax.swing.GroupLayout.DEFAULT_SIZE, 851, Short.MAX_VALUE)
        );
        btnPanelLivetickerLayout.setVerticalGroup(
            btnPanelLivetickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPanelLivetickerLayout.createSequentialGroup()
                .addGroup(btnPanelLivetickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddFile2)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemove2)
                    .addComponent(btnEdit2))
                .addGap(2, 2, 2)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTableLiveticker.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTableLiveticker);

        javax.swing.GroupLayout livetickerPanelLayout = new javax.swing.GroupLayout(livetickerPanel);
        livetickerPanel.setLayout(livetickerPanelLayout);
        livetickerPanelLayout.setHorizontalGroup(
            livetickerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnPanelLiveticker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator8)
            .addComponent(jScrollPane3)
        );
        livetickerPanelLayout.setVerticalGroup(
            livetickerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(livetickerPanelLayout.createSequentialGroup()
                .addComponent(btnPanelLiveticker, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("Liveticker", livetickerPanel);
        livetickerPanel.getAccessibleContext().setAccessibleName("livetickerTab");

        jMenuFile.setText("File");

        jMenuItemAddFile.setText("Add File");
        jMenuItemAddFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddFileActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemAddFile);

        jMenuItemAddThemeslide.setText("Add Themeslide");
        jMenuItemAddThemeslide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAddThemeslideActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemAddThemeslide);

        jMenuItemAddTickerElt.setText("Add Ticker Element");
        jMenuFile.add(jMenuItemAddTickerElt);

        jMenuItemAddCntDwn.setText("Add Countdown");
        jMenuFile.add(jMenuItemAddCntDwn);

        jMenuItemQuit.setText("Quit");
        jMenuItemQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemQuitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemQuit);

        jMenuBar1.add(jMenuFile);

        jMenuEdit.setText("Edit");

        jMenuItemEdit.setText("Edit");
        jMenuItemEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItemEdit);

        jMenuItemRemove.setText("Remove");
        jMenuItemRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRemoveActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItemRemove);

        jMenuItemUp.setText("Up");
        jMenuItemUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemUpActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItemUp);

        jMenuItemDown.setText("Down");
        jMenuItemDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDownActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItemDown);

        jMenuBar1.add(jMenuEdit);

        jMenuPrefs.setText("Preferences");

        jRadioButtonMenuItemAuto.setSelected(true);
        jRadioButtonMenuItemAuto.setText("Automode");
        jRadioButtonMenuItemAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItemAutoActionPerformed(evt);
            }
        });
        jMenuPrefs.add(jRadioButtonMenuItemAuto);

        jRadioButtonMenuItemShuffle.setSelected(true);
        jRadioButtonMenuItemShuffle.setText("Shuffle");
        jRadioButtonMenuItemShuffle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItemShuffleActionPerformed(evt);
            }
        });
        jMenuPrefs.add(jRadioButtonMenuItemShuffle);

        jMenuItemPrefs.setText("Preferences");
        jMenuItemPrefs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPrefsActionPerformed(evt);
            }
        });
        jMenuPrefs.add(jMenuItemPrefs);

        jMenuBar1.add(jMenuPrefs);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFileActionPerformed
        new FileThemeDialog(this, true).setVisible(true);
    }//GEN-LAST:event_btnAddFileActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
    	//TODO For all move and remove actions: implement a proper auto reselection
        int[] selectedRows = jTableFileManager.getSelectedRows();
        if(selectedRows.length > 0) {
        	mediaHandler.remove(Misc.indexListToMediaFiles(selectedRows));
        }
        
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpActionPerformed
    	int[] selectedRows = jTableFileManager.getSelectedRows();
    	if(selectedRows.length > 0) {
    		mediaHandler.up(Misc.indexListToMediaFiles(selectedRows));
    	}
    }//GEN-LAST:event_btnUpActionPerformed

    private void btnDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownActionPerformed
    	int[] selectedRows = jTableFileManager.getSelectedRows();
    	if(selectedRows.length > 0) {
    		mediaHandler.down(Misc.indexListToMediaFiles(selectedRows));
    	}
    }//GEN-LAST:event_btnDownActionPerformed

    private void btnSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetActionPerformed
    	int[] selectedRows = jTableFileManager.getSelectedRows();
    	if(selectedRows.length > 0) {
    		mediaHandler.getDisplayHandler().show(Misc.indexListToMediaFiles(selectedRows)[0]);
    	}
    }//GEN-LAST:event_btnSetActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
    	mediaHandler.getDisplayHandler().showPrevious();
    }//GEN-LAST:event_btnPrevActionPerformed
    
    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
    	mediaHandler.getDisplayHandler().showNext();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
    	int[] selectedRows = jTableFileManager.getSelectedRows();
    	if(selectedRows.length > 0) {
    		new EditMediaFileFrame(Misc.indexListToMediaFiles(selectedRows)[0]).setVisible(true);
    	}
    }//GEN-LAST:event_btnEditActionPerformed

    private void jMenuItemAddFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAddFileActionPerformed
    	new FileThemeDialog(this, true).getFileAddbtn().doClick();
    }//GEN-LAST:event_jMenuItemAddFileActionPerformed

    private void jMenuItemAddThemeslideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAddThemeslideActionPerformed
    	new FileThemeDialog(this, true).getThemeSlideAddBtn().doClick();
    }//GEN-LAST:event_jMenuItemAddThemeslideActionPerformed

    private void jMenuItemQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemQuitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItemQuitActionPerformed

    private void jMenuItemEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEditActionPerformed
    	int[] selectedRows = jTableFileManager.getSelectedRows();
    	if(selectedRows.length > 0) {
    		new EditMediaFileFrame(Misc.indexListToMediaFiles(selectedRows)[0]).setVisible(true);
    	}
    }//GEN-LAST:event_jMenuItemEditActionPerformed

    private void jMenuItemRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRemoveActionPerformed
    	int[] selectedRows = jTableFileManager.getSelectedRows();
        if(selectedRows.length > 0) {
        	mediaHandler.remove(Misc.indexListToMediaFiles(selectedRows));
        }
    }//GEN-LAST:event_jMenuItemRemoveActionPerformed

    private void jMenuItemUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemUpActionPerformed
    	int[] selectedRows = jTableFileManager.getSelectedRows();
    	if(selectedRows.length > 0) {
    		mediaHandler.up(Misc.indexListToMediaFiles(selectedRows));
    	}
    }//GEN-LAST:event_jMenuItemUpActionPerformed

    private void jMenuItemDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDownActionPerformed
    	int[] selectedRows = jTableFileManager.getSelectedRows();
    	if(selectedRows.length > 0) {
    		mediaHandler.down(Misc.indexListToMediaFiles(selectedRows));
    	}
    }//GEN-LAST:event_jMenuItemDownActionPerformed

    private void jMenuItemPrefsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPrefsActionPerformed
        new PreferencesFrame().setVisible(true);
    }//GEN-LAST:event_jMenuItemPrefsActionPerformed

    private void jRadioButtonMenuItemShuffleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItemShuffleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonMenuItemShuffleActionPerformed

    private void jRadioButtonMenuItemAutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItemAutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonMenuItemAutoActionPerformed

    private void toogleBtnAutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toogleBtnAutoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_toogleBtnAutoActionPerformed

    private void toggleBtnShuffleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleBtnShuffleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_toggleBtnShuffleActionPerformed

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
            java.util.logging.Logger.getLogger(ManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManagerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ManagerFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddFile;
    private javax.swing.JButton btnAddFile2;
    private javax.swing.JButton btnDown;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnEdit2;
    private javax.swing.JButton btnNext;
    private javax.swing.JPanel btnPanelFile;
    private javax.swing.JPanel btnPanelLiveticker;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnRemove2;
    private javax.swing.JButton btnSet;
    private javax.swing.JButton btnUp;
    private javax.swing.JPanel filemangerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemAddCntDwn;
    private javax.swing.JMenuItem jMenuItemAddFile;
    private javax.swing.JMenuItem jMenuItemAddThemeslide;
    private javax.swing.JMenuItem jMenuItemAddTickerElt;
    private javax.swing.JMenuItem jMenuItemDown;
    private javax.swing.JMenuItem jMenuItemEdit;
    private javax.swing.JMenuItem jMenuItemPrefs;
    private javax.swing.JMenuItem jMenuItemQuit;
    private javax.swing.JMenuItem jMenuItemRemove;
    private javax.swing.JMenuItem jMenuItemUp;
    private javax.swing.JMenu jMenuPrefs;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemAuto;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItemShuffle;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTableFileManager;
    private javax.swing.JTable jTableLiveticker;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblTimeleft;
    private javax.swing.JPanel livetickerPanel;
    private javax.swing.JToggleButton toggleBtnShuffle;
    private javax.swing.JToggleButton toogleBtnAuto;
    // End of variables declaration//GEN-END:variables

	public javax.swing.JTable getjTableFileManager() {
		return jTableFileManager;
        }

	public void setjTableFileManager(javax.swing.JTable jTableFileManager) {
		this.jTableFileManager = jTableFileManager;
	}
}
