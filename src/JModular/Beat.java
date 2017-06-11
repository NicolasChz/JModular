package JModular;

// le beat est un état applicable à l'un des 16 temps d'une mesure
// cet état est définit par :
// - Sa position (de 0 à 15)
// - le booléen TOP (il indique si un temps est jouable: Les séquenceurs ont le feu vert pour y placer une note)
// - le booléen BEGINNING (il vaut true sur le temps 0. ceci permet de repérer le début de la mesure pour débuter la lecture d'uen séquence)
// la valeur step permet de subdiviser le temps en quatre
// le beat est instancié en singleton et sa valeur est modifiée par le module tempoUnit

public class Beat {

	private boolean TOP=false;
	private boolean BEGINNING=false;
	private int step=3;
	private int position=0;
	
	public Beat(){		

	}
	
	public void increment(){		
		if (position==(step*16)) position=0;  // on boucle au 16ième temps		
		if (	position==0 
				|| position==(step) 
				|| position==(step*2)						
				|| position==(step*3) 
				|| position==(step*4) 
				|| position==(step*5) 
				|| position==(step*6) 
				|| position==(step*7) 
			// césure 	
				|| position==(step*8)  
				|| position==(step*9) 
				|| position==(step*10)
				|| position==(step*11) 
				|| position==(step*12) 
				|| position==(step*13) 
				|| position==(step*14) 
				|| position==(step*15)
			) TOP=true; else TOP=false;	 // pour chaque multiple de step, on a un temps jouable (pour lequel TOP=true)
		
		if (position==0) BEGINNING=true; else BEGINNING=false; //le premier temps est marqué comme début dela séquence
		position++;	
	}
	
	public boolean getTOP(){		
		return TOP;
	}
	
	public int getposition(){
		return position;
	}
	
	public boolean getBEGINNING(){
		return BEGINNING;
	}

}
