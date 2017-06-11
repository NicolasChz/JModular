package JModular;

// le beat est un �tat applicable � l'un des 16 temps d'une mesure
// cet �tat est d�finit par :
// - Sa position (de 0 � 15)
// - le bool�en TOP (il indique si un temps est jouable: Les s�quenceurs ont le feu vert pour y placer une note)
// - le bool�en BEGINNING (il vaut true sur le temps 0. ceci permet de rep�rer le d�but de la mesure pour d�buter la lecture d'uen s�quence)
// la valeur step permet de subdiviser le temps en quatre
// le beat est instanci� en singleton et sa valeur est modifi�e par le module tempoUnit

public class Beat {

	private boolean TOP=false;
	private boolean BEGINNING=false;
	private int step=3;
	private int position=0;
	
	public Beat(){		

	}
	
	public void increment(){		
		if (position==(step*16)) position=0;  // on boucle au 16i�me temps		
		if (	position==0 
				|| position==(step) 
				|| position==(step*2)						
				|| position==(step*3) 
				|| position==(step*4) 
				|| position==(step*5) 
				|| position==(step*6) 
				|| position==(step*7) 
			// c�sure 	
				|| position==(step*8)  
				|| position==(step*9) 
				|| position==(step*10)
				|| position==(step*11) 
				|| position==(step*12) 
				|| position==(step*13) 
				|| position==(step*14) 
				|| position==(step*15)
			) TOP=true; else TOP=false;	 // pour chaque multiple de step, on a un temps jouable (pour lequel TOP=true)
		
		if (position==0) BEGINNING=true; else BEGINNING=false; //le premier temps est marqu� comme d�but dela s�quence
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
