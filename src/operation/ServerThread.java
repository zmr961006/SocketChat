package operation;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JTextArea;

import displayUI.ServerUI;

/**
 * 
 * @Description 服务器监听线程
 * @author Jason
 * @date 2020年6月23号 
 */
public class ServerThread extends Thread {
	private ServerSocket serverSocket;
	private ServerUI SU;
	private List<ClientThread> client;
	private JTextArea textArea;
	private DefaultListModel listModel;

	public ServerThread(ServerSocket serverSocket, ServerUI SU, List<ClientThread> client,
			DefaultListModel listModel, JTextArea textArea) {
		this.serverSocket = serverSocket;
		this.SU = SU;
		this.client = client;
		this.listModel = listModel;
		this.textArea = textArea;
	}

	@Override
	public void run() {
		// 不停地等待客户连接
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				ClientThread clients = new ClientThread(socket, client, listModel, textArea);
				client.add(clients);
//				System.out.println(client.size());
				listModel.addElement(clients.getUser().getName()); // 更新在线列表


				clients.start(); // 开启客户端服务线程
				clients.update(); //更新用户列表
				textArea.append("[系统通知] " + clients.getUser().getName() + "上线了！\r\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
