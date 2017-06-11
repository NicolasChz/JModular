package JModular;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TempoPanel extends JPanel{  //ce panneau n'hérite pas de ModulePanel (affichage et positionnement différents dans le dashboard)

	JLabel ID=new JLabel();	
	JLabel Time=new JLabel();	
	JLabel BeatLabel=new JLabel();
	private Model model;
	private int i=0;
	private JLabel image;
	private ImageIcon Icon=new ImageIcon( "circle.png");
	JSlider slider = new JSlider(JSlider.HORIZONTAL,20,130,100);
	int stepPeriod=162; //ms correspondant à la valeur init de 120bpm


	public TempoPanel(Model model) {
		
		this.model=model;		
		display();
	}
	
	public void display(){
		setLayout(new FlowLayout());
	   	slider.setMinorTickSpacing(10);  
	   	slider.setPaintTicks(true);  
	   	slider.setPaintLabels(true);
	   	
	   	slider.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {	        	
	        	stepPeriod=((60*1000)/(8*slider.getValue()));
	    		model.bpmUnit.settempo(stepPeriod);
	    		ID.setText(slider.getValue()+" BMP");
	        	ID.repaint();	        	
	        }
	      });	   	
	   	
		add(slider);
		ID.setText(slider.getValue()+" BMP");
		ID.setForeground(Color.DARK_GRAY);				
	   	add(ID);

	   	this.setLayout( new FlowLayout());
	    this.setVisible(true);	
	}

	public int getslidervalue(){
	return slider.getValue();
	
	}

}
