package JModular;

// Le beat doit �tre unique pour tous les modules qui l'utilisent
// Un patron de conception singleton est utilis� ici

public class BeatSingleton {

	private static Beat beat;
	 
	protected BeatSingleton() {
		if (beat != null) {
            throw new IllegalStateException("Already instantiated");
		} else { beat = new Beat();};
	}
 

	public static Beat getInstance() {
		return beat;
	}
}