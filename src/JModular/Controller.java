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

		view.addSequencerbtn.addActionListener(new addSequencerListener());	//ce bouton permet l'ajout d'un séquenceur
		view.addGeneratorbtn.addActionListener(new addGeneratorListener());	//ce bouton permet l'ajout d'un générateur de son
		view.addSamplebtn.addActionListener(new addSampleListener());	//ce bouton permet l'ajout d'un sampleur
		view.addPatternbtn.addActionListener(new addPatternListener());	//ce bouton permet l'ajout d'un éditeur de pattern
		view.mainPlaybtn.addActionListener(new mainPlayListener());	//ce bouton permet de lancer la lecture simultanée de tous les séquenceurs
		view.mainStopbtn.addActionListener(new mainStopListener());	//ce bouton permet de Stopper la lecture de tous les séquenceurs

		model.addTempoUnit(); // l'unité tempo est ajoutée d'office
		
		view.validate();		
    }
    
    // action pour le bouton d'ajout d'un nouvel d'editeur de patterns:
	class addPatternListener implements ActionListener{	   
	    public void actionPerformed(ActionEvent e) {	      
	    	model.addPatternUnit();	// appel de la méthode de création d'un PatternUnit dans la liste patternUnits du modèle
	    	view.addPatternPanel(); //ajout du panneau correpondant
	    	PatternPanel Panel = (PatternPanel)(view.ModulePanels.get(view.ModulePanels.size()-1));	    	
	    	PatternListener patternlistener=new PatternListener(model, view); // ajout d'écouteurs pour l'éditeur de patterns
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
   
	//Action pour le bouton d'ajout d'un nouveau séquenceur:
	class addSequencerListener implements ActionListener{	   
	    public void actionPerformed(ActionEvent e) {  
	    	// ajout Sequencer unit
	    	model.addSequencerUnit();	
	    	SequencerUnit Sequencer= model.SequencerUnits.get(model.SequencerUnits.size()-1);	// on le met en fin de pile dans la liste des modules 	
    	
	    	///ajout sequencer panel
	    	view.addSequencerPanel();  // sequenceur créé dans une liste de séquenceurs du modèle
	    	SequencerPanel Panel = (SequencerPanel)(view.ModulePanels.get(view.ModulePanels.size()-1)); // on récupère le séquenceur créé pour la suite
	    	
	    	/// ajout des listeners:
	    	SequencerListener seqlistener =new SequencerListener(model, Panel); // appel de la classe création d'écouteur (interragit avec le modele et le panneau précédemment créé)
	    	Panel.btn.addActionListener(seqlistener);  //ajout d'action aux composants du paneau
	    	Panel.Targetlist.addActionListener(seqlistener);

			SequencerChangeListener seqchangeListener = new SequencerChangeListener(model, Panel);	// création d'écouteurs de changement d'état des sliders
	    	for (StepSlider step : Panel.StpSliders) {	
	    		step.addChangeListener(seqchangeListener); 	// ajout de l'action sur les sliders
	    	}	    	
	    	Panel.Octave.addActionListener(seqlistener);	    	//ajout d'action aux composants du paneau
	    	Panel.savebtn.addActionListener(seqlistener);
	    	Panel.loadbtn.addActionListener(seqlistener);
	    	Panel.resetbtn.addActionListener(seqlistener);	    
	    	
	    	for(ModulePanel ModPanel : view.ModulePanels){				// la création d'un nouveau séquenceur rafraichit la liste des ID de séquanceurs dispos
				if (ModPanel.Moduletype.compareTo("Pattern")==0){ 		// sur le panneau des éditeurs de Pattern
					PatternPanel PttrnPanel=(PatternPanel)ModPanel;
					PttrnPanel.refreshTargetlist();
					}
			}
	    	view.validate();	    	
	    }
	}

	
	// écouteur et action définis pour le bouton Play all:
	class mainPlayListener implements ActionListener{	   
	    public void actionPerformed(ActionEvent e) {	      
	    	// si ce bouton est préssé, tous les séquenceurs à l'arrêt passent en lecture:
			for(SequencerUnit sequencer : model.SequencerUnits){	// passage en revue de tous les séquenceurs de la liste des séquenceurs
				if (sequencer.playing==false){	// si le séquenceur n'est pas en cours de lecture...			
					sequencer.Play();	// on active la lecture...
					view.sequencerPanel.get(sequencer.ID).btn.setText("Stop");	// ... et on met à jour le status du bouton Play/Stop
				}			
			}
	    	view.validate();
	    }
	}
	
	// écouteur et action pour le bouton Stop all (action inverse de la méthode ci-dessus
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
	
	//  action pour bouton d'ajout d'un nouveau générateur:
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
			// à chaque nouveau générateur ajouté, la liste des cibles disponibles pour tous les séquenceurs est mise à jour : 
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
	    	// à chaque nouveau sampleur ajouté, la liste des cibles disponibles pour tous les séquenceurs est mise à jour : 
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