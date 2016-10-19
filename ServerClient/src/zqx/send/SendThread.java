package zqx.send;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SendThread implements Runnable{
	
	private ViewFile vf;
	public SendThread(ViewFile vf) {
		this.vf=vf;
	}

	public void run() {
		try {
			InputStream fis = new FileInputStream(new File(vf.getLblShow().getText()));
			OutputStream fos = new FileOutputStream("gt.jpg");
			byte[] array = new byte[1024];
			
			try {
				int size = fis.read(array);
			while (size != -1) {
				fos.write(array, 0, size);
				size = fis.read(array);
			}// end of while
			fos.flush(); // Çå¿Õ»º´æ
			fos.close();
			fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("over....");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
