package displayUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import operation.MessageThread;
import vo.User;

public class ClientUI extends JFrame {

	public JPanel contentPane;
	public JTextField textSend;
	public JTextPane textShow;
	public JButton btnLogin;
	public JButton btnLogout;
	public JButton btnUser;
	public JButton btnConnect;
	public JButton btnSend;
	public JButton btnExit;
	public JComboBox comboBox;
	public JComboBox outlineBox;
	public JButton btnPrivate;

	/**
	 * Create the frame.
	 */
	public ClientUI() {
		setResizable(false);
		setBackground(Color.GRAY);
		setTitle("聊天室客户端");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel menu_panel = new JPanel();
		menu_panel.setBackground(Color.white);
		menu_panel.setBorder(new CompoundBorder());
		contentPane.add(menu_panel, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setBorder(new CompoundBorder());
		menu_panel.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		btnUser = new JButton("用户设置");

//		btnUser.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				UserConfig c = new UserConfig();
//				c.setVisible(true);
//			}
//		});
		btnUser.setBackground(Color.white);
		btnUser.setVerticalAlignment(SwingConstants.BOTTOM);
		btnUser.setFont(new Font("微软雅黑", Font.BOLD, 16));
		panel.add(btnUser);

		btnConnect = new JButton("连接设置");
//		btnConnect.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				ConnectUI c = new ConnectUI();
//				c.setVisible(true);
//			}
//		});
		btnConnect.setBackground(Color.white);
		btnConnect.setFont(new Font("微软雅黑", Font.BOLD, 16));
		panel.add(btnConnect);

		JLabel label = new JLabel(" ");
		menu_panel.add(label);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.white);
		panel_1.setBorder(new CompoundBorder());
		menu_panel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnLogin = new JButton("登录");
//		btnLogin.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				login();
//			}
//		});
		btnLogin.setBackground(Color.white);
		btnLogin.setFont(new Font("微软雅黑", Font.BOLD, 16));
		panel_1.add(btnLogin);

		btnLogout = new JButton("注销");
//		btnLogout.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				logout();
//			}
//		});
		btnLogout.setBackground(Color.white);
		btnLogout.setFont(new Font("微软雅黑", Font.BOLD, 16));
		btnLogout.setEnabled(false);
		panel_1.add(btnLogout);

		JLabel label_1 = new JLabel(" ");
		panel_1.add(label_1);

		btnExit = new JButton("退出");
//		btnExit.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				System.exit(0);
//			}
//		});
		panel_1.add(btnExit);
		btnExit.setBackground(Color.white);
		btnExit.setFont(new Font("微软雅黑", Font.BOLD, 16));

		JPanel message_panel = new JPanel();
		message_panel.setBackground(Color.white);
		contentPane.add(message_panel, BorderLayout.SOUTH);
		message_panel.setLayout(new GridLayout(3, 1, 0, 3));

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.white);
		panel_2.setBorder(new CompoundBorder());
		message_panel.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		//在线人员选项
		comboBox = new JComboBox();
		comboBox.addItem("所有人");
		comboBox.setSelectedIndex(0);
		comboBox.setFont(new Font("微软雅黑", Font.BOLD, 14));
		panel_2.add(comboBox);

		JLabel label_3 = new JLabel("                     ");
		panel_2.add(label_3);
		//离线人员选项
		outlineBox = new JComboBox();
		outlineBox.addItem("已离线");
		outlineBox.setSelectedIndex(0);
		outlineBox.setFont(new Font("微软雅黑", Font.BOLD, 14));
		panel_2.add(outlineBox);
		JLabel label_4 = new JLabel("                     ");
		panel_2.add(label_4);
		
		
		btnPrivate = new JButton("私聊");
		btnPrivate.setBackground(Color.white);
		btnPrivate.setFont(new Font("微软雅黑", Font.BOLD, 14));
		panel_2.add(btnPrivate);
		
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.white);
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		panel_3.setBorder(new CompoundBorder());
		message_panel.add(panel_3);

		JLabel send = new JLabel("发送信息：");
		panel_3.add(send);
		send.setFont(new Font("微软雅黑", Font.BOLD, 16));

		textSend = new JTextField();
		textSend.setEditable(false);
		textSend.setFont(new Font("微软雅黑", Font.BOLD, 14));
		textSend.setHorizontalAlignment(SwingConstants.LEFT);
		panel_3.add(textSend);
		textSend.setColumns(20);
//		// 给文本框增加回车发送功能
//		textSend.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				send();
//			}
//		});

		JLabel label_2 = new JLabel("     ");
		panel_3.add(label_2);

//		// 单击发送
		btnSend = new JButton("发送");
		btnSend.setEnabled(false);
//		btnSend.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				send();
//			}
//		});
		btnSend.setBackground(Color.GRAY);
		btnSend.setFont(new Font("微软雅黑", Font.BOLD, 16));
		panel_3.add(btnSend);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new CompoundBorder());
		panel_4.setBackground(Color.white);
		message_panel.add(panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel user_panel = new JPanel();
		user_panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(user_panel, BorderLayout.CENTER);
		user_panel.setLayout(new BorderLayout(0, 0));
		
		
		/*
		 * 使用JTextPane代替JTextArea实现变色
		 */
		textShow = new JTextPane();
		Style base = textShow.getStyledDocument().addStyle(null, null);
		//设置两个风格，一个正常颜色，一个红色
		Style normal = textShow.addStyle("normal", base);
        Style color = textShow.addStyle("red", base);
		StyleConstants.setForeground(color, Color.RED);
//		textShow.setEditable(false);
		textShow.setFont(new Font("微软雅黑", Font.BOLD, 14));
		JScrollPane scrollPane = new JScrollPane(textShow);
		user_panel.add(scrollPane);
		scrollPane.setBounds(23, 217, 650, 266);
		textShow.setBounds(23, 217, 650, 266);
		scrollPane.setViewportView(textShow);
	}

	

}