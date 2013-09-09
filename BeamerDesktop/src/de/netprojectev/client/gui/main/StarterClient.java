package de.netprojectev.client.gui.main;

import javax.swing.UnsupportedLookAndFeelException;


public class StarterClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.setProperty("sun.java2d.opengl", "True");
		
		//TODO add memory params to the vm
		//TODO check which look and feel to use
		
		/*try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			
			e.printStackTrace();
			*/
			try {
	            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	                if ("Nimbus".equals(info.getName())) {
	                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                    break;
	                }
	            }
	        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
	        	e1.printStackTrace();
	        	System.exit(0);
	        }
		//}	
		

	        java.awt.EventQueue.invokeLater(new Runnable() {
	            public void run() {
	            	LoginDialog dialog = new LoginDialog(new javax.swing.JFrame(), true);
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

}
