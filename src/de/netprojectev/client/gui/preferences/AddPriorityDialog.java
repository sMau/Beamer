/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.netprojectev.client.gui.preferences;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.datastructures.media.Priority;
import de.netprojectev.misc.Misc;
import de.netprojectev.networking.Message;
import de.netprojectev.networking.OpCode;

/**
 *
 * @author samu
 */
public class AddPriorityDialog extends javax.swing.JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5684538579454947789L;
	/**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;

    private final ClientMessageProxy proxy;
    
    /**
     * Creates new form AddPriorityDialog
     */
    public AddPriorityDialog(java.awt.Frame parent, boolean modal, ClientMessageProxy proxy) {
        super(parent, modal);
        this.proxy = proxy;
        initComponents();

        setLocation(Misc.currentMousePosition());
        
        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
    }

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jlNewPrioName = new javax.swing.JLabel();
        jtfNewPrioName = new javax.swing.JTextField();
        jlNewPrioMinutes = new javax.swing.JLabel();
        jtfNewPrioMinutes = new javax.swing.JTextField();

        setTitle("Add Priority");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okButton.setText("Add");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jlNewPrioName.setText("Enter the name");

        jlNewPrioMinutes.setText("Time to show in min");

        jtfNewPrioMinutes.setText("10");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 223, Short.MAX_VALUE)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jlNewPrioMinutes)
                            .addComponent(jlNewPrioName))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtfNewPrioName, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfNewPrioMinutes, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfNewPrioName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlNewPrioName))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlNewPrioMinutes)
                    .addComponent(jtfNewPrioMinutes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        getRootPane().setDefaultButton(okButton);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        doClose(RET_OK);
    }//GEN-LAST:event_okButtonActionPerformed
    
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog
    
    private void doClose(int retStatus) {
    	if(retStatus == RET_OK) {
    		String name = jtfNewPrioName.getText();
    		int minutes = 0;
    		try {
				minutes =  Integer.parseInt(jtfNewPrioMinutes.getText());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Please enter an numeric value for the time in minutes.");
				return;
			}
    		if(name == null || name.isEmpty()) {
    			JOptionPane.showMessageDialog(this, "The entered name is not valid.");
    			return;
    		}
    		if(minutes < 1) {
    			JOptionPane.showMessageDialog(this, "The entered minutes are not valid.");
    			return;
    		}
    		proxy.sendMessageToServer(new Message(OpCode.CTS_ADD_PRIORITY, new Priority(name, minutes)));
    	}
        returnStatus = retStatus;
        setVisible(false);
        dispose();
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
            java.util.logging.Logger.getLogger(AddPriorityDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddPriorityDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddPriorityDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddPriorityDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddPriorityDialog dialog = new AddPriorityDialog(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jlNewPrioMinutes;
    private javax.swing.JLabel jlNewPrioName;
    private javax.swing.JTextField jtfNewPrioMinutes;
    private javax.swing.JTextField jtfNewPrioName;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables
    private int returnStatus = RET_CANCEL;
}
