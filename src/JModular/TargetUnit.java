package JModular;

public abstract class TargetUnit extends ModuleUnit{
	
	public double Notelength=0.1;
	
	public TargetUnit(int ID){
		super(ID);	
	}
	
	public int getID(){
		return ID;
	}

	public void Play(String note, String Octave) {		
	}
	
	public void setNotelength(double Notelength){
		this.Notelength=Notelength;
	}
	
	public void Stop(){   }
		
}
