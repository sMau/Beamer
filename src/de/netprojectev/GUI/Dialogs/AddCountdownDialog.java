/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.netprojectev.GUI.Dialogs;

import java.util.Date;

import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import de.netprojectev.Media.Countdown;
import de.netprojectev.Media.MediaFile;
import de.netprojectev.MediaHandler.DisplayHandler;
import de.netprojectev.MediaHandler.MediaHandler;
import de.netprojectev.Misc.Misc;

/**
 * Dialog to add a {@link Countdown}
 * @author samu
 */
public class AddCountdownDialog extends javax.swing.JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7849002135529613765L;

	
	/**
     * Creates new form AddCountdownDialog
     */
    public AddCountdownDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setLocation(Misc.currentMousePosition());
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jSpinnerTime = new javax.swing.JSpinner();
        jRadioButtonTimeInMinutes = new javax.swing.JRadioButton();
        jRadioButtonFinishDate = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldRuntimeInMinutes = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jButtonAddAndStart = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButtonAdd = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add a Countdown");

        jSpinnerTime.setModel(new SpinnerDateModel());
        jSpinnerTime.setEnabled(false);
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(jSpinnerTime, "HH:mm dd/MM");
        jSpinnerTime.setEditor(timeEditor);
        jSpinnerTime.setValue(new Date());

        jRadioButtonTimeInMinutes.setSelected(true);
        jRadioButtonTimeInMinutes.setText("Runtime in minutes");
        jRadioButtonTimeInMinutes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonTimeInMinutesActionPerformed(evt);
            }
        });

        jRadioButtonFinishDate.setText("Time of day");
        jRadioButtonFinishDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonFinishDateActionPerformed(evt);
            }
        });

        jLabel1.setText("Minutes");

        jTextFieldRuntimeInMinutes.setText("60");

        jLabel2.setText("Time");

        jLabel3.setText("(The countdown will run x minutes)");

        jLabel4.setText("(The countdown will be finished at the set time of day)");

        jLabel6.setText("Name");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldRuntimeInMinutes, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jRadioButtonTimeInMinutes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jRadioButtonFinishDate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1)))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSpinnerTime, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jRadioButtonTimeInMinutes)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldRuntimeInMinutes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jRadioButtonFinishDate)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonAddAndStart.setText("Add and start");
        jButtonAddAndStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddAndStartActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonCancel)
                .addGap(18, 18, 18)
                .addComponent(jButtonAddAndStart)
                .addGap(18, 18, 18)
                .addComponent(jButtonAdd)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonAddAndStart)
                        .addComponent(jButtonCancel)
                        .addComponent(jButtonAdd))
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButtonTimeInMinutesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonTimeInMinutesActionPerformed
    	if(!jRadioButtonTimeInMinutes.isSelected()) {
    		jRadioButtonFinishDate.setSelected(false);
        	jSpinnerTime.setEnabled(false);
        	jTextFieldRuntimeInMinutes.setEnabled(true);
    	}
    	
    }//GEN-LAST:event_jRadioButtonTimeInMinutesActionPerformed

    private void jRadioButtonFinishDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonFinishDateActionPerformed
    	if(!jRadioButtonFinishDate.isSelected()) {
    		jRadioButtonTimeInMinutes.setSelected(false);
        	jTextFieldRuntimeInMinutes.setEnabled(false);
        	jSpinnerTime.setEnabled(true);
    	}
    	
    }//GEN-LAST:event_jRadioButtonFinishDateActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
    	dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonAddAndStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddAndStartActionPerformed
    	addAndStartCountdown();
    }//GEN-LAST:event_jButtonAddAndStartActionPerformed

    

    /**
     * adds a countdown
     * @param evt
     */
	private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
		MediaFile countdown = addCountdown();
		if(countdown != null) {
			dispose();
		}

    }//GEN-LAST:event_jButtonAddActionPerformed

	/**
	 * adds a countdown and starts it if it was successfully added.
	 */
	private void addAndStartCountdown() {
		MediaFile countdown = addCountdown();
		if(countdown != null) {
			DisplayHandler.getInstance().show(countdown);
			dispose();
		}
	}
	
	/**
	 * Creates a new {@link Countdown} reading the data from the dialogs inputs.
	 * 
	 * @return null if no countdown was added, else the media file representing the countdown
	 */
    @SuppressWarnings("deprecation")
	private MediaFile addCountdown() {
		
    	String name = jTextFieldName.getText();
    	
    	if(name.equals("")) {
			JOptionPane.showMessageDialog(this, "Error adding the countdown. \nPlease enter a valid name.", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
    	}

    	if(jRadioButtonFinishDate.isSelected()) {
    		
    		SpinnerDateModel dateModel = (SpinnerDateModel) jSpinnerTime.getModel();
    		Date finishDate = dateModel.getDate();
    		finishDate.setYear(new Date().getYear());
    		int compare = finishDate.compareTo(new Date());
    		if(!(compare > 0)) {
    			JOptionPane.showMessageDialog(this, "Please choose a valid Date.\nThe Date you have choosen is in the past.", "Error", JOptionPane.ERROR_MESSAGE);
    			return null;
    		}
    		
    		MediaHandler mediaHandler = MediaHandler.getInstance();
			MediaFile[] countdown = new MediaFile[1];
			countdown[0] = new Countdown(name, finishDate);
			mediaHandler.add(countdown);
			return countdown[0];
    		
    	} else if(jRadioButtonTimeInMinutes.isSelected()) {
    		
    		try {
				int timeInMinutes = Integer.parseInt(jTextFieldRuntimeInMinutes.getText());
				MediaHandler mediaHandler = MediaHandler.getInstance();
				MediaFile[] countdown = new MediaFile[1];
				countdown[0] = new Countdown(name, timeInMinutes);
				mediaHandler.add(countdown);
				return countdown[0];
				
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Error adding the countdown. \nPlease enter a valid time.", "Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}
    		
    		
    	} else {
    		JOptionPane.showMessageDialog(this, "Error adding the countdown.", "Error", JOptionPane.ERROR_MESSAGE);
    		return null;
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
            java.util.logging.Logger.getLogger(AddCountdownDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddCountdownDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddCountdownDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddCountdownDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddCountdownDialog dialog = new AddCountdownDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonAddAndStart;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButtonFinishDate;
    private javax.swing.JRadioButton jRadioButtonTimeInMinutes;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSpinner jSpinnerTime;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldRuntimeInMinutes;
    // End of variables declaration//GEN-END:variables
}
