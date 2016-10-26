package zqx.bchandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

public class BCHandler extends Thread{
	private Socket client = null;
    private JTextArea jta1 = null;
    
    OutputStream os = null;  
    PrintWriter pw = null;
    
    InputStream is = null;  
    BufferedReader br = null;  
    
    public BCHandler(Socket client, JTextArea jta1){
    	this.client = client;
    	this.jta1 = jta1;
 	
        start();
    	
    }
    
    @Override
    public void run() {
    	try {  
            //2.得到socket读写流  
            OutputStream os=client.getOutputStream();  
            PrintWriter pw=new PrintWriter(os);  
            //输入流  
            InputStream is=client.getInputStream();  
            BufferedReader br=new BufferedReader(new InputStreamReader(is));  

            //接收服务器的相应  
            String reply=null;
            while(true){
            	reply=br.readLine();
            	if(reply != null){
            		jta1.append(reply+"\n");
            	}
            }
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {
        	//4.关闭资源  
        	try{
	            br.close();  
	            is.close();  
	            pw.close();  
	            os.close();  
	            client.close(); 
        	} catch(IOException e){
        		e.printStackTrace();
        	}
        }
    }
    
}


