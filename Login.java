package zqx.login;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.*;

import zqx.info.StudentInfo;
import zqx.info.UI;


public class Login extends JFrame implements ActionListener{
	//获取本机IP
	private static String getIpAddress() throws UnknownHostException { 
		InetAddress address = InetAddress.getLocalHost(); 
		return address.getHostAddress(); 
	}
	
	JButton jb1,jb2=null;  
    //JRadioButton jrb1,jrb2=null;  
    JPanel jp1,jp2,jp3,jp4,jp5=null;  
    JTextField jtf1, jtf2=null;  
    JLabel jlb1,jlb2,jlb3, jlb4=null;  
    JPasswordField jpf=null;  
    ButtonGroup bg=null;  
              
    public static void main(String[] args) {   
        Login ms=new Login();  
    }
    
    public Login()  
    {  
         //创建组件  
        jb1=new JButton("登录");  
        jb2=new JButton("重置");  
        //设置监听  
        jb1.addActionListener(this);  
        jb2.addActionListener(this);  
          
        //jrb1=new JRadioButton("教师");  
        //jrb2=new JRadioButton("学生");  
        //bg=new ButtonGroup();  
        //bg.add(jrb1);  
        //bg.add(jrb2);  
        //jrb2.setSelected(true);  
          
        jp1=new JPanel();  
        jp2=new JPanel();  
        jp3=new JPanel();  
        //jp4=new JPanel();
        jp5=new JPanel();
          
        jlb1=new JLabel("学    号：");
        jlb3=new JLabel("密    码：");  
        //jlb4=new JLabel("权    限：");
        jlb2=new JLabel("姓    名：");
          
        jtf1=new JTextField(10);
        jtf2=new JTextField(10);
        jpf=new JPasswordField(10);  
        //加入到JPanel中  
        jp1.add(jlb1);  
        jp1.add(jtf1);  
        
        jp2.add(jlb2);
        jp2.add(jtf2);
        
        jp3.add(jlb3);  
        jp3.add(jpf);  
          
        //jp4.add(jlb4);  
        //jp4.add(jrb1);  
        //jp4.add(jrb2);  
          
        jp5.add(jb1);  
        jp5.add(jb2);  
          
        //加入JFrame中  
        this.add(jp1);  
        this.add(jp2);  
        this.add(jp3);  
        //this.add(jp4);
        this.add(jp5);
        //设置布局管理器  
        this.setLayout(new GridLayout(4,1));  
        //给窗口设置标题  
        this.setTitle("远程桌面监控系统");  
        //设置窗体大小  
        this.setSize(300,210);  
        //设置窗体初始位置  
        this.setLocation(200, 150);  
        //设置当关闭窗口时，保证JVM也退出  
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        //显示窗体  
        this.setVisible(true);  
        this.setResizable(true);  
          
    }
    
    public void actionPerformed(ActionEvent e) {  
          
        if(e.getActionCommand()=="登录")  
        {  
//            //如果选中教师登录  
//            if(jrb1.isSelected())  
//            {  
//                   
//            }else if(jrb2.isSelected()) //学生在登录系统  
//            {  
            	stulogin();
//            }  
              
        }else if(e.getActionCommand()=="重置")  
        {  
              clear();  
        }             
          
    }
    
  //学生登录判断方法  
    public void stulogin()  
    {  
        if(jtf1.getText().isEmpty()||jtf2.getText().isEmpty()||jpf.getPassword().length == 0)  
        {  
            JOptionPane.showMessageDialog(null,"请输入学号、姓名和密码！","提示消息",JOptionPane.WARNING_MESSAGE);
        }else{  
        	System.out.println("登录成功");  
            JOptionPane.showMessageDialog(null,"登录成功！","提示消息",JOptionPane.WARNING_MESSAGE);  
            String id = jtf1.getText();
            String username = jtf2.getText();
            clear();
            dispose();
            try {
				StudentInfo studentinfo = new StudentInfo(id, username, getIpAddress());
				studentinfo.initialize(getIpAddress(), 5000);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
            //UI ui=new UI();
        }  
    }
    
    public  void clear()  
    {  
        jtf1.setText("");
        jtf2.setText("");
        jpf.setText("");  
    }  
}
