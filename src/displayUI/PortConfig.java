package displayUI;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class PortConfig extends JFrame implements ActionListener{

	private JPanel contentPane;
	public static JTextField textPort;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PortConfig frame = new PortConfig();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PortConfig() {
		setResizable(false);
		setBackground(Color.GRAY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 160);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		contentPane.add(panel);
		
		JLabel label = new JLabel("ÇëÊäÈëÕìÌýµÄ¶Ë¿ÚºÅ£º");
		label.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 16));
		panel.add(label);
		
		textPort = new JTextField("50000");
		textPort.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 16));
		panel.add(textPort);
		textPort.setColumns(7);
//		
//		JPanel panel_1 = new JPanel();
//		panel_1.setBackground(Color.PINK);
//		contentPane.add(panel_1);
		
//		JLabel label_1 = new JLabel("Ä¬ÈÏ¶Ë¿ÚºÅÎª£º9343");
//		label_1.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 16));
//		panel_1.add(label_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.white);
		contentPane.add(panel_2);
		
		JButton btnSave = new JButton("±£´æ");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PortConfig.this.dispose();
			}
		});
		btnSave.setBackground(Color.white);
		btnSave.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 16));
		panel_2.add(btnSave);
		
		JButton btnCancle = new JButton("È¡Ïû");
		btnCancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PortConfig.this.dispose();
			}
		});
		btnCancle.setBackground(Color.white);
		btnCancle.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 16));
		panel_2.add(btnCancle);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}