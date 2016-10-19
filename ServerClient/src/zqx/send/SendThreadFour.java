package zqx.send;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SendThreadFour {

	private static InetAddress serverAddress;
	private static MulticastSocket serverSocket;// 发送服务多播组
	private static int serverPort = 40000;
	private ViewFile vf;

	public SendThreadFour(ViewFile vf) {
		this.vf = vf;
	}

	static {
		try {
			serverAddress = InetAddress.getByName("224.224.10.0");
			serverSocket = new MulticastSocket(serverPort);
			serverSocket.joinGroup(serverAddress);
			serverSocket.setTimeToLive(1);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void beginSend(){
		SendThread send=new SendThread();
		new Thread(send).start();
	}
	
	class SendThread implements Runnable{

	public void run() {
		System.out.println("运行到了这里");
		try {
			
			DatagramPacket packet = null; // 声明DatagramPacket对象
			File file=new File(vf.getLblShow().getText());
			BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));
			vf.getProgressBar().setMaximum((int) file.length());//设置进度条的长度
			byte[] data = new byte[8];
			try {
				int size = fis.read(data);
				while(size!=-1){
					packet = new DatagramPacket(data, data.length, serverAddress, serverPort); // 将数据打包
					serverSocket.send(packet);// 发送数据
					size = fis.read(data);
					//以下控制进度条
					int value=vf.getProgressBar().getValue();
					if(value<vf.getProgressBar().getMaximum()){						
						vf.getProgressBar().setValue(value+8);
					}else{
						break;
					}							
				}//end of while
				fis.close();//关闭文件流
				data=new String("over").getBytes();
				packet = new DatagramPacket(data, data.length, serverAddress, serverPort); // 将数据打包
				serverSocket.send(packet);// 发送数据
				JOptionPane pan=new JOptionPane();
				pan.showConfirmDialog(vf, "数据OVER发送成功","文件发送完毕",JOptionPane.CANCEL_OPTION);
				System.out.println("数据OVER发送成功");//给对方一个字符提醒他文件发送完毕
				System.exit(1);
			} catch (IOException e) {
				System.out.println("发送失败："+e.getMessage());// 输出异常信息
			}//end of try-catch	
		} catch (Exception e) {
			System.out.println("其他未知错误"+e.getMessage());
		}
	}//end of run
		
	}//end of thread
	
	
	
}
