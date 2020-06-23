package Controller;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import displayUI.PortConfig;
import displayUI.ServerUI;
import operation.ClientThread;
import operation.ServerThread;

public class ServerController {
	private boolean isStart = false;
	private List<ClientThread> client = null;
	private ServerSocket serverSocket;
	private ServerThread serverThread;
	private ServerUI SU;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerController SC = new ServerController();
					SC.SU.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * 初始化
	 */

	public ServerController() {
		SU = new ServerUI();
		PortConfig pc = new PortConfig();
		
		
		// 端口设置
		SU.btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				PortConfig pc = new PortConfig();
				pc.setVisible(true);
			}
		});

		// 启动服务
		SU.btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});

		// 停止服务
		SU.btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});

		// 退出
		SU.btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// 给文本框增加回车发送功能
		SU.txt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send();
			}
		});

		// 单击发送
		SU.btnSend.setEnabled(false);
		SU.btnSend.addActionListener(new ActionListener() {
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
		if (!isStart) {
			JOptionPane.showMessageDialog(SU, "服务器还未启动，请先启动服务器");
			return;
		}
		if (client.size() == 0) {
			JOptionPane.showMessageDialog(SU, "没有用户在线，不能发送消息！");
			return;
		}
		String message = SU.txt.getText().trim(); // 去掉字符串头部和尾部的空字符串
		if (message == null || message.equals("")) {
			JOptionPane.showMessageDialog(SU, "消息不能为空！");
			return;
		}
		sendServerMessage(message); // 群发消息
		SU.textArea.append("[系统通知] " + SU.txt.getText() + "\r\n");
		SU.txt.setText(null);
	}

	/**
	 * 
	 * @Description 服务器群发信息
	 * @author Jason
	 * @date 2020年6月23号
	 * @param message
	 */
	public synchronized void sendServerMessage(String message) {
		for (int i = client.size() - 1; i >= 0; i--) {
			client.get(i).getWrite().println("[系统通知] " + message);
			client.get(i).getWrite().flush();
		}
	}

	/**
	 * 
	 * @Description 从端口设置PortConfig里获取textPort里的端口值进行启动服务
	 * @author Jason
	 * @date 2020年6月23号
	 */
	public void start() {
		if (isStart) {
			JOptionPane.showMessageDialog(SU, "服务器已经启动过啦！");
			return;
		}
		int port = -1;
		try {
			port = Integer.parseInt(PortConfig.textPort.getText());
			if (port < 0) {
				JOptionPane.showMessageDialog(SU, "端口号为正整数！");
				return;
			}
			if(!serverStart(port)) {return ;}
			SU.textArea.append("服务器已启动！端口号：" + port + "\r\n");
			JOptionPane.showMessageDialog(SU, "服务器启动成功！");

			SU.btnConnect.setEnabled(false);
			SU.btnUser.setEnabled(false);
			SU.btnStop.setEnabled(true);
			SU.txt.setEditable(true);
			SU.btnSend.setEnabled(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(SU, "端口号为正整数！");
		}
	}

	/**
	 * 
	 * @Description 服务器停止服务
	 * @author Jason
	 * @date 2020年6月23号
	 */
	public void stop() {
		if (!isStart) {
			JOptionPane.showMessageDialog(SU, "服务器还未启动，无需停止！");
			return;
		}
		try {
			closeServer();
			SU.btnConnect.setEnabled(true);
			SU.btnUser.setEnabled(true);
			SU.btnStop.setEnabled(false);
			SU.txt.setEditable(false);
			SU.btnSend.setEnabled(false);

			SU.textArea.append("服务器已成功停止！\r\n");
			JOptionPane.showMessageDialog(SU, "服务器已停止！");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(SU, "停止服务器发生异常!");
		}
	}

	/**
	 * 
	 * @Description 服务器启动服务
	 * @author Jason
	 * @date 2020年6月23号
	 * @param port
	 */
	public boolean serverStart(int port) {
		client = new ArrayList<ClientThread>(); // 放客户端线程
		try {
			serverSocket = new ServerSocket(port);
			serverThread = new ServerThread(serverSocket, SU, client, SU.listModel, SU.textArea);
			serverThread.start();
			isStart = true;
		} catch (BindException e) {
			isStart = false;
			JOptionPane.showMessageDialog(SU, "端口号被占用！");
		} catch (Exception e) {
			e.printStackTrace();
			isStart = false;
			JOptionPane.showMessageDialog(SU, "服务器启动异常");
		}
		return isStart == true;
	}

	/**
	 * 
	 * @Description 服务器关闭
	 * @author Jason
	 * @date 2020年6月23号
	 */
	public void closeServer() {
		try {
			if (serverThread != null) {
				serverThread.stop(); // 停止服务线程
			}

			// 发布给所有在线用户下线的消息
			for (int i = client.size() - 1; i >= 0; i--) {
				client.get(i).getWrite().println("CLOSE");
				client.get(i).getWrite().flush();
				// 释放资源
				client.get(i).stop(); // 停止此条为客户服务的线程
				client.get(i).getRead().close();
				client.get(i).getWrite().close();
				client.get(i).getSocket().close();
				client.remove(i);
			}
			// 关闭服务器连接
			if (serverSocket != null) {
				serverSocket.close();
			}

			SU.listModel.removeAllElements(); // 清空用户列表
			isStart = false;
		} catch (IOException e) {
			e.printStackTrace();
			isStart = true;
		}
	}

}
