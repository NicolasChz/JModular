package JModular;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;

public class SampleListener implements ActionListener {	 

	private SampleUnit Sample;
	private SamplePanel Panel;
	
	public SampleListener(SampleUnit Sample, SamplePanel Panel){
		this.Sample=Sample;	
		this.Panel=Panel;
	}	
	
	public void actionPerformed(ActionEvent e) {    

		  if (e.getSource()==Panel.browsebtn){
			  JFileChooser fileChooser = new JFileChooser();
			  fileChooser.setCurrentDirectory(new File("./Samples")); // ouverture de l'explorateur dans le dossier Samples à la racine du projet
		        int returnValue = fileChooser.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		          File selectedFile = fileChooser.getSelectedFile();		       
		          Sample.setFilePath(selectedFile.toString());
		          Panel.setFilePathLabel(selectedFile.getName().toString());
		        }
		  }

	  
		    Panel.repaint();
	}
}