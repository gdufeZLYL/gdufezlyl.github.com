package zqx.remoteserver;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneLayout;

import zqx.fileupload.FileUploadHandler;

/**
 * This is the entry class of the server
 */
public class ServerInitiator extends JFrame implements ActionListener{
    //Main server frame
    //private JFrame frame = new JFrame();
    //JDesktopPane represents the main container that will contain all
    //connected clients' screens
    private JDesktopPane desktop = null;
    private Container contentPane = null;
    private JButton jb1 = null;
    private JButton jb2 = null;
    private JTextArea jta1 = null;
    private JPanel jp = null;
    private File file;
    private FileInputStream fis;
    
    private static List<Socket> fileclients = new ArrayList<Socket>();//文件发送--客户端集合
    private static List<Socket> sos=new ArrayList<Socket>();//桌面广播--客户端集合
    
    public static void main(String args[]){
    	String port = "5000";				//图像传播
    	String port2 = "6666";				//文件上传
        String port3 = "7777";				//文件发送
        String port4 = "10000";				//桌面广播
        new ServerInitiator().initialize(Integer.parseInt(port), Integer.parseInt(port2), Integer.parseInt(port3),Integer.parseInt(port4));
    }
    
    public ServerInitiator(){
    	desktop = new JDesktopPane();
        contentPane = this.getContentPane();
        jb1 = new JButton("桌面广播");
        jb2 = new JButton("文件发送");
        jta1 = new JTextArea(2, 60);
        
        jp = new JPanel();
        
        jb2.addActionListener(this);
        jb1.addActionListener(this);
        
        jp.add(jb1);
        jp.add(jta1);
        jp.add(jb2);
        
        jp.setLayout(new FlowLayout());
             
        contentPane.add(desktop);
        contentPane.add(jp,BorderLayout.SOUTH);

        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Show the frame in a maximized state
        this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        
    }

    public void initialize(int port,int port2, int port3, int port4){

        try {
            ServerSocket sc = new ServerSocket(port);			//图像传播
            ServerSocket sc2 = new ServerSocket(port2);		//文件上传
            ServerSocket sc3 = new ServerSocket(port3);			//文件发送
            ServerSocket sc4 = new ServerSocket(port4);			//桌面广播
            
            //DataInputStream dis;
            //FileOutputStream fos;
            //Listen to server port and accept clients connections
            while(true){
                Socket client = sc.accept();			//图像传播
                Socket client2 = sc2.accept();			//文件上传
                Socket client3 = sc3.accept();			//文件发送
                Socket client4 = sc4.accept();			//桌面广播
                sos.add(client4);
                fileclients.add(client3);

                System.out.println("New client Connected to the server");
                                
                //Per each client create a ClientHandler
                new ClientHandler(client,desktop);
                new FileUploadHandler(client2);
                
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand()=="文件发送"){
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
                
    			//文件路径转换
    			String filename = fDialog.getSelectedFile().getAbsolutePath().replace('\\', '/');
    			//String filename = "D:/向dalao低头.txt";	示例	
               
    			System.out.println(filename);
                file =new File(filename);

                /**
                 * 文件发送
                 */
                Iterator<Socket> i=fileclients.iterator();
                Socket temps = null;
                FileInputStream fis = null;
       		 	DataOutputStream dos = null;
                
       		 	while(i.hasNext()){
	       		 	temps=(Socket)i.next();
	       		 	try{
	       		 		dos =new DataOutputStream(temps.getOutputStream());
	       		 		//文件名和长度
	                    dos.writeUTF(file.getName());
	                    dos.flush();
	                    dos.writeLong(file.length());
	                    dos.flush();
	                    fis =new FileInputStream(file);
	                    //传输文件
	                    byte[] sendBytes =new byte[1024];
	                    int length =0;
	                    while((length = fis.read(sendBytes,0, sendBytes.length)) >0){
	                        dos.write(sendBytes,0, length);
	                        dos.flush();
	                    }
       		 		}catch(IOException e1){
       		 			fileclients.remove(temps);
       		 			e1.printStackTrace();
       		 		}
       		 	}
               
    		}
		}else if(e.getActionCommand()=="桌面广播"){
			
			/**
			 * 桌面广播信息发送
			 */
			Iterator<Socket> i=sos.iterator();
			Socket temps=null;
			PrintWriter os = null;
			String[] lineString = jta1.getText().split("\n");
			while(i.hasNext()){
			        temps=(Socket)i.next();
			        try {
						os=new PrintWriter(temps.getOutputStream());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						sos.remove(temps);
						e1.printStackTrace();
					}
			        for(String s : lineString){
						//message_list.add(s);
			        	os.println(s+"\n");
				        os.flush();
					}
			}
			jta1.setText("");
		
		}
	}
}
