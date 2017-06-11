package JModular;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class PatternPanel extends ModulePanel{
	
	Model model;
	ArrayList<PatternPad> pads=new ArrayList<PatternPad>();	 
	private PatternUnit patternUnit;
	public JComboBox Targetlist=new JComboBox();
	
	public PatternPanel(Model model) {
		
		super("Pattern");
		this.model=model;
		this.patternUnit=model.PatternUnits.get(model.PatternUnits.size()-1);
		display();
	}

	public void display(){
				
		setLayout(new FlowLayout()); 
		
		JPanel middleSubPanel=new SubPanel("genericPanel.png");
			int maxPadsnumber=patternUnit.getmaxPadsnumber();
		   	LineBorder line = new LineBorder(Color.lightGray, 2, true);
		   	setBorder(line);
		   	setBackground(Color.white);	
		   	
		   	for (int i=0; i<maxPadsnumber;i++){
		   		PatternPad newpad=new PatternPad();
		   		pads.add(newpad);
		   		middleSubPanel.add(newpad);
		   	}

		JPanel BottomSubPanel=new SubPanel("genericPanel.png");
			JLabel Targetlabel=new JLabel("Connect to Sequencer");
		
		BottomSubPanel.add(Targetlabel);
		BottomSubPanel.add(Targetlist);
		   	
		add(middleSubPanel);   	
		add(BottomSubPanel);    	
		refreshTargetlist();   	
		this.setVisible(true);	
	}
	
	public void refreshTargetlist(){			
		Object alreadyselectedItem=null;		
		if (Targetlist.getItemCount()>0) {
			alreadyselectedItem=Targetlist.getSelectedItem();			
			Targetlist.removeAllItems();	
		}
		for (SequencerUnit target : model.SequencerUnits){ Targetlist.addItem(""+target.ID); }
		Targetlist.setSelectedItem(alreadyselectedItem);				 
	}
	
	
}
