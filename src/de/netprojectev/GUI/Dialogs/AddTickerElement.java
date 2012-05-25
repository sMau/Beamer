/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.netprojectev.GUI.Dialogs;

import de.netprojectev.GUI.Manager.FileManagerTableModel;
import de.netprojectev.GUI.Manager.ManagerFrame;
import de.netprojectev.GUI.Manager.TickerManagerTableModel;
import de.netprojectev.LiveTicker.LiveTicker;
import de.netprojectev.LiveTicker.TickerTextElement;
import de.netprojectev.Misc.Misc;

/**
 * GUI class dialog, to handle ticker-element adding and editing.
 * @author samu
 */
public class AddTickerElement extends javax.swing.JFrame {


	private static final long serialVersionUID = -3446932107371644570L;
    
    private TickerTextElement currentTickerElt;
    private Boolean editMode;
    
    private ManagerFrame managerFrame;
    
    /**
     * 
     * @param tickerElt The element to edit.
     */
    public AddTickerElement(TickerTextElement tickerElt, ManagerFrame managerFrame) {
        this.currentTickerElt = tickerElt;
        this.managerFrame = managerFrame;
        initComponents();
        setLocation(Misc.currentMousePosition());
        if(tickerElt == null) { //New Ticker Elt.
            currentTickerElt = new TickerTextElement("New element");
            editMode = false;
        } else { //Edit selected ticker Elt.
            currentTickerElt = tickerElt;
            jButtonAddTickerElt.setText("Edit");
            editMode = true;
        }
        jTextFieldTickerEltText.setText(currentTickerElt.getText());
        jCheckBoxTickerEltShow.setSelected(currentTickerElt.getToShow());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonAddTickerElt = new javax.swing.JButton();
        jButtonAddTickerEltCancel = new javax.swing.JButton();
        jCheckBoxTickerEltShow = new javax.swing.JCheckBox();
        jTextFieldTickerEltText = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Ticker Element");

        jButtonAddTickerElt.setText("Add");
        jButtonAddTickerElt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddTickerEltActionPerformed(evt);
            }
        });

        jButtonAddTickerEltCancel.setText("Cancel");
        jButtonAddTickerEltCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddTickerEltCancelActionPerformed(evt);
            }
        });

        jCheckBoxTickerEltShow.setText("Show");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 342, Short.MAX_VALUE)
                        .addComponent(jButtonAddTickerEltCancel)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonAddTickerElt))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jCheckBoxTickerEltShow)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTextFieldTickerEltText))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jCheckBoxTickerEltShow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextFieldTickerEltText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAddTickerElt)
                    .addComponent(jButtonAddTickerEltCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAddTickerEltCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddTickerEltCancelActionPerformed
        dispose();
    }//GEN-LAST:event_jButtonAddTickerEltCancelActionPerformed

    /**
     * Edits a ticker element or adds a new ticker element to the @see LiveTicker
     */
    private void jButtonAddTickerEltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddTickerEltActionPerformed
    	final int selectedRow = managerFrame.getjTableLiveticker().getSelectedRow();
    	currentTickerElt.setText(jTextFieldTickerEltText.getText());
        currentTickerElt.setToShow(jCheckBoxTickerEltShow.isSelected());
    	
    	if(!editMode) {
    		LiveTicker.getInstance().add(currentTickerElt);
    	}
    	if(managerFrame != null) {
			((TickerManagerTableModel) managerFrame.getjTableLiveticker().getModel()).updateModel();
			java.awt.EventQueue.invokeLater(new Runnable() {

	            public void run() {
	            	if(selectedRow >= 0 && selectedRow <= managerFrame.getjTableLiveticker().getRowCount() - 1) {
	            		managerFrame.getjTableLiveticker().setRowSelectionInterval(selectedRow, selectedRow);
	            	}
	            }
	        });
		}
    	dispose();    
    }//GEN-LAST:event_jButtonAddTickerEltActionPerformed



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
            java.util.logging.Logger.getLogger(AddTickerElement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddTickerElement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddTickerElement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddTickerElement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new AddTickerElement(null, null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddTickerElt;
    private javax.swing.JButton jButtonAddTickerEltCancel;
    private javax.swing.JCheckBox jCheckBoxTickerEltShow;
    private javax.swing.JTextField jTextFieldTickerEltText;
    // End of variables declaration//GEN-END:variables
}
