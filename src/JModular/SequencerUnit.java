package JModular;

import java.util.Arrays;

public class SequencerUnit extends ModuleUnit {

	GeneratorUnit TargetGenerator=null;
	SampleUnit TargetSample=null;
	boolean playing=false;
	Thread worker = new Thread();
	String initsequence[]={"-","-","-","-","-","-","-","-","-","-","-","-","-","-","-","-",};
	String notesequence[]=initsequence;
	private double freq;
	private double[] freqsequence;
	boolean TOP=false;
	boolean LetsPlay=false;
	double volume=0.6; 			
	double Notelength=0.02; 	
	private TempoUnit bpmUnit;
	private Beat beat;
	int last_position =-1;
	String octave="1";
	TargetUnit Target=null;

   public SequencerUnit(int ID) {       
    	super(ID);
    }    

   public void Play(){
       playing=true;
       worker = new Thread() {
        public void run() { 
        	 while(!beat.getBEGINNING() ) 														// tant que ce n'est pas encore le moment de jouer la note...
        		 try {sleep(1);} catch (InterruptedException e) {e.printStackTrace();} 			// ...on attends...
    		 
        	  int i=0;
        	   while (playing==true ){         		   
	        		 while(!PlayTime()) 															// tant que ce n'est pas encore le moment de jouer la note...
						try {sleep(1);} catch (InterruptedException e) {e.printStackTrace();}		 // ...on attends...   
	
	        		 if(notesequence[i].compareTo("-")!=0) 			//si la note n'est pas muette
		        		Target.Play(notesequence[i],octave); 										// ...puis on joue
	        		 if (i==notesequence.length-1)
		        		i=0; 																		// à la fin de la série de notes, on revient au début
	        		 else i++;	        		 
        	   } //end while (playing==true )           	  
           }
         };
        worker.start(); 
   } 
   
    public void Stop(){
    	playing=false;
    	worker.interrupt();
    	worker=null;
    }

	public void setTarget(TargetUnit Target){
		this.Target=Target;
		System.out.println("Linked to Generator:"+Target.ID );
		Target.setNotelength(Notelength);
	}

	public void setTempo(TempoUnit bpmUnit, BeatSingleton singlebeat){		
		this.bpmUnit=bpmUnit;
		this.beat= singlebeat.getInstance();
		this.Notelength=((double)bpmUnit.gettempo())/1000;
	}
	
	private boolean PlayTime(){		
		int position=beat.getposition();
		boolean TOP= beat.getTOP();
		if ( TOP && !(position==last_position)){  LetsPlay=true; last_position=position;}
		else LetsPlay=false;	
		return LetsPlay;
	}
	
	public void setsequence(String selectednote, int stepposition){	
		notesequence[stepposition]=selectednote;
	}
	
	public void setsequence(String Sequence[]){	
		notesequence=Sequence;
		System.out.println("sequence updated: "+Arrays.toString(notesequence));
	}
	
	public String[] getSequence(){
		return notesequence;
	}

	public void setOctave(String octave){
		this.octave=octave;
	}
	
	public void init(){
		notesequence=initsequence;
	}

}
