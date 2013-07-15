/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.netprojectev.client.gui.main;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

import org.apache.logging.log4j.Logger;

import de.netprojectev.client.datastructures.ClientMediaFile;
import de.netprojectev.client.gui.main.CreateTickerElementDialog.DialogClosedListener;
import de.netprojectev.client.gui.tablemodels.AllMediaTableModel;
import de.netprojectev.client.gui.tablemodels.CustomQueueTableModel;
import de.netprojectev.client.gui.tablemodels.TickerTableModel;
import de.netprojectev.client.model.MediaModelClient;
import de.netprojectev.client.model.TickerModelClient;
import de.netprojectev.client.networking.ClientMessageProxy;
import de.netprojectev.misc.ImageFileFilter;
import de.netprojectev.misc.LoggerBuilder;

/**
 * 
 * @author samu
 */
public class MainClientGUIWindow extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4247573311423083026L;
	private static final Logger log = LoggerBuilder.createLogger(MainClientGUIWindow.class);

	private final ClientMessageProxy proxy;
	private final MediaModelClient mediaModel;
	private final TickerModelClient tickerModel;

	/**
	 * Creates new form MainClientGUIWindow
	 */
	public MainClientGUIWindow(MediaModelClient mediaModel, TickerModelClient tickerModel, ClientMessageProxy proxy) {
		this.mediaModel = mediaModel;
		this.tickerModel = tickerModel;
		this.proxy = proxy;
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPaneContainer = new javax.swing.JTabbedPane();
        jpAllMedia = new javax.swing.JPanel();
        jspMediaTableContainer = new javax.swing.JScrollPane();
        jtAllMedia = new javax.swing.JTable();
        jpCustomQueue = new javax.swing.JPanel();
        jspQueueTableContainer = new javax.swing.JScrollPane();
        jtCustomQueue = new javax.swing.JTable();
        jpLiveTicker = new javax.swing.JPanel();
        jspTickerTableContainer = new javax.swing.JScrollPane();
        jtLiveTicker = new javax.swing.JTable();
        jpButtonContainer = new javax.swing.JPanel();
        jbAddFile = new javax.swing.JButton();
        jbAddThemeslide = new javax.swing.JButton();
        jbAddTickerElement = new javax.swing.JButton();
        jbEdit = new javax.swing.JButton();
        jbRemoveFromList = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Beamer Client");

        jtAllMedia.setModel(new AllMediaTableModel(mediaModel));
        jtAllMedia.getColumnModel().getColumn(0).setCellRenderer(new TableButtonRenderer());
        jtAllMedia.getColumnModel().getColumn(0).setCellEditor(new ButtonEditor(new JCheckBox(), new ButtonEditor.TableButtonActionListener() {
            public void buttonClicked(int row) {
                queueButtonClicked(row);
            }
        }));
        jspMediaTableContainer.setViewportView(jtAllMedia);

        javax.swing.GroupLayout jpAllMediaLayout = new javax.swing.GroupLayout(jpAllMedia);
        jpAllMedia.setLayout(jpAllMediaLayout);
        jpAllMediaLayout.setHorizontalGroup(
            jpAllMediaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspMediaTableContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 853, Short.MAX_VALUE)
        );
        jpAllMediaLayout.setVerticalGroup(
            jpAllMediaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpAllMediaLayout.createSequentialGroup()
                .addComponent(jspMediaTableContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPaneContainer.addTab("Media", jpAllMedia);

        jtCustomQueue.setModel(new CustomQueueTableModel(mediaModel));
        jtCustomQueue.getColumnModel().getColumn(0).setCellRenderer(new TableButtonRenderer());
        jtCustomQueue.getColumnModel().getColumn(0).setCellEditor(new ButtonEditor(new JCheckBox(), new ButtonEditor.TableButtonActionListener() {
            public void buttonClicked(int row) {
                deQueueButtonClicked(row);
            }
        }));
        jspQueueTableContainer.setViewportView(jtCustomQueue);

        javax.swing.GroupLayout jpCustomQueueLayout = new javax.swing.GroupLayout(jpCustomQueue);
        jpCustomQueue.setLayout(jpCustomQueueLayout);
        jpCustomQueueLayout.setHorizontalGroup(
            jpCustomQueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspQueueTableContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 853, Short.MAX_VALUE)
        );
        jpCustomQueueLayout.setVerticalGroup(
            jpCustomQueueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspQueueTableContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
        );

        tabbedPaneContainer.addTab("Queue", jpCustomQueue);

        jtLiveTicker.setModel(new TickerTableModel(tickerModel));
        jspTickerTableContainer.setViewportView(jtLiveTicker);

        javax.swing.GroupLayout jpLiveTickerLayout = new javax.swing.GroupLayout(jpLiveTicker);
        jpLiveTicker.setLayout(jpLiveTickerLayout);
        jpLiveTickerLayout.setHorizontalGroup(
            jpLiveTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspTickerTableContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 853, Short.MAX_VALUE)
        );
        jpLiveTickerLayout.setVerticalGroup(
            jpLiveTickerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jspTickerTableContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
        );

        tabbedPaneContainer.addTab("Ticker", jpLiveTicker);

        jbAddFile.setText("Add File");
        jbAddFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddFileActionPerformed(evt);
            }
        });

        jbAddThemeslide.setText("Add Themeslide");
        jbAddThemeslide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddThemeslideActionPerformed(evt);
            }
        });

        jbAddTickerElement.setText("Add Tickerelement");
        jbAddTickerElement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAddTickerElementActionPerformed(evt);
            }
        });

        jbEdit.setText("Edit");
        jbEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEditActionPerformed(evt);
            }
        });

        jbRemoveFromList.setText("Remove");
        jbRemoveFromList.setToolTipText("");
        jbRemoveFromList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRemoveFromListActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpButtonContainerLayout = new javax.swing.GroupLayout(jpButtonContainer);
        jpButtonContainer.setLayout(jpButtonContainerLayout);
        jpButtonContainerLayout.setHorizontalGroup(
            jpButtonContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpButtonContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbAddFile)
                .addGap(18, 18, 18)
                .addComponent(jbAddThemeslide)
                .addGap(18, 18, 18)
                .addComponent(jbAddTickerElement)
                .addGap(18, 18, 18)
                .addComponent(jbEdit)
                .addGap(18, 18, 18)
                .addComponent(jbRemoveFromList)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpButtonContainerLayout.setVerticalGroup(
            jpButtonContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpButtonContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpButtonContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAddFile)
                    .addComponent(jbAddThemeslide)
                    .addComponent(jbAddTickerElement)
                    .addComponent(jbRemoveFromList)
                    .addComponent(jbEdit))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPaneContainer)
            .addComponent(jpButtonContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jpButtonContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabbedPaneContainer, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void jbAddFileActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbAddFileActionPerformed
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setApproveButtonText("Add");
		fileChooser.setDialogTitle("Add Files");
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setFileFilter(new ImageFileFilter());
		int returnVal = fileChooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			proxy.sendAddImageFiles(fileChooser.getSelectedFiles());
		}

	}// GEN-LAST:event_jbAddFileActionPerformed

	private void jbAddThemeslideActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbAddThemeslideActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jbAddThemeslideActionPerformed

	private void jbAddTickerElementActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbAddTickerElementActionPerformed
		new CreateTickerElementDialog(this, true, new DialogClosedListener() {
			
			@Override
			public void dialogClosed(int result, String text) {
				if(result == 1) {
					proxy.sendAddTickerElement(text);
				}
			}
		}).setVisible(true);
	}// GEN-LAST:event_jbAddTickerElementActionPerformed

	private void jbEditActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbEditActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jbEditActionPerformed

	private void jbRemoveFromListActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbRemoveFromListActionPerformed
		int selectedTab = tabbedPaneContainer.getSelectedIndex();
		
		switch (selectedTab) {
		case 0:
			int[] selectedRowsAllMedia = jtAllMedia.getSelectedRows();
			proxy.sendRemoveSelectedMedia(selectedRowsAllMedia);
			break;
		case 1:
			int[] selectedRowsCustomQueue = jtCustomQueue.getSelectedRows();
			proxy.sendDequeueSelectedMedia(selectedRowsCustomQueue);
			break;
		case 2:
			int[] selectedRowsLiveTicker = jtLiveTicker.getSelectedRows();
			proxy.sendRemoveSelectedTickerElements(selectedRowsLiveTicker);
			break;
		default:
			log.debug("remove: no propper tab selected");
			break;

		}
	}// GEN-LAST:event_jbRemoveFromListActionPerformed

	
	private void queueButtonClicked(int row) {
		proxy.sendQueueSelectedMedia(row);
	}
	
	protected void deQueueButtonClicked(int row) {
		proxy.sendDequeueSelectedMedia(row);
	}

	
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed"
		// desc=" Look and feel setting code (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MainClientGUIWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MainClientGUIWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MainClientGUIWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainClientGUIWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MainClientGUIWindow(null, null, null).setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbAddFile;
    private javax.swing.JButton jbAddThemeslide;
    private javax.swing.JButton jbAddTickerElement;
    private javax.swing.JButton jbEdit;
    private javax.swing.JButton jbRemoveFromList;
    private javax.swing.JPanel jpAllMedia;
    private javax.swing.JPanel jpButtonContainer;
    private javax.swing.JPanel jpCustomQueue;
    private javax.swing.JPanel jpLiveTicker;
    private javax.swing.JScrollPane jspMediaTableContainer;
    private javax.swing.JScrollPane jspQueueTableContainer;
    private javax.swing.JScrollPane jspTickerTableContainer;
    private javax.swing.JTable jtAllMedia;
    private javax.swing.JTable jtCustomQueue;
    private javax.swing.JTable jtLiveTicker;
    private javax.swing.JTabbedPane tabbedPaneContainer;
    // End of variables declaration//GEN-END:variables
}
