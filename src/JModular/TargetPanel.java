package JModular;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.swing.DoubleBoundedRangeModel;
import com.jsyn.swing.PortModelFactory;
import com.jsyn.swing.RotaryTextController;

public abstract class TargetPanel extends ModulePanel{

	public TargetPanel(String Moduletype){
		super(Moduletype);
	}
	
    public RotaryTextController setupPortKnob(UnitInputPort port, String label) {
        DoubleBoundedRangeModel model = PortModelFactory.createExponentialModel(port);
        RotaryTextController knob = new RotaryTextController(model, 5);
        knob.setBorder(BorderFactory.createTitledBorder(label));
        knob.setTitle(label);
        return knob;
    }
	
	
	
	
}
