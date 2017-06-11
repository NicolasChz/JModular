package JModular;


public class TempoUnit extends ModuleUnit{

	private Thread timer;
	private Beat beat;
	private int tempo=100; // en ms

	
	public TempoUnit(BeatSingleton singlebeat) {
		super(100);
		this.beat=singlebeat.getInstance();
		beatcounter();
	}

	public void beatcounter(){
		timer = new Thread() {			
			public void run() {
		        for(;;) {
		      		try {Thread.sleep(tempo); //sleep 100 milliseconds		      				
		      		} catch(InterruptedException ie) {}
		      		beat.increment();			      	
		        }	  
            }// end run            
        };//end thread 
        timer.start();        
	}//end beatcounter	
	
	public int gettempo(){
		return tempo;
	}
	
	public void settempo(int tempo){
		this.tempo=tempo;
	}
	
	
	
}//end
