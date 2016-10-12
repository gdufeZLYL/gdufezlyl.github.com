package zqx.fileupload;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

import javax.swing.JFileChooser;

public class FileUpload extends Thread{
	 private Socket client;
	 private FileInputStream fis;
	 private DataOutputStream dos;
	 
	 public FileUpload(Socket socket){
		 this.client = socket;
		 start();
	 }
	 
	 public void run(){
		 try {
	            try {        
	                //初始化文件选择
	        		JFileChooser fDialog = new JFileChooser();
	        		//设置文件选择框的标题 
	        		fDialog.setDialogTitle("请选择上传的文件");
	        		//弹出选择框
	        		int returnVal = fDialog.showOpenDialog(null);
	        		// 如果是选择了文件
	        		if(JFileChooser.APPROVE_OPTION == returnVal){
	        		       //打印出文件的路径，你可以修改位 把路径值 写到 textField 中
	        			//System.out.println(fDialog.getSelectedFile());
	        			//String filename = String(fDialog.getSelectedFile());
	        			//向服务端传送文件
	                    //File file =new File("c:/test.doc");
	                    String filename = fDialog.getSelectedFile().getAbsolutePath().replace('\\', '/');
	        			//String filename = "D:/向dalao低头.txt";
	                    System.out.println(filename);
	                    File file =new File(filename);
	                    //System.out.println(filename);
	                    fis =new FileInputStream(file);
	                    dos =new DataOutputStream(client.getOutputStream());
	                     
	                    //文件名和长度
	                    dos.writeUTF(file.getName());
	                    dos.flush();
	                    dos.writeLong(file.length());
	                    dos.flush();
	                     
	                    //传输文件
	                    byte[] sendBytes =new byte[1024];
	                    int length =0;
	                    while((length = fis.read(sendBytes,0, sendBytes.length)) >0){
	                        dos.write(sendBytes,0, length);
	                        dos.flush();
	                    }
	        		}
	        		               
	            }catch (Exception e) {
	                e.printStackTrace();
	            }finally{
	                if(fis !=null)
	                    fis.close();
	                if(dos !=null)
	                    dos.close();
	                client.close();
	            }
	        }catch (Exception e) {
	            e.printStackTrace();
	        }
	 }
}
