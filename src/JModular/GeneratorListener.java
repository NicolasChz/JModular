package JModular;

// actions d�finies pour les composants du panneau G�n�rateur


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.jsyn.data.SegmentedEnvelope;

public class GeneratorListener implements ActionListener {	 

	private GeneratorUnit Generator;
	private GeneratorPanel Panel;
	
	public GeneratorListener(GeneratorUnit Generator, GeneratorPanel Panel){
		this.Generator=Generator;	
		this.Panel=Panel;
	}	
	
	public void actionPerformed(ActionEvent e) {    	

			// changement de la fome d'onde (sinusoidale, dents de scie ou carr�e):
		    if (e.getSource()==Panel.Button1){		    	
		    	Generator.setwavetype("Sine");			    	 
		    }
		    if (e.getSource()==Panel.Button2){		    	
		    	Generator.setwavetype("Sawtooth");	  
		    }
		    if (e.getSource()==Panel.Button3){		    	
		    	Generator.setwavetype("Square");	  
		    }
		    // types d'enveloppes ADSR propos�s:
		    if (e.getSource()==Panel.Env1){		    	
		    	Generator.setADSRpreference("1");	
		    	Generator.envelope=new SegmentedEnvelope(Generator.setpairs());
		    	System.out.println("choix ADSR:1");
		    }
		    if (e.getSource()==Panel.Env2){		    	
		    	Generator.setADSRpreference("2");	
		    	Generator.envelope=new SegmentedEnvelope(Generator.setpairs());
		    	System.out.println("choix ADSR:2");
		    }
		    if (e.getSource()==Panel.Env3){		    	
		    	Generator.setADSRpreference("3");	
		    	Generator.envelope=new SegmentedEnvelope(Generator.setpairs());
		    	System.out.println("choix ADSR:3");
		    }

		    Panel.repaint();
	}
}