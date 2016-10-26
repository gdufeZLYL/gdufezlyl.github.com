package zqx.remoteserver;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author ZLYL
 */
class ClientHandler extends Thread {

    private JDesktopPane desktop = null;
    //private JScrollPane desktop = null;
    private Socket cSocket = null;
    private JInternalFrame interFrame = new JInternalFrame("Client Screen",
                                                            true, true, true);
    private JPanel cPanel = new JPanel();
    
    public ClientHandler(Socket cSocket, JDesktopPane desktop) {
    	//JDesktopPane desktop
    	this.cSocket = cSocket;
        this.desktop = desktop;
        start();
    }

    /*
     * Draw GUI per each connected client
     */
    public void drawGUI(){
        interFrame.setLayout(new BorderLayout());
        interFrame.getContentPane().add(cPanel,BorderLayout.CENTER);
        
        interFrame.setSize(100,100);
        desktop.add(interFrame);
    	
    	//desktop.add(cPanel);
    	
        //desktop.add(new JButton("test"));
        //GridLayout GridLayoutgridLayout = new GridLayout(3, 3);
        //desktop.setLayout(new GridLayout(3, 3, 10, 10));
//        try {
//            //Initially show the internal frame maximized
//            interFrame.setMaximum(true);
//            //interFrame.setSize(200, 200);
//        } catch (PropertyVetoException ex) {
//            ex.printStackTrace();
//        }
        interFrame.setSize(386, 288);
        //this allows to handle KeyListener events
        cPanel.setFocusable(true);
        //cPanel.setVisible(true);
        interFrame.setVisible(true);
    }

    public void run(){

        //used to represent client screen size
        Rectangle clientScreenDim = null;
        //Used to read screenshots and client screen dimension
        ObjectInputStream ois = null;
        //start drawing GUI
        drawGUI();

        try{
            //Read client screen dimension
            ois = new ObjectInputStream(cSocket.getInputStream());
            clientScreenDim =(Rectangle) ois.readObject();
        }catch(IOException ex){
            ex.printStackTrace();
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        //Start recieveing screenshots
        new ClientScreenReciever(ois,cPanel);
        //Start sending events to the client
        //new ClientCommandsSender(cSocket,cPanel,clientScreenDim);
    }

}
