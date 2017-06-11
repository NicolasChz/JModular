package JModular;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;

public class MainPanel extends JPanel {	
    public MainPanel() {
    	super();
    	setBackground(Color.darkGray);
    	setPreferredSize(new Dimension(800, 800));	
    } 
}

class Display {
    public static void main(String[] arg) {	
		JFrame cadre = new javax.swing.JFrame();
		JPanel mainpanel=new MainPanel();
		cadre.pack();
		cadre.setVisible(true);
		cadre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

