package JModular;

import java.awt.event.ActionEvent;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TempoListener implements ChangeListener {
	private Model model;

	public TempoListener(Model model){
		this.model=model;	
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		System.out.println("coucou");
	}
	
	public void actionPerformed(ActionEvent e) {    	
		System.out.println("coucou");
	}
	
	

}
