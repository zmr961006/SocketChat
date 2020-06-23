package operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.DefaultListModel;
import javax.swing.JTextArea;

import Controller.ClientController;
import Controller.ServerController;
import user.User;

/**
 * 
 * @Description 服务器为客户端服务线程
 * @author Jason
 * @date 2020年6月23号 下午1:00:57
 */
public class ClientThread extends Thread {
	private Socket socket;
	private BufferedReader read;
	private PrintWriter write;
	private User user;
	private List<ClientThread> client;
	private DefaultListModel listModel;
	private JTextArea textArea;

	// 为用户提供输入输出流的getter方法
	public Socket getSocket() {
		return socket;
	}

	public BufferedReader getRead() {
		return read;
	}

	public PrintWriter getWrite() {
		return write;
	}

	public User getUser() {
		return user;
	}

	public ClientThread(Socket socket, List<ClientThread> client, DefaultListModel listModel, JTextArea textArea) {
		super();
		this.socket = socket;
		this.client = client;
		this.listModel = listModel;
		this.textArea = textArea;
		try {
			read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			write = new PrintWriter(socket.getOutputStream());
			// 接受用户信息
			String info = read.readLine();
			StringTokenizer st = new StringTokenizer(info, "@");
			user = new User(st.nextToken(), st.nextToken());
			
			// 将当前线程的用户添加到在线用户
			//	判断是否重复登录
			if (ServerController.onLineUser.containsKey(user.getName())) {
				write.println("FAILED@" + user.getName() + "@");
				write.flush();
			} else {
				ServerController.onLineUser.put(user.getName(), user);
//				for(String str:onLineUser.keySet()) {
//				System.out.println(str);
//			}
//				System.out.println(ClientController.onLineUser.size());

				// 反馈连接成功的信息
				write.println("SUCCESS@" + user.getName() + "@");
				write.flush();
				write.print("[系统通知] " + user.getName() + "与服务器连接成功！\r\n");
				write.flush();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		boolean flag = false;
		// 记录不是自己的条数
		int len = client.size();
		// 反馈当前所有在线用户信息
		try {
			if (client.size() > 0) {
				String temp = "";
				for (int i = client.size() - 1; i >= 0; i--) {
					// 不添加自己
//					System.out.println(client.get(i).getUser().getName());
//					System.out.println(user.getName());
					if (client.get(i).getUser().getName().equals(user.getName())) {
						len--;
						continue;
					}
					temp += (client.get(i).getUser().getName() + "/" + client.get(i).getUser().getIp()) + "@";
					flag = true;
				}
				if (flag) {
					client.get(client.size() - 1).getWrite().println("USERLIST@" + len + "@" + temp);
					client.get(client.size() - 1).getWrite().flush();
				}

			}

			// 向所有的用户发送该用户上线的消息
			for (int i = client.size() - 1; i >= 0; i--) {
				// 跳过自己
				if (client.get(i).getUser().getName().equals(user.getName())) {
					continue;
				}
				client.get(i).getWrite().println("ADD@" + user.getName());
				client.get(i).getWrite().flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 不断接受客户传来的消息，进行处理
	 */
	@Override
	public void run() {
		String message = null;
		while (true) {
			try {
				message = read.readLine(); // 接受消息
				if (message.equals("CLOSE")) { // 下线命令
					textArea.append("[系统通知] " + this.getUser().getName() + "下线!\r\n");
					// 断开连接的资源
					this.read.close();
					this.write.close();
					this.socket.close();

					// 向所有在线用户发送该用户下线的消息
					for (int i = client.size() - 1; i >= 0; i--) {
						client.get(i).getWrite().println("DELETE@" + this.getUser().getName());
						client.get(i).getWrite().flush();
					}

					listModel.removeElement(user.getName());

					// 删除此条客户端的服务线程
					for (int i = client.size() - 1; i >= 0; i--) {
						if (client.get(i).getUser() == user) {
							ClientThread temp = client.get(i);
							client.remove(i); // 删除此用户的服务线程
							temp.stop(); // 停止该条线程
							return;
						}
					}
				} else {
					sendMessage(message);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @Description 转发客户端发出的信息
	 * @author Jason
	 * @date 2020年6月23号
	 * @param message
	 */
	public void sendMessage(String message) {
		StringTokenizer st = new StringTokenizer(message, "@");// 用@作为分隔符分隔信息
		String name = st.nextToken(); // 按格式取，第一个用户名称
		String owner = st.nextToken(); // 判断群发还是私聊
		String contant = st.nextToken(); // 消息内容
		if (owner.equals("ALL")) { // 群发
			message = name + "说：" + contant;
			textArea.append(message + "\r\n");
			for (int i = client.size() - 1; i >= 0; i--) {
				client.get(i).getWrite().println(message); // 获取用户的输出流并打印信息
				client.get(i).getWrite().flush(); // 清空缓存区
			}
		} else if (owner.equals("ONE")) {// 私聊
			String privateName = st.nextToken(); // 获取私聊对象的名字
			System.out.println(privateName);
			System.out.println(contant);
			for (int i = client.size() - 1; i >= 0; i--) {
				System.out.print(client.get(i).getUser().getName() + " ");
				if (client.get(i).getUser().getName().equals(privateName)) {
					System.out.println("私聊");
					message = "ONE@" + name + "悄悄对你说：" + contant;
					System.out.println("私聊信息：" + message);
					client.get(i).getWrite().println(message);
					client.get(i).getWrite().flush();
				}
			}
		}
	}
}