package displayUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import operation.ClientThread;
import operation.ServerThread;

public class ServerUI extends JFrame {

	public JPanel contentPane;
	public JTextField txt;
	public JButton btnUser;
	public JButton btnConnect;
	public JButton btnStop;
	public JButton btnExit;
	public JButton btnSend;
	public JButton btnHelp;
	public JTextArea textArea;
	public DefaultListModel listModel;
	public JList userList;

	/**
	 * Create the frame.
	 */
	public ServerUI() {
		setResizable(false);
		setBackground(Color.GRAY);
		setTitle("ÁÄÌìÊÒ·þÎñ¶Ë");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.white);
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

		btnUser = new JButton("¶Ë¿ÚÉèÖÃ");
		btnUser.setBackground(Color.white);
		btnUser.setVerticalAlignment(SwingConstants.BOTTOM);
		btnUser.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 16));
		panel.add(btnUser);

		btnConnect = new JButton("Æô¶¯·þÎñ");
		btnConnect.setBackground(Color.white);
		btnConnect.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 16));
		panel.add(btnConnect);

		JLabel label = new JLabel(" ");
		menu_panel.add(label);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.white);
		panel_1.setBorder(new CompoundBorder());
		menu_panel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnStop = new JButton("Í£Ö¹·þÎñ");
		btnStop.setBackground(Color.white);
		btnStop.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 16));
		panel_1.add(btnStop);

		JLabel label_1 = new JLabel(" ");
		panel_1.add(label_1);

		btnExit = new JButton("ÍË³ö");
		panel_1.add(btnExit);
		btnExit.setBackground(Color.white);
		btnExit.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 16));

		JPanel message_panel = new JPanel();
		message_panel.setBackground(Color.white);
		contentPane.add(message_panel, BorderLayout.SOUTH);
		message_panel.setLayout(new GridLayout(2, 1, 0, 3));

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.white);
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		panel_3.setBorder(new CompoundBorder());
		message_panel.add(panel_3);

		JLabel send = new JLabel("·¢ËÍÐÅÏ¢£º");
		panel_3.add(send);
		send.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 16));

		txt = new JTextField();
		txt.setEditable(false);
		txt.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 14));
		txt.setBackground(new Color(255, 255, 255));
		txt.setHorizontalAlignment(SwingConstants.LEFT);
		panel_3.add(txt);
		txt.setColumns(20);

//		JLabel label_2 = new JLabel("     ");
//		panel_3.add(label_2);

		// µ¥»÷·¢ËÍ
		btnSend = new JButton("·¢ËÍ");

		btnSend.setBackground(Color.white);
		btnSend.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 16));
		panel_3.add(btnSend);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new CompoundBorder());
		panel_4.setBackground(Color.white);
		message_panel.add(panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 1));

		JPanel user_panel = new JPanel();
		user_panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.add(user_panel, BorderLayout.CENTER);
		user_panel.setLayout(new BorderLayout(0, 0));

		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 14));
		JScrollPane scrollPane = new JScrollPane();
		textArea.setBounds(23, 217, 650, 266);
		scrollPane.setViewportView(textArea);
		user_panel.add(scrollPane);

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPane.setRowHeaderView(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));

		JLabel label_3 = new JLabel("ÔÚÏßÓÃ»§");
		label_3.setBackground(new Color(255, 255, 255));
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 18));
		panel_5.add(label_3, BorderLayout.NORTH);

		listModel = new DefaultListModel();
		userList = new JList(listModel);

		userList.setForeground(new Color(0, 0, 0));
		userList.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 12));
		panel_5.add(userList, BorderLayout.CENTER);

	}

}