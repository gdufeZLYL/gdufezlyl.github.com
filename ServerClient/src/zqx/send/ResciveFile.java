package zqx.send;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ResciveFile {

	private MulticastSocket clientSocket;// 发送服务多播组
	private DatagramSocket serverSocket;// 接收
	private static int serverPort = 40000;
	private static int clientPort = 30000;
	private BufferedOutputStream fos;

	private class ReceiveThread implements Runnable {
		private InetAddress serverAddress;
		byte[] newData =new byte[8];

		public ReceiveThread() {
			try {
				serverAddress = InetAddress.getByName("224.224.10.0");
				clientSocket = new MulticastSocket(serverPort);
				clientSocket.joinGroup(serverAddress);
				clientSocket.setTimeToLive(1);
			} catch (final IOException e) {
			}// end of try-catch
		}

		public void run() {
			
			try {
				fos = new BufferedOutputStream(new FileOutputStream(new File("c:GBC.java"),true));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			while(true){
				DatagramPacket packet;
				 // 创建byte数组
				packet = new DatagramPacket(newData, newData.length); // 待接收的数据包
				try {
					clientSocket.receive(packet);// 接收数据包
				} catch (IOException e) {
					System.out.println("接收失败！");
				} // end of try-catch
				byte data[]=packet.getData();
				int size=packet.getData().length;
				String message = new String(data, 0, packet.getLength());				// 获取数据包中内容
				if(message.equals("over")){
					System.out.println("这里接受到了OVER");
					break;
				}else{
					try {
						fos.write(data, 0, size);
						System.out.println("写入成功"+"这次写入的大小为"+size);
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}		//end of while	
			try {	
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			}// end of run()
		}

	public static void main(String[] args) {
		ReceiveThread receive=new ResciveFile().new ReceiveThread();
		new Thread(receive).start();
		
	}
}
