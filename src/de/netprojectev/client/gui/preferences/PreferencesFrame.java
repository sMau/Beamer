/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.netprojectev.client.gui.preferences;

import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.netprojectev.client.model.PreferencesModelClient;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.datastructures.media.Theme;
import de.netprojectev.exceptions.PriorityDoesNotExistException;
import de.netprojectev.exceptions.ThemeDoesNotExistException;
import de.netprojectev.misc.Misc;

/**
 *
 * @author samu
 */
public class PreferencesFrame extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1287233997445904109L;
	
	private final ClientMessageProxy proxy;
	private final PreferencesModelClient prefs;
	
	/**
     * Creates new form PreferencesFrame
     */
    public PreferencesFrame(ClientMessageProxy proxy) {
        this.prefs = proxy.getPrefs();
        this.proxy = proxy;
    	initComponents();
    	
    	jliPriorities.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				updatePriorityPanel();
			}
		});
    	
    	jliThemes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				updateThemePanel();
			}

			
		});
        
    }


	/**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtpPreferences = new javax.swing.JTabbedPane();
        jpPriorities = new javax.swing.JPanel();
        jspPrioList = new javax.swing.JScrollPane();
        jliPriorities = new javax.swing.JList();
        jlPrioName = new javax.swing.JLabel();
        jlPrioNameVar = new javax.swing.JLabel();
        jlPrioTime = new javax.swing.JLabel();
        jlTimeToShowVar = new javax.swing.JLabel();
        jbRemovePrio = new javax.swing.JButton();
        jbAddPrio = new javax.swing.JButton();
        jpThemes = new javax.swing.JPanel();
        jspThemeList = new javax.swing.JScrollPane();
        jliThemes = new javax.swing.JList();
        jlThemeName = new javax.swing.JLabel();
        jlThemeNameVar = new javax.swing.JLabel();
        jlThemeBackgroundPreview = new javax.swing.JLabel();
        jbRemoveTheme = new javax.swing.JButton();
        jbAddNewTheme = new javax.swing.JButton();
        jpTicker = new javax.swing.JPanel();
        jpThemeslideCreator = new javax.swing.JPanel();
        jpCountdown = new javax.swing.JPanel();
        jbClosePrefs = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Preferences");

        jliPriorities.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jliPriorities.setModel(new PriorityListModel(prefs));
        jspPrioList.setViewportView(jliPriorities);

        jlPrioName.setText("Name");

        jlPrioNameVar.setText("unknown");

        jlPrioTime.setText("Time to show");

        jlTimeToShowVar.setText("unknown");

        jbRemovePrio.setText("Remove");
        jbRemovePrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemovePrioActionPerformed(evt);
            }
        });

        jbAddPrio.setText("Add New");
        jbAddPrio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddPrioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpPrioritiesLayout = new javax.swing.GroupLayout(jpPriorities);
        jpPriorities.setLayout(jpPrioritiesLayout);
        jpPrioritiesLayout.setHorizontalGroup(
            jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPrioritiesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspPrioList, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addGroup(jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPrioritiesLayout.createSequentialGroup()
                        .addGroup(jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlPrioTime)
                            .addComponent(jlPrioName))
                        .addGap(30, 30, 30)
                        .addGroup(jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlPrioNameVar)
                            .addComponent(jlTimeToShowVar)))
                    .addGroup(jpPrioritiesLayout.createSequentialGroup()
                        .addComponent(jbRemovePrio)
                        .addGap(18, 18, 18)
                        .addComponent(jbAddPrio)))
                .addContainerGap(142, Short.MAX_VALUE))
        );
        jpPrioritiesLayout.setVerticalGroup(
            jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpPrioritiesLayout.createSequentialGroup()
                .addGroup(jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpPrioritiesLayout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlPrioName)
                            .addComponent(jlPrioNameVar))
                        .addGap(18, 18, 18)
                        .addGroup(jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlPrioTime)
                            .addComponent(jlTimeToShowVar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jpPrioritiesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbRemovePrio)
                            .addComponent(jbAddPrio)))
                    .addGroup(jpPrioritiesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jspPrioList, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jtpPreferences.addTab("Priorities", jpPriorities);

        jliThemes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jliThemes.setModel(new ThemeListModel(prefs));
        jspThemeList.setViewportView(jliThemes);

        jlThemeName.setText("Name");

        jlThemeNameVar.setText("unknown");

        jlThemeBackgroundPreview.setText("Preview");

        jbRemoveTheme.setText("Remove");
        jbRemoveTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoveThemeActionPerformed(evt);
            }
        });

        jbAddNewTheme.setText("Add New");
        jbAddNewTheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddNewThemeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpThemesLayout = new javax.swing.GroupLayout(jpThemes);
        jpThemes.setLayout(jpThemesLayout);
        jpThemesLayout.setHorizontalGroup(
            jpThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpThemesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jspThemeList, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(80, 80, 80)
                .addGroup(jpThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpThemesLayout.createSequentialGroup()
                        .addGroup(jpThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpThemesLayout.createSequentialGroup()
                                .addComponent(jlThemeName)
                                .addGap(77, 77, 77)
                                .addComponent(jlThemeNameVar))
                            .addGroup(jpThemesLayout.createSequentialGroup()
                                .addComponent(jbRemoveTheme)
                                .addGap(18, 18, 18)
                                .addComponent(jbAddNewTheme)))
                        .addGap(0, 136, Short.MAX_VALUE))
                    .addComponent(jlThemeBackgroundPreview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpThemesLayout.setVerticalGroup(
            jpThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpThemesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jspThemeList, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpThemesLayout.createSequentialGroup()
                        .addGroup(jpThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jlThemeName)
                            .addComponent(jlThemeNameVar))
                        .addGap(18, 18, 18)
                        .addComponent(jlThemeBackgroundPreview, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jpThemesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbRemoveTheme)
                            .addComponent(jbAddNewTheme))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtpPreferences.addTab("Themes", jpThemes);

        javax.swing.GroupLayout jpTickerLayout = new javax.swing.GroupLayout(jpTicker);
        jpTicker.setLayout(jpTickerLayout);
        jpTickerLayout.setHorizontalGroup(
            jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 615, Short.MAX_VALUE)
        );
        jpTickerLayout.setVerticalGroup(
            jpTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 319, Short.MAX_VALUE)
        );

        jtpPreferences.addTab("Ticker", jpTicker);

        javax.swing.GroupLayout jpThemeslideCreatorLayout = new javax.swing.GroupLayout(jpThemeslideCreator);
        jpThemeslideCreator.setLayout(jpThemeslideCreatorLayout);
        jpThemeslideCreatorLayout.setHorizontalGroup(
            jpThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 615, Short.MAX_VALUE)
        );
        jpThemeslideCreatorLayout.setVerticalGroup(
            jpThemeslideCreatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 319, Short.MAX_VALUE)
        );

        jtpPreferences.addTab("Themeslide Creator", jpThemeslideCreator);

        javax.swing.GroupLayout jpCountdownLayout = new javax.swing.GroupLayout(jpCountdown);
        jpCountdown.setLayout(jpCountdownLayout);
        jpCountdownLayout.setHorizontalGroup(
            jpCountdownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 615, Short.MAX_VALUE)
        );
        jpCountdownLayout.setVerticalGroup(
            jpCountdownLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 319, Short.MAX_VALUE)
        );

        jtpPreferences.addTab("Countdown", jpCountdown);

        jbClosePrefs.setText("Close");
        jbClosePrefs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbClosePrefsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jtpPreferences)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jbClosePrefs)
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jtpPreferences, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jbClosePrefs)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbClosePrefsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbClosePrefsActionPerformed
        dispose();
    }//GEN-LAST:event_jbClosePrefsActionPerformed

    private void jbAddPrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddPrioActionPerformed
    	new AddPriorityDialog(this, true, proxy).setVisible(true);
    	updatePriorityPanel();
    }//GEN-LAST:event_jbAddPrioActionPerformed

    private void jbRemovePrioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemovePrioActionPerformed
        int selected = jliPriorities.getSelectedIndex();
        if(selected >= 0 && prefs.priorityCount() > 0 && selected < prefs.priorityCount()) {
        	try {
				proxy.sendRemovePriority(prefs.getPriorityAt(selected).getId());
			} catch (PriorityDoesNotExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	updatePriorityPanel();
        }
        
    }//GEN-LAST:event_jbRemovePrioActionPerformed

    private void jbRemoveThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRemoveThemeActionPerformed
        int selected = jliThemes.getSelectedIndex();
        if(selected >= 0 && prefs.themeCount() > 0 && selected < prefs.themeCount()) {
        	try {
				proxy.sendRemoveTheme(prefs.getThemeAt(selected).getId());
			} catch (ThemeDoesNotExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	updateThemePanel();
        }
        
    }//GEN-LAST:event_jbRemoveThemeActionPerformed

    private void jbAddNewThemeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAddNewThemeActionPerformed
        new AddThemeDialog(this, true, proxy).setVisible(true);
        updateThemePanel();
    }//GEN-LAST:event_jbAddNewThemeActionPerformed

    @Override
	protected void processWindowEvent(WindowEvent e) {

		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			dispose();
		} else {
			super.processWindowEvent(e);
		}
	}
    
    private void updateThemePanel() {
    	int row = jliThemes.getSelectedIndex();
        if(row >= 0) {
        	Theme selected;
			try {
				selected = prefs.getThemeAt(row);
				jlThemeNameVar.setText(selected.getName());
				jlThemeBackgroundPreview.setText("");
				
				ImageIcon scaledPreview = Misc.getScaledImageIcon(selected.getBackgroundImage(), jlThemeBackgroundPreview.getWidth());
				
				jlThemeBackgroundPreview.setIcon(scaledPreview);
			} catch (ThemeDoesNotExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
    private void updatePriorityPanel() {
    	int row = jliPriorities.getSelectedIndex();
        if(row >= 0) {
        	Priority selected;
			try {
				selected = prefs.getPriorityAt(row);
				jlPrioNameVar.setText(selected.getName());
	        	jlTimeToShowVar.setText(Integer.toString(selected.getMinutesToShow()));
			} catch (PriorityDoesNotExistException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
	}
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PreferencesFrame(null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbAddNewTheme;
    private javax.swing.JButton jbAddPrio;
    private javax.swing.JButton jbClosePrefs;
    private javax.swing.JButton jbRemovePrio;
    private javax.swing.JButton jbRemoveTheme;
    private javax.swing.JLabel jlPrioName;
    private javax.swing.JLabel jlPrioNameVar;
    private javax.swing.JLabel jlPrioTime;
    private javax.swing.JLabel jlThemeBackgroundPreview;
    private javax.swing.JLabel jlThemeName;
    private javax.swing.JLabel jlThemeNameVar;
    private javax.swing.JLabel jlTimeToShowVar;
    private javax.swing.JList jliPriorities;
    private javax.swing.JList jliThemes;
    private javax.swing.JPanel jpCountdown;
    private javax.swing.JPanel jpPriorities;
    private javax.swing.JPanel jpThemes;
    private javax.swing.JPanel jpThemeslideCreator;
    private javax.swing.JPanel jpTicker;
    private javax.swing.JScrollPane jspPrioList;
    private javax.swing.JScrollPane jspThemeList;
    private javax.swing.JTabbedPane jtpPreferences;
    // End of variables declaration//GEN-END:variables
}
