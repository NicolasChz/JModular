package JModular;

public class JModular {
    public static void main(String[] arg) {
    	
	Model model = new Model();
	View view = new View(model);	
	Controller controller =  new Controller(model, view);

  }
}
