package de.netprojectev.tests;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class FontSetTest extends JFrame {

	public FontSetTest() {
		
		setBounds(500, 500, 500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Parent parent = new Parent();
		Child child = new Child();
		parent.setOpaque(false);
		child.setOpaque(false);
		
		child.setForeground(Color.GREEN);
		
		parent.setLayout(new BorderLayout());
		parent.add(child);
		add(parent);
		
		parent.setForeground(Color.BLUE);
		repaint();
		parent.repaint();
		child.repaint();
		
	}
	
	public static void main(String[] args) {
		
		new FontSetTest().setVisible(true);
		
	}
	
}

class Parent extends JComponent {

	@Override
	protected void paintComponent(Graphics g) {
		Graphics gTmp = g.create();
		gTmp.drawString("PARENT", 10, 10);
		gTmp.dispose();
	}
	
}

class Child extends JComponent {

	@Override
	protected void paintComponent(Graphics g) {
		Graphics gTmp = g.create();
		gTmp.drawString("CHILD", 100, 100);
		gTmp.dispose();
	}
}