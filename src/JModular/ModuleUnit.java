package JModular;

// classe enc�tre des modules 

public abstract class ModuleUnit {

	int ID;	// l'id sera l'index d'un module dans la liste des modules e m�mes types (voir la classe model)
	
	public ModuleUnit(int ID){
		super();
		this.ID=ID;
	}
	
	public int getID(){
		return ID;
	}

		
}
