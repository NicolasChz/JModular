package JModular;

import java.util.ArrayList;


public class Model {
	  
	BeatSingleton singlebeat=new BeatSingleton(); //on cr�e l'instance du singleton Beat
	
	// on cr�e les listes o� les modules seront empil�s par type de module
	ArrayList<SequencerUnit> SequencerUnits=new ArrayList<SequencerUnit>();
	ArrayList<GeneratorUnit> GeneratorUnits=new ArrayList<GeneratorUnit>();
	ArrayList<SampleUnit> SampleUnits=new ArrayList<SampleUnit>();	
	ArrayList<TargetUnit> TargetUnits=new ArrayList<TargetUnit>();	// la liste TargetUnits est utilis�e par les s�quenceurs. Elle contient tous les g�n�rateurs et les sampleurs cr��s
	ArrayList<PatternUnit> PatternUnits=new ArrayList<PatternUnit>();	
	
	// ce module est particulier: il est unique et ajout� automatiquement au d�marrage:
	TempoUnit bpmUnit;
	
	
	public Model(){
		super();
	}
 
	// m�thode d'ajout d'un nouveau s�quenceur:
	public void addSequencerUnit(){
		SequencerUnit SeqUnit;
		int ID=SequencerUnits.size(); 	//l'ID du S�quenceur � cr�er correspond � la taille de la liste des s�quenceurs
		SequencerUnits.add(SeqUnit=new SequencerUnit(ID)); 	// cr�ation du s�quenceur et ajout de celui-ci � la fin de la liste
		SeqUnit.setTempo(bpmUnit, singlebeat); 	// r�glage du tempo du s�quenceur: on lui transmet l'instance du singleton Beat
	}
	
	public void addPatternUnit(){
		PatternUnit patternUnit;
		int ID=PatternUnits.size(); 	//l'ID de l'�diteur de Pattern � cr�er correspond � la taille de la liste PatternUnit
		PatternUnits.add( patternUnit=new PatternUnit(ID)); // cr�ation et ajout � la fin de  la liste	
	}
	
	public void addTempoUnit(){
		this.bpmUnit=new TempoUnit(singlebeat); // cr�ation de l'unit� de gestion / r�partition du tempo
	}

    public void addGeneratorUnit(){			
    	int ID=TargetUnits.size(); 		//l'ID du Generateur � cr�er correspond � la taille de la liste GeneratorUnits
    	GeneratorUnit GenUnit=new GeneratorUnit(ID); // cr�ation du g�n�rateur 
    	GeneratorUnits.add(GenUnit);		// empilement dans la liste des g�n�rateurs
    	TargetUnits.add(GenUnit);  //les s�quenceurs iront chercher dans cette liste les unit�s auxquelles se connecter
	}
    
    public void addSampleUnit(){			
    	int ID=TargetUnits.size(); //l'ID du SampleUnit � cr�er correspond � l'indice du dernier �l�ment de la liste
    	SampleUnit sampleUnit=new SampleUnit(ID);  //cr�ation du sampleur 
    	SampleUnits.add(sampleUnit);	// empilement dans la liste des sampleurs
    	TargetUnits.add(sampleUnit); //les s�quenceurs iront chercher dans cette liste les unit�s auxquelles se connecter
	}
	
}