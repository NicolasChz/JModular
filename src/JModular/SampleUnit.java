package JModular;
import java.io.File;
import java.io.IOException;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.FloatSample;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.unitgen.VariableRateMonoReader;
import com.jsyn.unitgen.VariableRateStereoReader;
import com.jsyn.util.SampleLoader;

public class SampleUnit extends TargetUnit {

    public Synthesizer synth;
    public  VariableRateDataReader samplePlayer;
    private LineOut lineOut;
    private String FilePath="Samples/Kick03.wav";
    private File sampleFile;
    String Switchstate="ON";
    public double maxVolume=1;
    FloatSample sample;

    
	public SampleUnit(int ID) {
		super(ID);
		init();
	}
	
	public void init(){
		synth = JSyn.createSynthesizer();
		synth.add(lineOut = new LineOut());
		initsample();
		synth.start();  //
		lineOut.start(); //
		samplePlayer.rate.set(sample.getFrameRate());		
	}
	
	
	public void initsample(){
		sampleFile = new File(FilePath);		
		 try { 
				 SampleLoader.setJavaSoundPreferred(false);
		         sample = SampleLoader.loadFloatSample(sampleFile);        
		         if (sample.getChannelsPerFrame() == 1) {
		             synth.add(samplePlayer = new VariableRateMonoReader());
		             samplePlayer.output.connect(0, lineOut.input, 0);
		         } else if (sample.getChannelsPerFrame() == 2) {
		             synth.add(samplePlayer = new VariableRateStereoReader());
		             samplePlayer.output.connect(0, lineOut.input, 0);
		             samplePlayer.output.connect(1, lineOut.input, 1);
		         } else {
		             throw new RuntimeException("Can only play mono or stereo samples.");
		         }   
		 } catch (IOException e1) {
	            e1.printStackTrace();
	     }
	}
		 
	
	
	public void Play(String note, String octave) {	
			 Thread worker = new Thread() {
			        public void run() {
						samplePlayer.dataQueue.clear();
						samplePlayer.rate.set(setPitch(note,octave) );
						samplePlayer.dataQueue.queue(sample);				
			        }
			 };
			 worker.start();
	}	

	public void setFilePath(String FilePath){
		this.FilePath=FilePath;
		initsample();
		//restart:
		samplePlayer.dataQueue.queueOff(sample);		
		synth.stop();
		lineOut.stop();
		synth.start();  //
		lineOut.start(); //
		samplePlayer.rate.set(sample.getFrameRate());	
	}

	private double setPitch(String note, String octave){
		double pitch=0.0;
		double transposedNote=0.0;
		pitch=sample.getFrameRate();
		if (note.compareTo("G")==0) transposedNote=(98*pitch)/131;
		if (note.compareTo("G#")==0) transposedNote=(103*pitch)/131;
		if (note.compareTo("A")==0) transposedNote=(110*pitch)/131;
		if (note.compareTo("A#")==0)transposedNote=(116*pitch)/131;
		if (note.compareTo("B")==0) transposedNote=(123*pitch)/131;
		if (note.compareTo("C")==0) transposedNote=pitch;
		if (note.compareTo("C#")==0) transposedNote=(139*pitch)/131;
		if (note.compareTo("D")==0) transposedNote=(147*pitch)/131;
		if (note.compareTo("D#")==0) transposedNote=(156*pitch)/131;
		if (note.compareTo("E")==0) transposedNote=(165*pitch)/131;
		if (note.compareTo("F")==0) transposedNote=(175*pitch)/131;
		if (note.compareTo("F#")==0)transposedNote=(185*pitch)/131;		
		if (octave=="0")transposedNote=transposedNote/4;
		if (octave=="1")transposedNote=transposedNote/2;
		if (octave=="3")transposedNote=transposedNote*2;
		if (octave=="4")transposedNote=transposedNote*4;
		if (octave=="5")transposedNote=transposedNote*8;
		return transposedNote;		
	}
	
}
