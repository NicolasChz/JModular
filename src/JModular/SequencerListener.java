package JModular;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;


public class SequencerListener implements ActionListener{
	
	private SequencerUnit Sequencer;
	private SequencerPanel Panel;
	Model model;
	
	public SequencerListener(Model model , SequencerPanel Panel){
		this.model=model;
		this.Sequencer=model.SequencerUnits.get(model.SequencerUnits.size()-1);	
		this.Panel=Panel;
	}
	
	
	public void actionPerformed(ActionEvent e) {    	

		    	if (e.getSource()==Panel.btn){	
		    		if(Sequencer.Target!=null){ //lancement de la lecture si une cible est choisie
			    		if ( Panel.btn.getText().compareTo("Play")==0 ){ Sequencer.Play(); Panel.btn.setText("Stop"); } // si le bouton affiche Play, on lance la lecture et on affiche Stop
			    		else if ( Panel.btn.getText().compareTo("Stop")==0 ){ Sequencer.Stop(); Panel.btn.setText("Play");}	// si le bouton affiche Stop, on arrete la lecture et on affiche Play
		    		}
		    	}

		    	if (e.getSource()==Panel.Targetlist){		 
		    		int selectedID=Panel.Targetlist.getSelectedIndex();
		    		if (selectedID <=0) selectedID=0;
		    		Sequencer.setTarget(model.TargetUnits.get(selectedID));
		    	}

		    	if (e.getSource()==Panel.Octave) {		 
		    		String selectedOctave=(String) Panel.Octave.getSelectedItem();		
		    		Sequencer.setOctave(selectedOctave);    		
		    	}
		    	
		    	if (e.getSource()==Panel.resetbtn){
		    		Sequencer.init();
		    		Panel.displayconfig();
		    	}
		    	
		    	if (e.getSource()==Panel.savebtn){		 
		    		JFileChooser filechoose = new JFileChooser();
		    		   filechoose.setCurrentDirectory(new File("./Sequences")); 
		    		   String approve = new String("ENREGISTRER");   
		    		   int resultatEnregistrer = filechoose.showDialog(filechoose,approve); 
		    		   if (resultatEnregistrer ==JFileChooser.APPROVE_OPTION) { String monFichier= new String(filechoose.getSelectedFile().toString());
					    try{ 
					    	FileWriter lu = new FileWriter(monFichier);		
					    	BufferedWriter out = new BufferedWriter(lu);
					    	out.write(configAsString()); //
					    	out.close();
					    } catch (IOException er) {;}
					 }
		    	}		    	
		    	
		    	if (e.getSource()==Panel.loadbtn){	
		    		String loaddedconfig="";		    		
		    		JFileChooser filechoose = new JFileChooser();
					filechoose.setCurrentDirectory(new File("./Sequences"));		
					String approve = new String("OUVRIR");	
					String monFichier= null; 
					int resultatOuvrir = filechoose.showDialog(filechoose,approve); 
					if(resultatOuvrir == filechoose.APPROVE_OPTION){  
						monFichier = filechoose.getSelectedFile().toString();
						try
						{ FileInputStream fis = new FileInputStream(monFichier);		
						   int n;
						   while ((n = fis.available())> 0) 
						   { byte[] b = new byte[n];
						      int result= fis.read(b); 
						      if (result== -1) break; 
						      String s = new String(b);		    		    	
						      loaddedconfig=loaddedconfig+s;    		    	
						   }
						} catch (Exception err) {;}
					}		    		 
		    		System.out.println("config chargée: "+loaddedconfig);
		    		Sequencer.setsequence(loaddedconfig.split(","));	
		    		Panel.displayconfig();		    		 
		    	}		    
		    	Panel.repaint();
		    }

	
	private String configAsString(){		
		String configtosave="";		
		for (StepSlider step : Panel.StpSliders) {
			configtosave=configtosave+(String)step.getnote()+",";			
		}			 		
		return configtosave;
	}
	


}
