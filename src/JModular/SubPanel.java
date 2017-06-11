package JModular;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class SubPanel extends JPanel {

	private ImageIcon background;

	public SubPanel(String ImagePath){		
		this.background = settexture(ImagePath);       
		LineBorder line = new LineBorder(Color.lightGray, 1, true);	 
		setBorder(line);
	}
	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background.getImage(), 0, 0, this);
    }

    public ImageIcon settexture(String ImagePath){
    	ImageIcon texture= new ImageIcon(ImagePath);
    	return texture;
    }
}
