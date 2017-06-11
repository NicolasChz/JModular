package JModular;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.border.LineBorder;

public class DashBoardPanel extends ModulePanel {
	GridBagConstraints c=new GridBagConstraints();
	
	public DashBoardPanel(Model model){
		super("DashBoard");	     
	   	display();
	}
	
	public void display(){
		LineBorder line = new LineBorder(Color.lightGray, 2, true);
	   	setBorder(line);
		c.insets = new Insets(5,5,5,5); 
		c.fill = GridBagConstraints.VERTICAL;  
	}
	
	@Override
	void add(Component someComponent,int x, int y, int width){		
		c.gridx=x;
		c.gridy=y;
		c.gridwidth=width;
		add(someComponent, c);
	}
}
