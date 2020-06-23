package Controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import displayUI.ClientUI;
import displayUI.ConnectUI;
import displayUI.UserConfig;
import operation.MessageThread;
import user.User;

public class ClientController {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientController CC = new ClientController();
					CC.CU.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
//		ClientController CC = new ClientController();
//		new ClientUI();
	}
	//是否连接
	private boolean isConnect = false;
	private BufferedReader read;
	private PrintWriter write;
	private Socket socket;
	private MessageThread messageThread;
	public ClientUI CU;

	/*
	 * 初始化
	 */
	public ClientController() {
		CU = new ClientUI();
		UserConfig UserCf = new UserConfig();
		ConnectUI ConUI = new ConnectUI();

		// 用户设置按钮
		CU.btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				UserConfig c = new UserConfig();
				UserCf.setVisible(true);
			}
		});

		// 连接设置按钮
		CU.btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				ConnectUI c = new ConnectUI();
				ConUI.setVisible(true);
			}
		});
		// 登录按钮
		CU.btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

		// 注销
		CU.btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		});

		// 退出
		CU.btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		// 私聊
		CU.btnPrivate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendToOne();
			}
		});
		// 给文本框增加回车发送功能
		CU.textSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});

		// 单击发送
		CU.btnSend.setEnabled(false);
		CU.btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});

	}

	/**
	 * 
	 * @Description 发送信息
	 * @author Jason
	 * @date 2020年6月23号
	 */
	public synchronized void send() {
		if (!isConnect()) {
			JOptionPane.showMessageDialog(CU, "还没有连接服务器，无法发送消息！");
			return;
		}
		String message = CU.textSend.getText().trim();
		if (message == null || message.equals("")) {
			JOptionPane.showMessageDialog(CU, "消息不能为空");
			return;
		}
		sendMessage(CU.getTitle() + "@" + "ALL" + "@" + message);
		CU.textSend.setText(null);
	}

	public synchronized void sendToOne() {
		if (!isConnect()) {
			JOptionPane.showMessageDialog(CU, "还没有连接服务器，无法发送消息！");
			return;
		}
		String message = CU.textSend.getText().trim();
		if (message == null || message.equals("")) {
			JOptionPane.showMessageDialog(CU, "消息不能为空");
			return;
		}
		String name = CU.comboBox.getSelectedItem().toString();
		sendMessage(CU.getTitle() + "@" + "ONE" + "@" + message + "@" + name);
		CU.textSend.setText(null);
	}

	/**
	 * 
	 * @Description 登录操作
	 * @author Jason
	 * @date 2020年6月23号
	 */
	public void login() {
		int port = -1;
		if (isConnect()) {
			JOptionPane.showMessageDialog(CU, "已经处于连接状态，不能重复连接！");
			return;
		}
		try {
			try {
				port = Integer.parseInt(ConnectUI.portNumber.getText().trim());
//				System.out.println(port);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(CU, "输入的端口号不规范，要求为整数！");
			}

			String hostIp = ConnectUI.IpNumber.getText().trim();
			String name = UserConfig.textName.getText().trim();
			User user = new User(name, hostIp);

			if (hostIp.equals("") || name.equals("")) {
				JOptionPane.showMessageDialog(CU, "Ip地址和用户名都不能为空！");
				return;
			}

//			System.out.println(hostIp+" "+ name);
			boolean flag = connecServer(port, hostIp, name);
//			if (flag == false) {
//				JOptionPane.showMessageDialog(CU, "与服务器连接失败！");
//				return;
//			}
			
			

		} catch (Exception e) {
			JOptionPane.showMessageDialog(CU, e.toString());
		}
	}

	/**
	 * 
	 * @Description 连接服务器
	 * @author Jason
	 * @date 2020年6月23号
	 * @param port
	 * @param hostIp
	 * @param name
	 * @return
	 */
	public boolean connecServer(int port, String hostIp, String name) {
		try {
			socket = new Socket(hostIp, port); // 根据端口号号和服务器
			write = new PrintWriter(socket.getOutputStream());
			read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// 发送客户端的基本信息
			sendMessage(name + "@" + socket.getLocalAddress().toString());
			// 开启接收消息的线程
			messageThread = new MessageThread(this, read, write, socket);
			messageThread.start();
			//判断当前线程是否已经连接
			
			setConnect(true); // 状态改为：已连接
			return true;
		} catch (Exception e) {
			CU.textShow.append("与端口号为：" + port + ",   IP地址为：" + hostIp + "的服务器连接失败！\r\n");
			setConnect(false); // 状态为：未连接
			return false;
		}
	}

	/**
	 * 
	 * @Description 注销操作
	 * @author Jason
	 * @date 2020年6月23号
	 */
	public void logout() {

//		String hostIp = ConnectUI.IpNumber.getText().trim();
//		String name = UserConfig.textName.getText().trim();

		if (!isConnect()) {
			JOptionPane.showMessageDialog(CU, "已经是断开状态了哦！");
			return;
		}
		try {
			boolean flag = closeConnect(); // 断开连接
			if (!flag) {
				JOptionPane.showMessageDialog(CU, "断开连接发生异常！");
				return;
			}
			JOptionPane.showMessageDialog(CU, "断开成功！");

			CU.comboBox.removeAllItems();
			CU.comboBox.revalidate();

			CU.btnConnect.setEnabled(true);
			CU.btnUser.setEnabled(true);
			CU.btnLogin.setEnabled(true);
			CU.btnLogout.setEnabled(false);
			CU.textSend.setEditable(false);
			CU.btnSend.setEnabled(false);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(CU, e.toString());
		}

	}

	/**
	 * 
	 * @Description 客户端关闭
	 * @author Jason
	 * @date 2020年6月23号
	 * @return
	 */
	public synchronized boolean closeConnect() {
		try {
			sendMessage("CLOSE"); // 发送断开连接命令给服务器
			messageThread.stop(); // 停止接受消息的线程
			// 释放资源
			if (read != null) {
				read.close();
			}
			if (write != null) {
				write.close();
			}
			if (socket != null) {
				socket.close();
			}
			setConnect(false);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			setConnect(true);
			return false;
		}

	}

	/**
	 * 
	 * @Description 发送信息
	 * @author Jason
	 * @date 2020年6月23号
	 * @param message
	 */
	public synchronized void sendMessage(String message) {
		System.out.println(message);
		write.println(message);
		write.flush();
	}

	public boolean isConnect() {
		return isConnect;
	}

	public void setConnect(boolean isConnect) {
		this.isConnect = isConnect;
	}

	
}
