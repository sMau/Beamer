package de.netprojectev.Tests;

import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;

public class FullscreenWorking extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton gross, klein, exit;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FullscreenWorking frame = new FullscreenWorking();
		frame.setVisible(true);
	}

	public FullscreenWorking() {
		setLayout(new FlowLayout());
		gross = new JButton("Groß");
		gross.addActionListener(this);
		klein = new JButton("Klein");
		klein.addActionListener(this);
		exit = new JButton(new AbstractAction("Exit") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -6915307674087790957L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		add(exit);
		add(gross);
		add(klein);
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		if (src == gross) {
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			GraphicsDevice myDevice = ge.getDefaultScreenDevice();
			dispose();
			this.setUndecorated(true);
			setVisible(true);
			// this.setAlwaysOnTop(true);
			myDevice.setFullScreenWindow(this);
		} else {
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			GraphicsDevice myDevice = ge.getDefaultScreenDevice();
			dispose();
			this.setUndecorated(false);
			setVisible(true);
			myDevice.setFullScreenWindow(null);
			// this.setAlwaysOnTop(false);
			pack();
		}
	}

}
