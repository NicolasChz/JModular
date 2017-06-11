package JModular;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class  Controller { 
	
    Model model;
    View view;
    String type;
    
   
    Controller(Model model, View view) {
		this.model = model;
		this.view = view;		

		view.addSequencerbtn.addActionListener(new addSequencerListener());	//ce bouton permet l'ajout d'un s�quenceur
		view.addGeneratorbtn.addActionListener(new addGeneratorListener());	//ce bouton permet l'ajout d'un g�n�rateur de son
		view.addSamplebtn.addActionListener(new addSampleListener());	//ce bouton permet l'ajout d'un sampleur
		view.addPatternbtn.addActionListener(new addPatternListener());	//ce bouton permet l'ajout d'un �diteur de pattern
		view.mainPlaybtn.addActionListener(new mainPlayListener());	//ce bouton permet de lancer la lecture simultan�e de tous les s�quenceurs
		view.mainStopbtn.addActionListener(new mainStopListener());	//ce bouton permet de Stopper la lecture de tous les s�quenceurs

		model.addTempoUnit(); // l'unit� tempo est ajout�e d'office
		
		view.validate();		
    }
    
    // action pour le bouton d'ajout d'un nouvel d'editeur de patterns:
	class addPatternListener implements ActionListener{	   
	    public void actionPerformed(ActionEvent e) {	      
	    	model.addPatternUnit();	// appel de la m�thode de cr�ation d'un PatternUnit dans la liste patternUnits du mod�le
	    	view.addPatternPanel(); //ajout du panneau correpondant
	    	PatternPanel Panel = (PatternPanel)(view.ModulePanels.get(view.ModulePanels.size()-1));	    	
	    	PatternListener patternlistener=new PatternListener(model, view); // ajout d'�couteurs pour l'�diteur de patterns
	    	//ajout des actions aux boutons:
	    	Panel.pads.get(0).send.addActionListener(patternlistener); 
	    	Panel.pads.get(1).send.addActionListener(patternlistener);
	    	Panel.pads.get(2).send.addActionListener(patternlistener);   	
	    	Panel.pads.get(0).store.addActionListener(patternlistener);
	    	Panel.pads.get(1).store.addActionListener(patternlistener);
	    	Panel.pads.get(2).store.addActionListener(patternlistener);
	    	Panel.Targetlist.addActionListener(patternlistener);

	    	view.validate();
	    }
	}
   
	//Action pour le bouton d'ajout d'un nouveau s�quenceur:
	class addSequencerListener implements ActionListener{	   
	    public void actionPerformed(ActionEvent e) {  
	    	// ajout Sequencer unit
	    	model.addSequencerUnit();	
	    	SequencerUnit Sequencer= model.SequencerUnits.get(model.SequencerUnits.size()-1);	// on le met en fin de pile dans la liste des modules 	
    	
	    	///ajout sequencer panel
	    	view.addSequencerPanel();  // sequenceur cr�� dans une liste de s�quenceurs du mod�le
	    	SequencerPanel Panel = (SequencerPanel)(view.ModulePanels.get(view.ModulePanels.size()-1)); // on r�cup�re le s�quenceur cr�� pour la suite
	    	
	    	/// ajout des listeners:
	    	SequencerListener seqlistener =new SequencerListener(model, Panel); // appel de la classe cr�ation d'�couteur (interragit avec le modele et le panneau pr�c�demment cr��)
	    	Panel.btn.addActionListener(seqlistener);  //ajout d'action aux composants du paneau
	    	Panel.Targetlist.addActionListener(seqlistener);

			SequencerChangeListener seqchangeListener = new SequencerChangeListener(model, Panel);	// cr�ation d'�couteurs de changement d'�tat des sliders
	    	for (StepSlider step : Panel.StpSliders) {	
	    		step.addChangeListener(seqchangeListener); 	// ajout de l'action sur les sliders
	    	}	    	
	    	Panel.Octave.addActionListener(seqlistener);	    	//ajout d'action aux composants du paneau
	    	Panel.savebtn.addActionListener(seqlistener);
	    	Panel.loadbtn.addActionListener(seqlistener);
	    	Panel.resetbtn.addActionListener(seqlistener);	    
	    	
	    	for(ModulePanel ModPanel : view.ModulePanels){				// la cr�ation d'un nouveau s�quenceur rafraichit la liste des ID de s�quanceurs dispos
				if (ModPanel.Moduletype.compareTo("Pattern")==0){ 		// sur le panneau des �diteurs de Pattern
					PatternPanel PttrnPanel=(PatternPanel)ModPanel;
					PttrnPanel.refreshTargetlist();
					}
			}
	    	view.validate();	    	
	    }
	}

	
	// �couteur et action d�finis pour le bouton Play all:
	class mainPlayListener implements ActionListener{	   
	    public void actionPerformed(ActionEvent e) {	      
	    	// si ce bouton est pr�ss�, tous les s�quenceurs � l'arr�t passent en lecture:
			for(SequencerUnit sequencer : model.SequencerUnits){	// passage en revue de tous les s�quenceurs de la liste des s�quenceurs
				if (sequencer.playing==false){	// si le s�quenceur n'est pas en cours de lecture...			
					sequencer.Play();	// on active la lecture...
					view.sequencerPanel.get(sequencer.ID).btn.setText("Stop");	// ... et on met � jour le status du bouton Play/Stop
				}			
			}
	    	view.validate();
	    }
	}
	
	// �couteur et action pour le bouton Stop all (action inverse de la m�thode ci-dessus
	class mainStopListener implements ActionListener{	   
	    public void actionPerformed(ActionEvent e) {	      

			for(SequencerUnit sequencer : model.SequencerUnits){
				if (sequencer.playing==true){				
					sequencer.Stop();
					view.sequencerPanel.get(sequencer.ID).btn.setText("Play");
				}			
			}
	    	view.validate();
	    }
	}
	
	//  action pour bouton d'ajout d'un nouveau g�n�rateur:
	class addGeneratorListener implements ActionListener{	   
	    public void actionPerformed(ActionEvent e) {	      
	    	model.addGeneratorUnit();	
	    	GeneratorUnit Generator= model.GeneratorUnits.get(model.GeneratorUnits.size()-1);	    	
	    	view.addGeneratorPanel();
	    	GeneratorPanel Panel = (GeneratorPanel)(view.ModulePanels.get(view.ModulePanels.size()-1));	    	
	    	GeneratorListener Generatorlistener=new GeneratorListener(Generator, Panel);
	    	Panel.Button1.addActionListener(Generatorlistener);
			Panel.Button2.addActionListener(Generatorlistener);
			Panel.Button3.addActionListener(Generatorlistener);
			Panel.Env1.addActionListener(Generatorlistener);
			Panel.Env2.addActionListener(Generatorlistener);
			Panel.Env3.addActionListener(Generatorlistener);
			// � chaque nouveau g�n�rateur ajout�, la liste des cibles disponibles pour tous les s�quenceurs est mise � jour : 
			for(ModulePanel ModPanel : view.ModulePanels){				
				if (ModPanel.Moduletype.compareTo("Sequencer")==0){ 
					SequencerPanel SeqPanel=(SequencerPanel)ModPanel;
					SeqPanel.refreshTargetlist();
					}
			}
	    	view.validate();
	    }
	}

	// action pour le bouton d'ajout d'un nouveau samleur:
	class addSampleListener implements ActionListener{	   
	    public void actionPerformed(ActionEvent e) {	      
	    	model.addSampleUnit();	
	    	SampleUnit Sample= model.SampleUnits.get(model.SampleUnits.size()-1);	    	
	    	view.addSamplePanel();
	    	SamplePanel Panel = (SamplePanel)(view.ModulePanels.get(view.ModulePanels.size()-1));	    	
	    	SampleListener samplelistener=new SampleListener(Sample, Panel);
	    	Panel.browsebtn.addActionListener(samplelistener);
	    	// � chaque nouveau sampleur ajout�, la liste des cibles disponibles pour tous les s�quenceurs est mise � jour : 
			for(ModulePanel ModPanel : view.ModulePanels){				
				if (ModPanel.Moduletype.compareTo("Sequencer")==0){ 
					SequencerPanel SeqPanel=(SequencerPanel)ModPanel;
					SeqPanel.refreshTargetlist();
					}
			}
	    	view.validate();
	    }
	}


	


}