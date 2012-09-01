package de.netprojectev.Tests;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
 
public class TickerEx extends JPanel implements Runnable {
    private static final long serialVersionUID = 1L;
    private JLabel tickerLabel;
    private String tickerString;
    private int x = 0;
    private int step = 2;
    private int delay = 80;
 
    public TickerEx() {
        initComponents();
    }
 
    private void initComponents() {
        setLayout(new FlowLayout());
        this.setBackground(Color.RED);
        setOpaque(true);
        tickerString = "Das ist nur der Teststring";
        tickerLabel = new JLabel(tickerString);
        add(tickerLabel);
        tickerLabel.setLocation(x, 0);
        start();
    }
 
    private void start() {
        Thread t = new Thread(this);
        t.setDaemon(true);
        t.start();
    }
 
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(new Dimension(300, 300));
        f.setLocationRelativeTo(null);
        TickerEx ltp = new TickerEx();
        f.add(BorderLayout.SOUTH, ltp);
        f.setVisible(true);
    }
 
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (isVisible()) {
                int widthX = getWidth();
                int laenge = tickerLabel.getWidth();
                if (x > -laenge) {
                    x = x - step;
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            tickerLabel.setLocation(x, 0);
                        }
                    });
                } else {
                    x = widthX;
                }
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}