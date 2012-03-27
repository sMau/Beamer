/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.netprojectev.GUI;

import java.io.File;

import de.netprojectev.MediaHandler.MediaHandler;
import de.netprojectev.Misc.Misc;

/**
 *
 * 
 */
public class FileThemeDialog extends javax.swing.JDialog {

	
	//TODO Pimp the look of the Filechooser used of Ubuntu, maybe with this here:
	// see FileDialog
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 731673406169325261L;
	/**
     * Creates new form FileThemeDialog
     */
    public FileThemeDialog(java.awt.Frame parent, boolean modal) {
    	super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooserAddFile = new javax.swing.JFileChooser();
        fileAddbtn = new javax.swing.JButton();
        themeSlideAddBtn = new javax.swing.JButton();

        jFileChooserAddFile.setDialogTitle("Add File");
        jFileChooserAddFile.setMultiSelectionEnabled(true);
        jFileChooserAddFile.setName("Add file");
        jFileChooserAddFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooserAddFileActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add File");
        setBackground(new java.awt.Color(240, 240, 240));
        setModal(true);

        fileAddbtn.setText("File");
        fileAddbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileAddbtnActionPerformed(evt);
            }
        });

        themeSlideAddBtn.setText("Themeslide");
        themeSlideAddBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themeSlideAddBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fileAddbtn)
                .addGap(18, 18, 18)
                .addComponent(themeSlideAddBtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(themeSlideAddBtn)
                    .addComponent(fileAddbtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileAddbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileAddbtnActionPerformed
        jFileChooserAddFile.showDialog(this, null);
        jFileChooserAddFile.setVisible(true);
        dispose();
    }//GEN-LAST:event_fileAddbtnActionPerformed

    private void themeSlideAddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themeSlideAddBtnActionPerformed
        new ThemeslideCreatorFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_themeSlideAddBtnActionPerformed

    private void jFileChooserAddFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooserAddFileActionPerformed
        File[] files = jFileChooserAddFile.getSelectedFiles();
        MediaHandler.getInstance().add(Misc.createMediaFromFiles(files));
    }//GEN-LAST:event_jFileChooserAddFileActionPerformed

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
            java.util.logging.Logger.getLogger(FileThemeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FileThemeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FileThemeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FileThemeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                FileThemeDialog dialog = new FileThemeDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton fileAddbtn;
    private javax.swing.JFileChooser jFileChooserAddFile;
    private javax.swing.JButton themeSlideAddBtn;
    // End of variables declaration//GEN-END:variables
	public javax.swing.JButton getFileAddbtn() {
		return fileAddbtn;
	}

	public javax.swing.JButton getThemeSlideAddBtn() {
		return themeSlideAddBtn;
	}

}
