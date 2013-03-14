/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.netprojectev.client.gui.dialogs;

import javax.swing.SwingUtilities;

import de.netprojectev.client.gui.manager.FileManagerTableModel;
import de.netprojectev.client.gui.manager.ManagerFrame;
import de.netprojectev.misc.Misc;
import de.netprojectev.old.PreferencesModelOld;
import de.netprojectev.old.ServerMediaFile;


/**
 * GUI class dialog to edit name and priority of a @see MediaFile
 * @author samu
 */
public class EditMediaFileFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 1446531265560668639L;
	private ServerMediaFile selectedMediaFile;
	private PreferencesModelOld preferencesHandler;
	private ManagerFrame managerFrame;
	
    public EditMediaFileFrame(ServerMediaFile selectedMediaFile, ManagerFrame managerFrame) {
    	
    	if(selectedMediaFile != null) {
    		initComponents();
    		setLocation(Misc.currentMousePosition());
    		preferencesHandler = PreferencesModelOld.getInstance();
    		this.managerFrame = managerFrame;
    		this.selectedMediaFile = selectedMediaFile;
            jTextFieldEditFileName.setText(selectedMediaFile.getName()); 
            
            for(int i = 0; i < preferencesHandler.getListOfPriorities().size(); i++) {
            	jComboBoxPriority.addItem(preferencesHandler.getListOfPriorities().get(i).getName());
            }
            jComboBoxPriority.setSelectedItem(selectedMediaFile.getPriority().getName());
            jLabelMinutes.setText(Integer.toString(selectedMediaFile.getPriority().getMinutesToShow()));
    	}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldEditFileName = new javax.swing.JTextField();
        jLabelEnterNameHint = new javax.swing.JLabel();
        jButtonApply = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jLabelEnterNameHint1 = new javax.swing.JLabel();
        jComboBoxPriority = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabelMinutes = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit File");

        jTextFieldEditFileName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
            }
        });

        jLabelEnterNameHint.setText("File name");

        jButtonApply.setText("Apply");
        jButtonApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApplyActionPerformed(evt);
            }
        });

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jLabelEnterNameHint1.setText("Priority");

        jComboBoxPriority.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxPriorityActionPerformed(evt);
            }
        });

        jLabel1.setText("Minutes:");

        jLabelMinutes.setText("-");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 199, Short.MAX_VALUE)
                        .addComponent(jButtonCancel)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonApply))
                    .addComponent(jTextFieldEditFileName)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelEnterNameHint)
                            .addComponent(jLabelEnterNameHint1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBoxPriority, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelMinutes)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabelEnterNameHint)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldEditFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelEnterNameHint1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxPriority, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelMinutes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonApply)
                    .addComponent(jButtonCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    /**
     * Editing the selected media file. Changing the name and the priority.
     * In the end a GUI update is called.
     */
    private void jButtonApplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApplyActionPerformed    	
    	final int selectedRow = managerFrame.getjTableFileManager().getSelectedRow();    	
    	selectedMediaFile.setName(jTextFieldEditFileName.getText());
    	selectedMediaFile.setPriority(preferencesHandler.getListOfPriorities().get(jComboBoxPriority.getSelectedIndex()));   	
    	
			((FileManagerTableModel) managerFrame.getjTableFileManager().getModel()).updateModel();
			SwingUtilities.invokeLater(new Runnable() {

	            public void run() {
	            	managerFrame.getjTableFileManager().setRowSelectionInterval(selectedRow, selectedRow);
	            }
	        });
		
    	dispose();
    }//GEN-LAST:event_jButtonApplyActionPerformed

    /**
	 * Update on selection change of the combo box, the text label showing the minutes of current priority.
	 */
    private void jComboBoxPriorityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxPriorityActionPerformed
		jLabelMinutes.setText(Integer.toString(preferencesHandler.getListOfPriorities().get(jComboBoxPriority.getSelectedIndex()).getMinutesToShow()));
    }//GEN-LAST:event_jComboBoxPriorityActionPerformed

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
            java.util.logging.Logger.getLogger(EditMediaFileFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditMediaFileFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditMediaFileFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditMediaFileFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new EditMediaFileFrame(null, null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonApply;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JComboBox jComboBoxPriority;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelEnterNameHint;
    private javax.swing.JLabel jLabelEnterNameHint1;
    private javax.swing.JLabel jLabelMinutes;
    private javax.swing.JTextField jTextFieldEditFileName;
    // End of variables declaration//GEN-END:variables
}
