package zqx.send;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SureAction implements ActionListener{
	
	private ViewFile vf;
	
	public SureAction(ViewFile vf) {
		this.vf=vf;
	}

	public void actionPerformed(ActionEvent e) {
		SendThreadFour st=new SendThreadFour(vf);
		st.beginSend();
	}//end of faction

}

