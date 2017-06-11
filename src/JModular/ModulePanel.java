package JModular;

// classe encêtre des panneaux de modules 

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public abstract class ModulePanel extends JPanel{

	String Moduletype;
	JLabel IDLabel=new JLabel("", SwingConstants.CENTER);
	private ImageIcon background;

	
	public ModulePanel(String Moduletype){
		super();
		this.Moduletype=Moduletype;	
		this.setLayout(new GridBagLayout());
	   	LineBorder line = new LineBorder(Color.lightGray, 2, true);
	   	IDLabel.setPreferredSize(new Dimension (25,25));
		IDLabel.setBorder(line);
		IDLabel.setOpaque(isOpaque());
		IDLabel.setForeground(Color.LIGHT_GRAY);
   		IDLabel.setBackground(Color.darkGray);	
   		//affichage d'une image de fond pour les panneaux:
		if (Moduletype.compareTo("Sequencer")==0)
			this.background = settexture("ressources/LargePanel.jpg");  // un double panneau pour les modules de type séquenceur !
		else this.background = settexture("ressources/SmallPanel.png"); 
	}

	public String GetModuleType(){
		return Moduletype;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(397, 170);
	}
	
	public void setIDLabel(String ID){	
		IDLabel.setText(ID);
	}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background.getImage(), 0, 0, this);
    }

    public ImageIcon settexture(String ImagePath){
    	ImageIcon texture= new ImageIcon(ImagePath);
    	return texture;
    }

	void add(Component someComponent, int x, int y, int width) {		
	}
	
    
}
 