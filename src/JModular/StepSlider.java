package JModular;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JSlider;

public class StepSlider extends JSlider{
	IconThumbSliderUI sliderUI = new IconThumbSliderUI();
	
	public StepSlider(){
		this.minorTickSpacing=1;
		this.majorTickSpacing=1;
		this.setMajorTickSpacing(1);
		this.setPaintTicks(true); 
		this.setOrientation(VERTICAL);
		this.setMaximum(12);
		this.setMinimum(1);
		this.setPreferredSize(new Dimension(30,150));
		this.setUI(sliderUI);
		
		this.setSnapToTicks(true);
		
		Font font = new Font("default", Font.TRUETYPE_FONT, 8);
		Font fontbig = new Font("default", Font.BOLD, 11);

		JLabel note1 = new JLabel("-");
		JLabel note2 = new JLabel("G");
		JLabel note3 = new JLabel("");	
		JLabel note4 = new JLabel("A");	
		JLabel note5 = new JLabel("");	
		JLabel note6 = new JLabel("B");	
		JLabel note7 = new JLabel("C");	
		JLabel note8 = new JLabel("");	
		JLabel note9 = new JLabel("D");	
		JLabel note10 = new JLabel("");	
		JLabel note11 = new JLabel("E");	
		JLabel note12 = new JLabel("F");	
		JLabel note13 = new JLabel("");
		
		note1.setFont(font);
		note2.setFont(font);
		note3.setFont(font);
		note4.setFont(font);
		note5.setFont(font);
		note6.setFont(font);		
		note7.setFont(font);
		note8.setFont(font);
		note9.setFont(font);
		note10.setFont(font);
		note11.setFont(font);		
		note12.setFont(font);
		note13.setFont(font);
		
		Hashtable<Integer, JLabel> labelTable = new Hashtable();
		
		labelTable.put( new Integer( 1 ), note1);
		labelTable.put( new Integer( 2 ), note2 );
		labelTable.put( new Integer( 3 ), note3 );
		labelTable.put( new Integer( 4 ), note4 );
		labelTable.put( new Integer( 5), note5 );
		labelTable.put( new Integer( 6 ), note6 );
		labelTable.put( new Integer( 7 ), note7 );
		labelTable.put( new Integer( 8 ), note8 );
		labelTable.put( new Integer( 9 ), note9);
		labelTable.put( new Integer( 10 ), note10 );
		labelTable.put( new Integer( 11 ), note11 );
		labelTable.put( new Integer( 12 ), note12);
		labelTable.put( new Integer( 13 ), note13);
		 
		this.setLabelTable( labelTable );
		this.setPaintLabels(true);	
	}
	
	public String getnote(){
		String note = null;
		int slidervalue=this.getValue();
		if (slidervalue==1) note ="-";
		if (slidervalue==2) note ="G";
		if (slidervalue==3) note ="G#";
		if (slidervalue==4) note ="A";
		if (slidervalue==5) note ="A#";
		if (slidervalue==6) note ="B";
		if (slidervalue==7) note ="C";
		if (slidervalue==8) note ="C#";
		if (slidervalue==9) note ="D";
		if (slidervalue==10) note ="D#";
		if (slidervalue==11) note ="E";
		if (slidervalue==12) note ="F";
		if (slidervalue==13) note ="F#";	
		return note;
	}
	
	public void setnote(String note){
		if (note.compareTo("-")==0) this.setValue(1);
		if (note.compareTo("G")==0) this.setValue(2);
		if (note.compareTo("G#")==0) this.setValue(3);
		if (note.compareTo("A")==0) this.setValue(4);
		if (note.compareTo("A#")==0) this.setValue(5);
		if (note.compareTo("B")==0) this.setValue(6);
		if (note.compareTo("C")==0) this.setValue(7);
		if (note.compareTo("C#")==0) this.setValue(8);
		if (note.compareTo("D")==0) this.setValue(9);
		if (note.compareTo("D#")==0) this.setValue(10);
		if (note.compareTo("E")==0) this.setValue(11);
		if (note.compareTo("F")==0) this.setValue(12);
		if (note.compareTo("F#")==0) this.setValue(13);
	}
	
	
	public void switschStatus(String status){
		if (status.compareTo("ON")==0) sliderUI.setImageIcon("ressources/thumb-on.png");
		if (status.compareTo("OFF")==0) sliderUI.setImageIcon("ressources/thumb-mute.png");
	}
	
	
	

}
