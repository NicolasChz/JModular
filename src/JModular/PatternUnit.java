// PatterUnit: Ce module permet de mémoriser rapidement les séquences de notes d'un séquenceur 
// pour les rappeler ensuite. Chaque unité d'édition de pattern comprend 3 banques et peut être
// lié à un seul séquenceur à la fois. Le séquenceur lis peut être changé à tout moment...
// Pour coller au vocabulaire habituellement employé dans les logiciels de ce type, on emploiera
// le terme de séquence pour parler des notes définies dans le séquenceur... Une fois mémorisées dans 
// le module patternInit, on parlera alors de pattern 

package JModular;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;


public class PatternUnit  extends ModuleUnit{

	String SequenceInit[]={"-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-",}; // notes muettes
	SequencerUnit linkedSequencer=null;  // séquenceur lié ( on importe/exporte les séquences depuis/vers celui-ci)
	int maxPadsnumber=3;	// on peut en faire autant qu'on veut...
	ArrayList<String[]> Patterns=new ArrayList<String[]>();	 // list de stockage des séquences mémorisées 

	 
	public PatternUnit(int ID) {       
    	super(ID);
    	init();
    } 
	
	public void init(){
	 	for (int i=0; i<maxPadsnumber;i++){		//init de la séquence contenue dans chaque PAD avec des notes muettes
	 		Patterns.add(SequenceInit);
	   }	   	
	}
	
	public void setlinkedSequencer(SequencerUnit Sequencer){
		this.linkedSequencer=Sequencer;	   // attribue un séquenceur à l'editeur de pattern
		System.out.println("Linked to Sequencer:"+Sequencer.ID );
	}
	
	public SequencerUnit getLinkedSequencer(){
		return linkedSequencer;
	}
	
	//méthode d'envoi d'une séquence:
	public void sendPattern(int index){
		String patterntoSend[]=Patterns.get(index).clone();		
		linkedSequencer.setsequence(patterntoSend);
	}
	
	//méthode de mémorisation d'une séquence:
	public void storePattern(int index){
		String  patterntoStore[]=linkedSequencer.getSequence().clone();
		Patterns.set(index, patterntoStore);
	}
	
	//getter pour le pattern:
	public String getPattern(int index){
		String pattern=Arrays.toString(Patterns.get(index));		
		return pattern;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(395, 170);
	}
	
	public int getmaxPadsnumber(){
		return maxPadsnumber;
	}
	
}
