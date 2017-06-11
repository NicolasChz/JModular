package JModular;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatternListener implements ActionListener {
	private SequencerPanel linkedSequencerPanel;
	private PatternUnit patternUnit;
	private PatternPanel patternPanel;
	int SequencerUnitID;
	Model model;
	View view;
	
	public PatternListener(Model model , View view){  // on a besoin du mod�le et de la vue, afin d'agir sur le s�quenceur et sur son panneau
		this.model=model;
		this.view=view;
		this.patternUnit=model.PatternUnits.get(model.PatternUnits.size()-1);
		this.patternPanel = (PatternPanel)(view.ModulePanels.get(view.ModulePanels.size()-1));	
	}
	
	public void actionPerformed(ActionEvent e) {    	
    	
		//actions pour les bouton send et store des pads 1 � 3:
		for (int i=0; i<=2;i++){
			if (e.getSource()==patternPanel.pads.get(i).send){	
				if (patternUnit.getLinkedSequencer()!=null){ // aucune action si on n'a pas choisit un s�quenceur li�
					patternUnit.sendPattern(i);
					linkedSequencerPanel.displayconfig();
				}
	    	}
			
			if (e.getSource()==patternPanel.pads.get(i).store){	
				if (patternUnit.getLinkedSequencer()!=null){ // aucune action si on n'a pas choisit un s�quenceur li�
					patternUnit.storePattern(i);    		
		    		patternPanel.pads.get(i).State.setText(""+patternUnit.getPattern(i)); // affiche un apper�u du pattern charg� au dessus du PAD
				}
	    	}
		}
		

		if (e.getSource()==patternPanel.Targetlist){	
			int selectedID=patternPanel.Targetlist.getSelectedIndex();
			if (selectedID <=0) selectedID=0;    		
    		patternUnit.setlinkedSequencer(model.SequencerUnits.get(selectedID));
    		this.SequencerUnitID=patternUnit.linkedSequencer.ID; // on cherche l'id du s�quenceur li� (index dans le file des s�quenceurs) 
    		this.linkedSequencerPanel=view.sequencerPanel.get(SequencerUnitID);	// on d�finit le s�quenceur li�
		}

	}
	
	
}
