// PatterUnit: Ce module permet de m�moriser rapidement les s�quences de notes d'un s�quenceur 
// pour les rappeler ensuite. Chaque unit� d'�dition de pattern comprend 3 banques et peut �tre
// li� � un seul s�quenceur � la fois. Le s�quenceur lis peut �tre chang� � tout moment...
// Pour coller au vocabulaire habituellement employ� dans les logiciels de ce type, on emploiera
// le terme de s�quence pour parler des notes d�finies dans le s�quenceur... Une fois m�moris�es dans 
// le module patternInit, on parlera alors de pattern 

package JModular;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;


public class PatternUnit  extends ModuleUnit{

	String SequenceInit[]={"-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-",}; // notes muettes
	SequencerUnit linkedSequencer=null;  // s�quenceur li� ( on importe/exporte les s�quences depuis/vers celui-ci)
	int maxPadsnumber=3;	// on peut en faire autant qu'on veut...
	ArrayList<String[]> Patterns=new ArrayList<String[]>();	 // list de stockage des s�quences m�moris�es 

	 
	public PatternUnit(int ID) {       
    	super(ID);
    	init();
    } 
	
	public void init(){
	 	for (int i=0; i<maxPadsnumber;i++){		//init de la s�quence contenue dans chaque PAD avec des notes muettes
	 		Patterns.add(SequenceInit);
	   }	   	
	}
	
	public void setlinkedSequencer(SequencerUnit Sequencer){
		this.linkedSequencer=Sequencer;	   // attribue un s�quenceur � l'editeur de pattern
		System.out.println("Linked to Sequencer:"+Sequencer.ID );
	}
	
	public SequencerUnit getLinkedSequencer(){
		return linkedSequencer;
	}
	
	//m�thode d'envoi d'une s�quence:
	public void sendPattern(int index){
		String patterntoSend[]=Patterns.get(index).clone();		
		linkedSequencer.setsequence(patterntoSend);
	}
	
	//m�thode de m�morisation d'une s�quence:
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
