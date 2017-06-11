package JModular;

// unit� de g�n�ration (synth�se) audio
// re�oit des notes d'un s�quenceur (en notation internationnale A..G) ainsi que le valeur de l'octave
// traduit ces information en pr�quences et ajoute une enveloppe pour d�finir l'�volution du volume sonore de la note dans le temps


import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.SegmentedEnvelope;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.SineOscillatorPhaseModulated;
import com.jsyn.unitgen.SquareOscillatorBL;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.UnitVoice;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.unitgen.VariableRateMonoReader;

public class GeneratorUnit extends TargetUnit {

    public Synthesizer synth;
    private UnitOscillator osc1 = new SawtoothOscillatorBL();    
    private UnitOscillator osc2 = new SineOscillatorPhaseModulated();
    private UnitOscillator osc3 = new SquareOscillatorBL();
    public UnitOscillator osc;
    private LineOut lineOut;
    public SegmentedEnvelope envelope;
    public VariableRateDataReader envelopePlayer;
    public String type="Sawtooth"; 
    public UnitVoice voice;
    public String ADSRpref="1";
    public double maxVolume=1;
    private Beat beat;
    private double freq=0.0;
     
	public GeneratorUnit(int ID) {
		super(ID);	
		init();
	}
	
	
	public void init(){
		synth = JSyn.createSynthesizer();
		osc=osc1;	
	    voice = (UnitVoice) osc;
	    synth.add(envelopePlayer = new VariableRateMonoReader());		// cr�ation d'une enveloppe et ajout de celle-ci au synth�	       		
	    envelope = new SegmentedEnvelope(setpairs());					// l'enveloppe est d�finie par les coordonn�es de 3 points       		
	    synth.add(osc);	
	    synth.add(lineOut = new LineOut());
	    envelopePlayer.output.connect(osc.amplitude);			// Connect the oscillator to the output.	       		
	    osc.output.connect(0, lineOut.input, 0);
	    osc.output.connect(0, lineOut.input, 1);		       
	    voice.getOutput().connect(0, lineOut.input, 0);
	    voice.getOutput().connect(0, lineOut.input, 1); 	       
	    synth.start();    
	    lineOut.start(); 
	}
	
	// r�ception d'une note du s�quenceur, et g�n�ration du son correspondant:
	public void Play(String note, String octave){	
		try {
			freq=converttofrequence(note,octave); // traduction de la note en fr�quence audio
			osc.frequency.set(freq);
	        envelopePlayer.dataQueue.queue(envelope);
	        synth.sleepFor(Notelength);	//      
		} catch (InterruptedException e) {
	        e.printStackTrace();
	    }	
	}
	
	
	// connecte l'un des trois oscillateurs en fonction du type d'onde choisit (avec arr�t/ red�marrage du synth� pour �viter les bruits parasites):
	public void setwavetype(String wavetype){ 
		if (synth.isRunning()){ synth.stop(); synth.start(); }
		voice.getOutput().disconnectAll();
		synth.remove(osc); 
		if (wavetype.compareTo("Sawtooth")==0)osc=osc1;	  	
		if (wavetype.compareTo("Sine")==0)osc=osc2;
		if (wavetype.compareTo("Square")==0)osc=osc3;
		envelope = new SegmentedEnvelope(setpairs());
		this.type=wavetype;
		synth.add(osc);	   
		envelopePlayer.output.connect(osc.amplitude); // reconnection de l'enveloppe n�cessaire lors du changement d'oscillateur
		voice = (UnitVoice) osc;  
		voice.getOutput().connect(0, lineOut.input, 0);
        voice.getOutput().connect(0, lineOut.input, 1);
        lineOut.start();
	}
	
	public void Start(){
		synth.start();    
		lineOut.start();
	}
	
	public void Stop(){   
		envelopePlayer.dataQueue.clear();
		synth.stop();
		lineOut.stop();
	} 
	
	public String gettype(){
		return type;   	
	}  
	
	// d�finition des pr�f�rences pour l'enveloppe:
	public void setADSRpreference(String ADSRpref){
		this.ADSRpref=ADSRpref;
	}
	
	// pour chacune des 3 pr�f�rences d'enveloppe propos�es, on calcule les 3 points de passage de la courbe d'enveloppe:
	public double[] setpairs(){
		double[] pairs = { 0, 0, 0, 0, 0, 0 };	// init: tout � 0
		// pr�f�rence d'enveloppe num�ro 1: attaque rapide, descente normale:
		if (ADSRpref.compareTo("1")==0){
			pairs[0]=0;
			pairs[1]=maxVolume;		// valeur du volume correspondant au point d'abscisse 0 (attaque imm�diate)	
			pairs[2]=Notelength/2;
			pairs[3]=maxVolume/2;	// � la moit�e de l'abscisse, le volume est divis� par 2
			pairs[4]=Notelength;	
			pairs[5]=0;		// � la fin, on est � 0
		}		
		// pr�f�rence d'enveloppe num�ro 2: attaque longue, descente plus courte:
		if (ADSRpref.compareTo("2")==0){
			pairs[0]=0;
			pairs[1]=0;		// ici, on commence avec un volume � 0 pour le premier point
			pairs[2]=Notelength-(Notelength/2);
			pairs[3]=maxVolume;			// on est au max du volume au tiers du temps de la note
			pairs[4]=Notelength-(Notelength/2);
			pairs[5]=0;		// apr�s le dernier tiers, on est � 0
		}		
		// pr�f�rence d'enveloppe num�ro 3: attaque tr�s longue, descente brusque:
		if (ADSRpref.compareTo("3")==0){
			pairs[0]=0;
			pairs[1]=0;		// on commence � 0
			pairs[2]=Notelength-(Notelength/4);
			pairs[3]=maxVolume;		// au 3/4 de la dur�e totale, on est au volume max
			pairs[4]=Notelength; 
			pairs[5]=0;	// on passe � 0 � la fin
		}		
		return pairs;
	}
	

	// les notes envoy�es par le s�quenceur sont traduites en fr�quences:
	public double converttofrequence(String note, String octave){
	double F=0;
	if (note.compareTo("G")==0)F=98;
	if (note.compareTo("G#")==0)F=103;
	if (note.compareTo("A")==0)F=110;
	if (note.compareTo("A#")==0)F=116;
	if (note.compareTo("B")==0)F=123;
	if (note.compareTo("C")==0)F=131;
	if (note.compareTo("C#")==0)F=139;
	if (note.compareTo("D")==0)F=147;
	if (note.compareTo("D#")==0)F=156;
	if (note.compareTo("E")==0)F=165;
	if (note.compareTo("F")==0)F=175;
	if (note.compareTo("F#")==0)F=185;	
	if (octave=="0")F=F/4;
	if (octave=="1")F=F/2;
	if (octave=="3")F=F*2;
	if (octave=="4")F=F*4;
	if (octave=="5")F=F*8;
	return F;		
}


}
