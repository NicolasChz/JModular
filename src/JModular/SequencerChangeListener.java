package JModular;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SequencerChangeListener implements ChangeListener{
	
	private SequencerPanel Panel;
	private SequencerUnit Sequencer;
	Model model;
	
	public SequencerChangeListener(Model model , SequencerPanel Panel){
		this.model=model;
		this.Panel=Panel;
		this.Sequencer=model.SequencerUnits.get(model.SequencerUnits.size()-1);	
	}
	
	
	@Override
	public void stateChanged(ChangeEvent e) {
		// on surveille le changement d'état des 16 sliders du séquenceur
		// la fonction setsequence de la classe SequencerUnit modifie la note correspondant 
		// à la position du slider dans la séquence à jouer:		
		for (int stepposition=0;stepposition<=15;stepposition++){
	    	if (e.getSource()==Panel.StpSliders.get(stepposition)) {		 
    		String selectednote=(String) Panel.StpSliders.get(stepposition).getnote();		
    		Sequencer.setsequence(selectednote,stepposition);    
    		Panel.displayconfig();
	    	}
    	}
		
		
		
		
	}

}
