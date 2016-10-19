package zqx.send;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

public class OpenAction implements ActionListener{
	
	private  ViewFile vf;
	
	public OpenAction(ViewFile vf) {
		this.vf=vf;
	}

	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		int rVal = fc.showOpenDialog(vf);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			vf.getLblShow().setText(file.getAbsolutePath());
		}// end of if
	}

}
