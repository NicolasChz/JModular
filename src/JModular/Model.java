package JModular;

import java.util.ArrayList;


public class Model {
	  
	BeatSingleton singlebeat=new BeatSingleton(); //on crée l'instance du singleton Beat
	
	// on crée les listes où les modules seront empilés par type de module
	ArrayList<SequencerUnit> SequencerUnits=new ArrayList<SequencerUnit>();
	ArrayList<GeneratorUnit> GeneratorUnits=new ArrayList<GeneratorUnit>();
	ArrayList<SampleUnit> SampleUnits=new ArrayList<SampleUnit>();	
	ArrayList<TargetUnit> TargetUnits=new ArrayList<TargetUnit>();	// la liste TargetUnits est utilisée par les séquenceurs. Elle contient tous les générateurs et les sampleurs créés
	ArrayList<PatternUnit> PatternUnits=new ArrayList<PatternUnit>();	
	
	// ce module est particulier: il est unique et ajouté automatiquement au démarrage:
	TempoUnit bpmUnit;
	
	
	public Model(){
		super();
	}
 
	// méthode d'ajout d'un nouveau séquenceur:
	public void addSequencerUnit(){
		SequencerUnit SeqUnit;
		int ID=SequencerUnits.size(); 	//l'ID du Séquenceur à créer correspond à la taille de la liste des séquenceurs
		SequencerUnits.add(SeqUnit=new SequencerUnit(ID)); 	// création du séquenceur et ajout de celui-ci à la fin de la liste
		SeqUnit.setTempo(bpmUnit, singlebeat); 	// réglage du tempo du séquenceur: on lui transmet l'instance du singleton Beat
	}
	
	public void addPatternUnit(){
		PatternUnit patternUnit;
		int ID=PatternUnits.size(); 	//l'ID de l'éditeur de Pattern à créer correspond à la taille de la liste PatternUnit
		PatternUnits.add( patternUnit=new PatternUnit(ID)); // création et ajout à la fin de  la liste	
	}
	
	public void addTempoUnit(){
		this.bpmUnit=new TempoUnit(singlebeat); // création de l'unité de gestion / répartition du tempo
	}

    public void addGeneratorUnit(){			
    	int ID=TargetUnits.size(); 		//l'ID du Generateur à créer correspond à la taille de la liste GeneratorUnits
    	GeneratorUnit GenUnit=new GeneratorUnit(ID); // création du générateur 
    	GeneratorUnits.add(GenUnit);		// empilement dans la liste des générateurs
    	TargetUnits.add(GenUnit);  //les séquenceurs iront chercher dans cette liste les unités auxquelles se connecter
	}
    
    public void addSampleUnit(){			
    	int ID=TargetUnits.size(); //l'ID du SampleUnit à créer correspond à l'indice du dernier élément de la liste
    	SampleUnit sampleUnit=new SampleUnit(ID);  //création du sampleur 
    	SampleUnits.add(sampleUnit);	// empilement dans la liste des sampleurs
    	TargetUnits.add(sampleUnit); //les séquenceurs iront chercher dans cette liste les unités auxquelles se connecter
	}
	
}