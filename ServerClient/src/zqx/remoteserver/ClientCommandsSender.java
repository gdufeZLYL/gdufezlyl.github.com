package zqx.remoteserver;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JPanel;

/**
 * @author ZLYL
 */
class ClientCommandsSender implements KeyListener,
        MouseMotionListener,MouseListener {

    private Socket cSocket = null;
    private JPanel cPanel = null;
    private PrintWriter writer = null;
    private Rectangle clientScreenDim = null;

    ClientCommandsSender(Socket s, JPanel p, Rectangle r) {
        cSocket = s;
        cPanel = p;
        clientScreenDim = r;
        //Associate event listners to the panel
        cPanel.addKeyListener(this);
        cPanel.addMouseListener(this);
        cPanel.addMouseMotionListener(this);
        try {
             //Prepare PrintWriter which will be used to send commands to
             //the client
            writer = new PrintWriter(cSocket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }

    //Not implemeted yet
    public void mouseDragged(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        double xScale = clientScreenDim.getWidth()/cPanel.getWidth();
        System.out.println("xScale: " + xScale);
        double yScale = clientScreenDim.getHeight()/cPanel.getHeight();
        System.out.println("yScale: " + yScale);
        System.out.println("Mouse Moved");
        writer.println(EnumCommands.MOVE_MOUSE.getAbbrev());
        writer.println((int)(e.getX() * xScale));
        writer.println((int)(e.getY() * yScale));
        writer.flush();
    }

    //this is not implemented
    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse Pressed");
        writer.println(EnumCommands.PRESS_MOUSE.getAbbrev());
        int button = e.getButton();
        int xButton = 16;
        if (button == 3) {
            xButton = 4;
        }
        writer.println(xButton);
        writer.flush();
    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse Released");
        writer.println(EnumCommands.RELEASE_MOUSE.getAbbrev());
        int button = e.getButton();
        int xButton = 16;
        if (button == 3) {
            xButton = 4;
        }
        writer.println(xButton);
        writer.flush();
    }

    //not implemented
    public void mouseEntered(MouseEvent e) {
    }

    //not implemented
    public void mouseExited(MouseEvent e) {

    }

    //not implemented
    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        System.out.println("Key Pressed");
        writer.println(EnumCommands.PRESS_KEY.getAbbrev());
        writer.println(e.getKeyCode());
        writer.flush();
    }

    public void keyReleased(KeyEvent e) {
        System.out.println("Mouse Released");
        writer.println(EnumCommands.RELEASE_KEY.getAbbrev());
        writer.println(e.getKeyCode());
        writer.flush();
    }

}