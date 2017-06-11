package JModular;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.border.LineBorder;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.swing.DoubleBoundedRangeModel;
import com.jsyn.swing.PortModelFactory;
import com.jsyn.swing.RotaryController;

public class GeneratorPanel extends ModulePanel{

	 int ID;
	 ButtonGroup bG;
	 JRadioButton Button1 = new JRadioButton();
	 JRadioButton Button2 = new JRadioButton();
	 JRadioButton Button3 = new JRadioButton();
	 JRadioButton Env1 =new JRadioButton();
 	 JRadioButton Env2 =new JRadioButton();
 	 JRadioButton Env3 =new JRadioButton();

	 private JLabel image;
	 GeneratorUnit Generator;
	 String[] preferences = { "1", "2", "3",};
	 JComboBox ADSRpref = new JComboBox(preferences);

	
	public GeneratorPanel(Model model){
		super("Generator");	
		this.Generator=model.GeneratorUnits.get(model.GeneratorUnits.size()-1);
		this.ID=Generator.ID;
		display();    
	} //end creator
	
	public void display(){

		setLayout(new GridBagLayout());   
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
	   	LineBorder line = new LineBorder(Color.lightGray, 2, true);	   	
	   	setBorder(line);
	   	setBackground(Color.white);	
	   	
	   	c.gridx = 0;
	   	c.gridy = 0;
	   	c.insets = new Insets(2,2,2,2);
	   	Font font = new Font("Serif", Font.TRUETYPE_FONT, 15);
		IDLabel.setText(""+Generator.ID);
		IDLabel.setFont(font);

		add(IDLabel,c);

		c.gridx = 1;
	   	c.gridy = 0;
	   	this.add(VCOPanel(), c);
	   	c.gridx = 2;
	   	c.gridy = 0;
	   	this.add(EnvelopePanel(),c);
	    c.gridx = 3;
	   	c.gridy = 0;
	   	this.add(volumePanel(),c);
	   	
	    this.setVisible(true);	
	}
	
    private RotaryController setupPortKnob(UnitInputPort port) {
        DoubleBoundedRangeModel model = PortModelFactory.createExponentialModel(port);
        RotaryController knob = new RotaryController(model);
        knob.setLineColor(Color.darkGray);
        return knob;
    }

    private SubPanel volumePanel(){
    	SubPanel volumePanel=new SubPanel("ressources/VolumePanel.png");    	
    	volumePanel.setPreferredSize(new Dimension(80,130));
    	volumePanel.setLayout(new GridBagLayout());   
    	GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
	   	c.gridy = 0;
    	volumePanel.add(setupPortKnob(Generator.envelopePlayer.amplitude),c);
    	return volumePanel;
    }
    
    public SubPanel EnvelopePanel(){
    	
    	SubPanel EnvelopePanel=new SubPanel("ressources/EnvelopePanel.png");
    	EnvelopePanel.setLayout(new GridBagLayout());
    	EnvelopePanel.setPreferredSize(new Dimension(110,130));
    	GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
    	 Env1 =new JRadioButton();
    	 Env2 =new JRadioButton();
    	 Env3 =new JRadioButton();
    	JLabel icon1=new JLabel();
    	JLabel icon2=new JLabel();
    	JLabel icon3=new JLabel();
    	
    	icon1.setIcon(new ImageIcon("ressources/adsr1-icon.png"));
    	icon2.setIcon(new ImageIcon("ressources/adsr2-icon.png"));
    	icon3.setIcon(new ImageIcon("ressources/adsr3-icon.png"));

    	ButtonGroup ADSR = new ButtonGroup();
    	ADSR.add(Env1);
    	ADSR.add(Env2);
    	ADSR.add(Env3);
//    	c.insets = new Insets(0,5,5,5);
    	Env1.setSelected(true);
    	c.gridx = 0;
	   	c.gridy = 0;
    	EnvelopePanel.add(Env1,c);
    	c.gridx = 1;
	   	c.gridy = 0;
	   	EnvelopePanel.add(icon1,c);
	   	c.gridx = 0;
	   	c.gridy = 1;
    	EnvelopePanel.add(Env2,c);
    	c.gridx = 1;
	   	c.gridy = 1;
	   	EnvelopePanel.add(icon2,c);
	   	c.gridx = 0;
	   	c.gridy = 2;
    	EnvelopePanel.add(Env3,c);
    	c.gridx = 1;
	   	c.gridy = 2;
	   	EnvelopePanel.add(icon3,c);
		return EnvelopePanel;    	
    }
    
	public SubPanel VCOPanel(){
		
		SubPanel VCOPanel=new SubPanel("ressources/VCOPanel.png");
		ImageIcon Icon_saw=new ImageIcon( "ressources/sawtooth-icon.png");
		ImageIcon Icon_sine=new ImageIcon( "ressources/sine-icon.png");
		ImageIcon Icon_Square=new ImageIcon( "ressources/square-icon.png");
		Font font = new Font("Serif", Font.TRUETYPE_FONT, 10);
		VCOPanel.setLayout(new GridBagLayout());
		VCOPanel.setPreferredSize(new Dimension(80,130));
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		Button1.setFont(font);
		Button2.setFont(font);
		Button3.setFont(font);
	    bG = new ButtonGroup();
	    bG.add(Button1);
	    bG.add(Button2);
	    bG.add(Button3);
	    c.gridx = 0;
	   	c.gridy = 0;
	   	VCOPanel.add(Button1,c);
		c.gridx = 0;
	   	c.gridy = 1;
	   	VCOPanel.add(Button2,c);
	   	c.gridx = 0;
	   	c.gridy = 2;
	   	VCOPanel.add(Button3,c);
		c.gridx = 1;
	   	c.gridy = 0;
	   	JLabel image1 = new JLabel(Icon_sine);
	   	VCOPanel.add(image1,c);
	   	c.gridx = 1;
	   	c.gridy = 1;
	   	JLabel image2 = new JLabel(Icon_saw);
	   	VCOPanel.add(image2,c);
	   	c.gridx = 1;
	   	c.gridy = 2;
	   	JLabel image3 = new JLabel(Icon_Square);
	   	VCOPanel.add(image3,c);
	   		   	
	   	Button2.setSelected(true);
	   	    
		return VCOPanel;         
		
	}

}
