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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import zqx.send.ViewFile;

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
    private JButton jb3 = null;
    private JPanel jp = null;

    public static void main(String args[]){
    	String id = JOptionPane.showInputDialog("请输入教工号");
    	String user = JOptionPane.showInputDialog("请输入姓名");
    	String port = "5000";
    	String port2 = "5001";
        new ServerInitiator().initialize(Integer.parseInt(port), Integer.parseInt(port2));
    }
    
    public ServerInitiator(){
    	desktop = new JDesktopPane();
        contentPane = this.getContentPane();
        jb1 = new JButton("进入聊天室");
        jb2 = new JButton("文件上传");
        jb3 = new JButton("文件下载");
        jp = new JPanel();
        
        jp.add(jb1);
        jp.add(jb2);
        jp.add(jb3);
        jp.setLayout(new FlowLayout());
        //jp.setMinimumSize(getMinimumSize());
        
        contentPane.add(desktop);
        contentPane.add(jp,BorderLayout.SOUTH);
		//contentPane.add(desktop);
		//contentPane.add(desktop);
		//contentPane.setLayout(new GridLayout(2,1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Show the frame in a maximized state
        this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        
    }

    public void initialize(int port,int port2){

        try {
            ServerSocket sc = new ServerSocket(port);
            ServerSocket sc2 = new ServerSocket(port2);
            //DataInputStream dis;
            //FileOutputStream fos;
            //Show Server GUI
            //drawGUI();
            //Listen to server port and accept clients connections
            while(true){
                Socket client = sc.accept();
                Socket client2 = sc2.accept();
                System.out.println("New client Connected to the server");
                                
                //Per each client create a ClientHandler
                new ClientHandler(client,desktop);
                new FileUploadHandler(client2);
                
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /*
     * Draws the main server GUI
     */
    public void drawGUI(){
    		
            jp.add(jb1);
            jp.add(jb2);
            jp.add(jb3);
            jp.setLayout(new GridLayout(1,3));
            
            contentPane.add(jp);
    		contentPane.add(desktop,BorderLayout.SOUTH);
    		contentPane.setLayout(new GridLayout(2,1));
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //Show the frame in a maximized state
            this.setExtendedState(this.getExtendedState()|JFrame.MAXIMIZED_BOTH);
            this.setVisible(true);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand()=="文件上传"){
        	ViewFile vf= new ViewFile();
        	vf.setVisible(true);
        }
	}
}