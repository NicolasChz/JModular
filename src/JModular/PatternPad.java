package JModular;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PatternPad extends JPanel{

	JButton send,open,store;
	JLabel State;
		
	public PatternPad(){		
		send=new JButton(); // ce bouton envoie une s�quence m�moris�e vers le s�quenceur li� au patternUnit 
		store=new JButton();	// ce bouton m�morise la s�quence d'un s�quenceur li� dans le pad en cours du patternUnit
		State=new JLabel("void");	// ce champ affiche (en chaine de caract�res) le d�but de la s�quence m�moris�e	
		display();		
	}
	
	public void display(){	
		setLayout(new BorderLayout());	
		setBackground(Color.GRAY);
		send.setIcon(new ImageIcon("ressources/sendPatternbtn.png"));
		store.setIcon(new ImageIcon("ressources/getPatternbtn.png"));
		
		Dimension bigButton=new Dimension(100,45); 
		Dimension smallButton=new Dimension(50,40);		
		send.setPreferredSize(bigButton);
		store.setPreferredSize(smallButton);		
				
		javax.swing.border.Border border = BorderFactory.createLineBorder(Color.white);

		this.setBorder(BorderFactory.createBevelBorder(1, Color.lightGray, Color.DARK_GRAY));
		this.setPreferredSize(new Dimension(100,95));
		
		Font PadFont = new Font("Arial", Font.TRUETYPE_FONT, 10);
		State.setHorizontalAlignment(SwingConstants.CENTER);
		State.setBackground(Color.BLACK);
		State.setFont(PadFont);
		State.setForeground(Color.cyan);
		add(State,BorderLayout.NORTH);
		add(send,BorderLayout.CENTER);
		add(store,BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
		
}
