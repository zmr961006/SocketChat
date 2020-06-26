package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

import Controller.ServerController;

/**
 * 
 * @Description 连接mysql数据库
 * @author Jason
 * @date 2020年6月26号
 */
public class DAO {
	private static ServerController SC = new ServerController();
	private static Connection conn = null;
	public DAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
//			e.printStackTrace();
			JOptionPane.showMessageDialog(SC.SU, "数据库驱动加载失败" + e.getMessage());

		}
	}

	// 连接数据库
	public static Connection getConn() {
		try {
			String url="jdbc:mysql://192.168.1.2:3306/SocketChat?&characterEncoding=utf-8&autoReconnect=true&useSSL=false";   //127.0.0.1:3306/database
			conn = DriverManager.getConnection(url,"root","123456");
			return conn;
		}catch(Exception e){
			JOptionPane.showMessageDialog(SC.SU, "数据库连接失败" + e.getMessage());
			return null;
		}
	}
}
