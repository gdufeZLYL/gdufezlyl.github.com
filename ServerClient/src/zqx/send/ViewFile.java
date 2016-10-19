package zqx.send;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.border.LineBorder;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class ViewFile extends JFrame implements DropTargetListener{
	
	private static final long serialVersionUID = 2525543203562010403L;
	private JButton btnSure;
	private JButton btnNot;
	private JButton btnOpen;
	private JRadioButton btnTop;
	private JRadioButton btnWord;
	private JRadioButton btnUser;
	private ButtonGroup btnGroup=new ButtonGroup();
	private JPanel pnlShow;
	private JProgressBar progressBar;
	private JLabel lblShow;
	private JLabel label;

	public ViewFile() {
		init();
		new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
	}

	public JLabel getLblShow() {
		return lblShow;
	}
	
	public void setLblShow(JLabel lblShow) {
		this.lblShow = lblShow;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public JRadioButton getBtnWord() {
		return btnWord;
	}

	public void setBtnWord(JRadioButton btnWord) {
		this.btnWord = btnWord;
	}

	public JRadioButton getBtnTop() {
		return btnTop;
	}

	public void setBtnTop(JRadioButton btnTop) {
		this.btnTop = btnTop;
	}

	public JRadioButton getBtnUser() {
		return btnUser;
	}

	public void setBtnUser(JRadioButton btnUser) {
		this.btnUser = btnUser;
	}

	private void init() {
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(64, 64, 64), 1, true), "这里选择你要的接收方式", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(0, 164, 407, 55);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		btnTop = new JRadioButton("桌面");
		btnTop.setBounds(10, 22, 82, 23);
		panel.add(btnTop);
		
		btnWord = new JRadioButton("我的文档");
		btnWord.setBounds(108, 22, 82, 23);
		panel.add(btnWord);
		
		btnUser = new JRadioButton("用户自定义位置");
		btnUser.setBounds(221, 22, 130, 23);
		panel.add(btnUser);
		
		btnGroup.add(btnTop);
		btnGroup.add(btnWord);
		btnGroup.add(btnUser);
				
		pnlShow = new JPanel();
		pnlShow.setBorder(new TitledBorder(new LineBorder(new Color(64, 64, 64), 1, true), "这里显示你已经选择的文件", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnlShow.setBounds(0, 0, 407, 138);
		getContentPane().add(pnlShow);
		pnlShow.setLayout(null);
		
		lblShow = new JLabel();
		lblShow.setVerticalAlignment(SwingConstants.TOP);
		lblShow.setBackground(Color.WHITE);
		lblShow.setBounds(10, 21, 387, 107);
		pnlShow.add(lblShow);
		
		btnOpen = new JButton("打开");
		btnOpen.setBounds(10, 229, 93, 23);
		getContentPane().add(btnOpen);
		btnOpen.addActionListener(new OpenAction(this));
		
		btnSure = new JButton("确定");
		btnSure.setBounds(211, 229, 93, 23);
		getContentPane().add(btnSure);
		btnSure.addActionListener(new SureAction(this));
		
		btnNot = new JButton("取消");
		btnNot.setBounds(310, 229, 93, 23);
		getContentPane().add(btnNot);
		btnNot.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					System.exit(1);
			}		
		});
		
		progressBar = new JProgressBar();
		progressBar.setBounds(92, 144, 311, 14);
		getContentPane().add(progressBar);
		
		label = new JLabel("传输进度");
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setBounds(10, 144, 66, 25);
		getContentPane().add(label);
		setSize(new Dimension(424, 300));
		setLocationRelativeTo(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("文件分发");
	}

	public void dragEnter(DropTargetDragEvent dtde) {
		
	}

	public void dragExit(DropTargetEvent dte) {
		
	}

	public void dragOver(DropTargetDragEvent dtde) {
		
	}

	@SuppressWarnings("unchecked")
	public void drop(DropTargetDropEvent dtde) {
		try {
			if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
				List<Object> list = (List<Object>) (dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
				Iterator<Object> iterator = list.iterator();
				while (iterator.hasNext()) {
					File file = (File) iterator.next();
					this.lblShow.setText(file.getAbsolutePath());
				}
				dtde.dropComplete(true);
				// this.updateUI();
			} else {
				dtde.rejectDrop();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedFlavorException e1) {
			e1.printStackTrace();
		}
	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
		
	}
	

}
