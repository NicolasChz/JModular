package JModular;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Insets;



public class SequencerPanel extends ModulePanel{
	 JButton btn;
	 SequencerUnit Sequencer;	 
	 JComboBox Targetlist;
	 Model model;	 
	 String[] notes = { "-", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
	 ArrayList<JComboBox> Steps=new ArrayList<JComboBox>();	 
	 ArrayList<StepSlider> StpSliders=new ArrayList<StepSlider>();	
	 int ID;
	 String[] octaves={"0","1","2","3","4","5"};	 
 	 JComboBox Octave = new JComboBox(octaves);
	 JFileChooser Filepicker=new JFileChooser();	 
	 JButton savebtn =new JButton();
	 JButton loadbtn =new JButton();
	 JButton resetbtn =new JButton();
	 JLabel configFilePathLabel=new JLabel();
	 StepSlider slider = new StepSlider();

	public SequencerPanel(Model model){
		super("Sequencer");
		this.model=model;
		this.Sequencer=model.SequencerUnits.get(model.SequencerUnits.size()-1);
		this.ID=Sequencer.ID;
		display();
		refreshTargetlist();		
	} //end creator	

	public void display(){		
		setLayout(new FlowLayout()); 
	   	LineBorder line = new LineBorder(Color.lightGray, 2, true);
	   	setBorder(line);
	   	setBackground(Color.white);	 		
	   	Font font = new Font("default", Font.TRUETYPE_FONT, 10);
	   	JLabel ID=new JLabel();
	   	JLabel octavelabel=new JLabel("Octave ");
	   	octavelabel.setFont(font);
	   	JLabel TargetLabel=new JLabel("Send to ");
	   	TargetLabel.setFont(font);
	   	IDLabel.setText(""+Sequencer.ID);		
		Targetlist=new JComboBox();
		btn=new JButton();
		btn.setText("Play");
	    loadbtn.setIcon(new ImageIcon("ressources/load.png"));
	    savebtn.setIcon(new ImageIcon("ressources/save.png"));
	    resetbtn.setIcon(new ImageIcon("ressources/reset.png"));
	    loadbtn.setBorder(null);
	    savebtn.setBorder(null);
	    resetbtn.setBorder(null);
	    
	 /// panneau ID
	    JPanel IDPanel = new JPanel();
	    IDPanel.setLayout(new FlowLayout()); 
	    IDPanel.add(IDLabel,BorderLayout.WEST);	    
	   	add(IDPanel,BorderLayout.NORTH);
	   		
	//// panneau step
	   	JPanel StepPanel=new JPanel();
			for (int i=0; i<=15; i++){
		   		StepSlider step=new StepSlider();
		   		StpSliders.add(step);
		   		StepPanel.add(step);
		   	}
		add(StepPanel,BorderLayout.CENTER);
		
		//// panneau inférieur
		   	JPanel GroupPanel = new JPanel();
		   	GroupPanel.setLayout(new GridBagLayout());
		   	GridBagConstraints c = new GridBagConstraints();
		   	c.fill = GridBagConstraints.HORIZONTAL;
		   	c.insets = new Insets(5,5,5,5);  
		   	c.gridx = 0;
		   	c.gridy = 0;
				    JPanel OctavePanel=new JPanel();				    
				    	OctavePanel.setLayout(new BorderLayout());	    	
				    	OctavePanel.add(octavelabel, BorderLayout.WEST);	// ajout label "Octave"
				    	OctavePanel.add(Octave,BorderLayout.EAST);			//ajout combobox du choix de l'octave
		    GroupPanel.add(OctavePanel,c);				
					JPanel TargetPanel = new JPanel();
						TargetPanel.setLayout(new BorderLayout());
						TargetPanel.add(TargetLabel,BorderLayout.WEST);		// ajout label "link to"
						refreshTargetlist();
						TargetPanel.add(Targetlist,BorderLayout.EAST);		//ajout comboBox target (choix du générateur ou sampler cible)
			c.gridx = 0;
			c.gridy = 1;
			GroupPanel.add(TargetPanel,c);
			c.gridx = 0;
			c.gridy = 2;
			GroupPanel.add(btn,c);	
				    JPanel filePanel=new JPanel();
					    filePanel.setLayout(new GridBagLayout());
					    GridBagConstraints c2 = new GridBagConstraints();
					    c2.gridx=0;
					    c2.gridy=0;
					    c2.insets = new Insets(0,5,0,5);
					    filePanel.add(loadbtn,c2);		// ajout bouton load
					    c2.gridx=1;
					    c2.gridy=0;
					    filePanel.add(savebtn,c2);		//ajout bouton save
					    c2.gridx=2;
					    c2.gridy=0;
					    filePanel.add(resetbtn,c2);		//ajout bouton reset
			c.gridx = 0;
			c.gridy = 3;
			GroupPanel.add(filePanel,c);			
		add (GroupPanel,BorderLayout.EAST);
		refreshTargetlist();
		displayconfig();
		this.setVisible(true);	
	}
	
	public void setbtn(String text){
		btn.setText(text);
		repaint();
	}

	public void refreshTargetlist(){			
		Object alreadyselectedItem=null;		
		if (Targetlist.getItemCount()>0) {
			alreadyselectedItem=Targetlist.getSelectedItem();			
			Targetlist.removeAllItems();	
		}
		for (TargetUnit target : model.TargetUnits){ Targetlist.addItem(""+target.ID); }
		Targetlist.setSelectedItem(alreadyselectedItem);	
	}	
	
	public Dimension getPreferredSize() {
		return new Dimension(800, 170);
	}
	
	
	public void displayconfig(){		
		String SequencetoDisplay[]=Sequencer.getSequence();
		ArrayList<String> notes = new  ArrayList<String>(Arrays.asList(SequencetoDisplay));
		int i=0;
		for (StepSlider step : StpSliders) {
			step.setnote(notes.get(i));
			if (notes.get(i).compareTo("-")==0)step.switschStatus("OFF"); //changement de couleur du bouton : gris (note muette)
			else step.switschStatus("ON");  // changement de couleur : bleu
			i++;
		}
	}	
		
	
}
