package de.netprojectev.tests;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.netprojectev.gui.manager.ManagerFrame;

public class ButtonColoring extends JFrame {

	
	public ButtonColoring() {
		
		setName("Button coloring");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 200, 200);
		JPanel panel = new JPanel();
		JButton button = new JButton("This is a test");
		
		button.setBackground(Color.BLACK);
		button.setForeground(Color.BLACK);
		
		panel.add(button);
		this.add(panel);
		
	}
	

	public static void main(String[] args) {

		try {
	        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
	            if ("Nimbus".equals(info.getName())) {
	                javax.swing.UIManager.setLookAndFeel(info.getClassName());
	                break;
	            }
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new ButtonColoring().setVisible(true);
				
			}
		});
		
		
	}

}
