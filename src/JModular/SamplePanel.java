package JModular;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.LineBorder;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.swing.DoubleBoundedRangeModel;
import com.jsyn.swing.PortModelFactory;
import com.jsyn.swing.RotaryController;



public class SamplePanel extends ModulePanel{

	 JButton browsebtn =new JButton();
	 SampleUnit Sampler;
	
	 int ID;
	 Model model;
	 JFileChooser Filepicker=new JFileChooser();
	 String FilePathLabel="Kick03.wav";
	 RotaryController Knob=null; 
	 
	public SamplePanel(Model model){
		super("Sample");
		this.model=model;
		this.Sampler=model.SampleUnits.get(model.SampleUnits.size()-1);
		this.ID=Sampler.ID;		
		display();			     
	} //end creator	

	
	public void display(){		
		setLayout(new GridBagLayout());
	   	GridBagConstraints c = new GridBagConstraints();
	   	c.fill = GridBagConstraints.HORIZONTAL;
	   	c.insets = new Insets(5,5,5,5);  
	   	   	
	   	LineBorder line = new LineBorder(Color.lightGray, 2, true);
	   	setBorder(line);
	   	setBackground(Color.white);	 
		c.gridx = 0;
	   	c.gridy = 0;

	   	IDLabel.setText(""+Sampler.ID);
	   	add(IDLabel,c);

	   	setFilePathLabel(FilePathLabel);
	   	c.gridx = 1;
	   	c.gridy = 0;
	   	add(BrowsePanel(),c);
	   	
	   	c.gridx = 2;
	   	c.gridy = 0;	   	
	    add(volumePanel(), c);
	    
	    this.setVisible(true);	
	}
	// potentionmétre de volume 
	 private RotaryController setupPortKnob(UnitInputPort port) {
	        DoubleBoundedRangeModel model = PortModelFactory.createExponentialModel(port);
	        RotaryController knob = new RotaryController(model);
	        knob.setLineColor(Color.darkGray);
	        return knob;
	  }	
	
	 // panneau du bouton de volume
	 private SubPanel volumePanel(){
	    	SubPanel volumePanel=new SubPanel("ressources/VolumePanel.png");    	
	    	volumePanel.setPreferredSize(new Dimension(80,130));
	    	volumePanel.setLayout(new GridBagLayout());   
	    	GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
	    	c.gridx = 0;
		   	c.gridy = 0;
	    	volumePanel.add(setupPortKnob(Sampler.samplePlayer.amplitude),c);
	    	return volumePanel;
	 }
	 
	 // panneau du bouton d'exploration de samples:
	 private SubPanel BrowsePanel(){
	    	SubPanel BrowsePanel=new SubPanel("ressources/BrowseSamplePanel.png");    	
	    	BrowsePanel.setPreferredSize(new Dimension(120,130));
	    	BrowsePanel.setLayout(new GridBagLayout());   
	    	GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
	    	c.gridx = 0;
		   	c.gridy = 0;
		   	BrowsePanel.add(browsebtn,c);
	    	return BrowsePanel;
	 }
	 
	// affichage du nom de fichier audio sur le bouton: 
	public void setFilePathLabel(String FilePath){
		this.browsebtn.setText(FilePath);
		if (Knob!=null){
		this.remove(Knob);
	   	Knob = setupPortKnob(Sampler.samplePlayer.amplitude); //mise à jour du potentionmtre
	    this.add(Knob);
		}				
	}

	
}
