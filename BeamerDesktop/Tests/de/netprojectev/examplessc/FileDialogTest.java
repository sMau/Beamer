package de.netprojectev.examplessc;

import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;

/** @see http://stackoverflow.com/questions/2914733 */
public class FileDialogTest {

	public static void main(String[] args) {

		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println(e);
		}

		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(0, 1));
		frame.add(new JButton(new AbstractAction("Load") {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog(frame, "Test", FileDialog.LOAD);
				fd.setMultipleMode(true);
				fd.setVisible(true);

				System.out.println(fd.getFile());
			}
		}));
		frame.add(new JButton(new AbstractAction("Save") {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog(frame, "Test", FileDialog.SAVE);
				fd.setMultipleMode(true);
				fd.setVisible(true);
				System.out.println(fd.getFile());
			}
		}));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
}
