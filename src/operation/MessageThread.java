package operation;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import Controller.ClientController;
import displayUI.ClientUI;
import user.User;

/**
 * 
 * @Description 客户端自用的消息传递接受线程
 * @author Haomu
 * @date 2019年12月1日 下午1:00:57
 */
public class MessageThread extends Thread {
	private BufferedReader read;
	private PrintWriter write;
	private Socket socket;
	private ClientController CC;

	// 接收消息线程的构造方法
	public MessageThread(ClientController CC, BufferedReader read, PrintWriter write, Socket socket) {
		super();
		this.CC = CC;
		this.read = read;
		this.write = write;
		this.socket = socket;
	}

	/**
	 * 
	 * @Description 客户端意外关闭
	 * @author Haomu
	 * @date 2019年12月1日 下午4:52:36
	 * @throws Exception
	 */
	public synchronized void closeConnect() throws Exception {
		// 清空用户列表
		CC.CU.comboBox.removeAllItems();

		// 被动关闭连接释放资源
		if (read != null) {
			read.close();
		}
		if (write != null) {
			write.close();
		}
		if (socket != null) {
			socket.close();
		}
		CC.setConnect(false); // 将状态改为未连接状态
		CC.CU.btnConnect.setEnabled(true);
		CC.CU.btnUser.setEnabled(true);
		CC.CU.btnLogin.setEnabled(true);
		CC.CU.btnLogout.setEnabled(false);
		CC.CU.textSend.setEditable(false);
		CC.CU.btnSend.setEnabled(false);
	}

	@Override
	public void run() { // 不断接受消息
		String message = "";
		while (true) {
			try {
				message = read.readLine();
				System.out.println(message);
				StringTokenizer st = new StringTokenizer(message, "/@");
				String command = st.nextToken();
				System.out.println("cm"+command);
				if (command.equals("CLOSE")) { // 关闭命令
					CC.CU.textShow.append("服务器已关闭！\r\n");
					closeConnect(); // 被动关闭连接
					return; // 结束线程
				} else if (command.equals("ADD")) { // 有用户上线更新列表
					String userName = "";
					String userIp = "";
					if ((userName = st.nextToken()) != null) {
						User user = new User(userName, userIp);
						CC.onLineUser.put(userName, user);
						CC.CU.comboBox.addItem(userName);
						CC.CU.comboBox.revalidate();
					}
					CC.CU.textShow.append("[系统通知] " + userName + "上线了！\r\n");
				} else if (command.equals("DELETE")) { // 有用户下线更新列表
					String userName = st.nextToken();
					User user = (User) CC.onLineUser.get(userName);
					CC.onLineUser.remove(userName);

					CC.CU.comboBox.removeItem(userName);
					CC.CU.comboBox.revalidate();
					CC.CU.textShow.append("[系统通知] " + userName + "下线了！\r\n");
				} else if (command.equals("USERLIST")) { // 更新用户列表
					int size = Integer.parseInt(st.nextToken());
					String userName = null;
					String userIp = null;
					for (int i = 0; i < size; i++) {
						userName = st.nextToken();
						userIp = st.nextToken();
						User user = new User(userName, userIp);
						CC.onLineUser.put(userName, user);
						CC.CU.comboBox.addItem(userName);
						CC.CU.comboBox.revalidate();
					}
				}
					else if(command.equals("ONE")){
						String msg = st.nextToken();
//						CC.CU.textShow.setForeground(Color.blue);
						CC.CU.textShow.append(msg + "\r\n");
					}
				else { // 普通消息
					CC.CU.textShow.append(message + "\r\n");
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}