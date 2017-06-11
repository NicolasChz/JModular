package JModular;

// unité de génération (synthèse) audio
// reçoit des notes d'un séquenceur (en notation internationnale A..G) ainsi que le valeur de l'octave
// traduit ces information en préquences et ajoute une enveloppe pour définir l'évolution du volume sonore de la note dans le temps


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
	    synth.add(envelopePlayer = new VariableRateMonoReader());		// création d'une enveloppe et ajout de celle-ci au synthé	       		
	    envelope = new SegmentedEnvelope(setpairs());					// l'enveloppe est définie par les coordonnées de 3 points       		
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
	
	// réception d'une note du séquenceur, et génération du son correspondant:
	public void Play(String note, String octave){	
		try {
			freq=converttofrequence(note,octave); // traduction de la note en fréquence audio
			osc.frequency.set(freq);
	        envelopePlayer.dataQueue.queue(envelope);
	        synth.sleepFor(Notelength);	//      
		} catch (InterruptedException e) {
	        e.printStackTrace();
	    }	
	}
	
	
	// connecte l'un des trois oscillateurs en fonction du type d'onde choisit (avec arrêt/ redémarrage du synthé pour éviter les bruits parasites):
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
		envelopePlayer.output.connect(osc.amplitude); // reconnection de l'enveloppe nécessaire lors du changement d'oscillateur
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
	
	// définition des préférences pour l'enveloppe:
	public void setADSRpreference(String ADSRpref){
		this.ADSRpref=ADSRpref;
	}
	
	// pour chacune des 3 préférences d'enveloppe proposées, on calcule les 3 points de passage de la courbe d'enveloppe:
	public double[] setpairs(){
		double[] pairs = { 0, 0, 0, 0, 0, 0 };	// init: tout à 0
		// préférence d'enveloppe numéro 1: attaque rapide, descente normale:
		if (ADSRpref.compareTo("1")==0){
			pairs[0]=0;
			pairs[1]=maxVolume;		// valeur du volume correspondant au point d'abscisse 0 (attaque immédiate)	
			pairs[2]=Notelength/2;
			pairs[3]=maxVolume/2;	// à la moitée de l'abscisse, le volume est divisé par 2
			pairs[4]=Notelength;	
			pairs[5]=0;		// à la fin, on est à 0
		}		
		// préférence d'enveloppe numéro 2: attaque longue, descente plus courte:
		if (ADSRpref.compareTo("2")==0){
			pairs[0]=0;
			pairs[1]=0;		// ici, on commence avec un volume à 0 pour le premier point
			pairs[2]=Notelength-(Notelength/2);
			pairs[3]=maxVolume;			// on est au max du volume au tiers du temps de la note
			pairs[4]=Notelength-(Notelength/2);
			pairs[5]=0;		// après le dernier tiers, on est à 0
		}		
		// préférence d'enveloppe numéro 3: attaque trés longue, descente brusque:
		if (ADSRpref.compareTo("3")==0){
			pairs[0]=0;
			pairs[1]=0;		// on commence à 0
			pairs[2]=Notelength-(Notelength/4);
			pairs[3]=maxVolume;		// au 3/4 de la durée totale, on est au volume max
			pairs[4]=Notelength; 
			pairs[5]=0;	// on passe à 0 à la fin
		}		
		return pairs;
	}
	

	// les notes envoyées par le séquenceur sont traduites en fréquences:
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
