package JModular;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.sun.glass.events.MouseEvent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;



public class View extends JFrame{
	
    Model model;
    JButton addSamplebtn, addSequencerbtn, addGeneratorbtn, addPatternbtn, mainPlaybtn, mainStopbtn;
    ArrayList<PatternPanel> patternPanels=new ArrayList<PatternPanel>();
    ArrayList<SequencerPanel> sequencerPanel=new ArrayList<SequencerPanel>();
	ArrayList<ModulePanel> ModulePanels=new ArrayList<ModulePanel>();
	MainPanel MainPanel = new MainPanel();
    int totalheight=100;

    public View(Model model) {
    	
    	this.model = model;	
    	
    	// construction du tableau de bord:
    	
    	DashBoardPanel dashBoardpanel =new DashBoardPanel(model);

			addSamplebtn = new JButton();
			addSamplebtn.setPreferredSize(new Dimension (130,30));
			addSamplebtn.setText("Add Sampler");
		dashBoardpanel.add(addSamplebtn,0,0,1);   // .add(x,y,width,someComponent) in the gridBagContraints    	
	
			addGeneratorbtn = new JButton();
			addGeneratorbtn.setPreferredSize(new Dimension (130,30));
			addGeneratorbtn.setText("Add Generator");
		dashBoardpanel.add(addGeneratorbtn,2,0,1);
	
			addSequencerbtn = new JButton();
			addSequencerbtn.setPreferredSize(new Dimension (130,30));
			addSequencerbtn.setText("Add Sequencer");		
		dashBoardpanel.add(addSequencerbtn,0,1,1);
	
			addPatternbtn = new JButton();
			addPatternbtn.setPreferredSize(new Dimension (130,30));
			addPatternbtn.setText("Add Pattern Edit.");
		dashBoardpanel.add(addPatternbtn,2,1,1);
		
			mainPlaybtn = new JButton();
			mainPlaybtn.setText("Play all");
		dashBoardpanel.add(mainPlaybtn,0,2,1);	
		
			mainStopbtn=new JButton();			
			mainStopbtn.setText("Stop all");
		dashBoardpanel.add(mainStopbtn,2,2,1);	
		
			TempoPanel TmpPanel= new TempoPanel(model);
		dashBoardpanel.add(TmpPanel,0,3,3);
		
    	MainPanel.add(dashBoardpanel);
    	add(MainPanel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(200,200);
		
		JScrollPane pane = new JScrollPane(MainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.getContentPane().add(pane);

		pack();		
		setVisible(true);
	}
    
	public Dimension getPreferredSize() {
		  return new Dimension(840, 800);
	}

	public void addModulePanel(){ //ajoute le contenu de la liste des modules à l'ardoise
		for (ModulePanel Panel: ModulePanels) MainPanel.add(Panel); 
	}
	 
	public void addSamplePanel(){ //ajoute un nouveau panel VCO dans la liste des modules
		SamplePanel newPanel=new SamplePanel(model);
		ModulePanels.add(new SamplePanel(model)); //ajout du nouveau panneaux dans la liste des panneaux de modules
		addModulePanel();
	}
	
	public void addSequencerPanel(){ //ajoute un nouveau paneau sequencer dans la liste des modules
		SequencerPanel newPanel=new SequencerPanel(model);
		sequencerPanel.add(newPanel); //ajout du nouveau panneaux dans la liste des panneux du même type
		ModulePanels.add(newPanel); //ajout du nouveau panneaux dans la liste des panneaux de modules
		addModulePanel();
	}
	
	public void addPatternPanel(){ //ajoute un nouveau panel Pattern dans la liste des modules
		PatternPanel newPanel=new PatternPanel(model); 
		ModulePanels.add(new PatternPanel(model)); //ajout du nouveau panneaux dans la liste des panneaux de modules
		addModulePanel();
	}
	
	public void addGeneratorPanel(){ 
		GeneratorPanel newPanel=new GeneratorPanel(model);
		ModulePanels.add(newPanel); //ajout du nouveau panneaux dans la liste des panneaux de modules
		addModulePanel();	
	}


}