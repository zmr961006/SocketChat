package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Controller.ServerController;
import vo.Message;

/**
 * @Description 数据库的增删改查
 * @author Jason
 * 
 *
 */
public class DBUtils {
	private static ServerController SC = new ServerController();

	// 插入数据
	public static void insert(Message M) {
		try {
			Connection conn = DAO.getConn();
			PreparedStatement ps = conn
					.prepareStatement("insert into outlineMessage(fname,tname,message)values(?,?,?)");
			ps.setString(1, M.getFname());
			ps.setString(2, M.getTname());
			ps.setString(3, M.getMessage());

			int f = ps.executeUpdate();
			if (f > 0) {
//				JOptionPane.showMessageDialog(SC.SU, "插入数据成功");
			} else {
				JOptionPane.showMessageDialog(SC.SU, "插入数据失败");
			}

			// 释放资源
			// 可使用数据库连接池改造
			ps.close();
			conn.close();
		} catch (Exception e) {
//			JOptionPane.showMessageDialog(SC.SU, "连接失败！");
			e.printStackTrace();
		}
	}

	// 更新数据
	// 聊天室系统暂时不需要此功能
	@Deprecated
	public static void update(Message M) {

	}

	public static void delete(String tname) {
		try {
			Connection conn = DAO.getConn();
			PreparedStatement ps = conn.prepareStatement("delete from outlineMessage where tname=?");
			ps.setString(1, tname);
			int f = ps.executeUpdate();
			if (f > 0) {
//				JOptionPane.showMessageDialog(SC.SU, "删除数据成功");
			} else {
				JOptionPane.showMessageDialog(SC.SU, "删除数据失败");
			}
			ps.close();
			conn.close();

		} catch (Exception e) {
			// TODO: handle exception
//			JOptionPane.showMessageDialog(SC.SU, "连接失败");
			e.printStackTrace();
		}
	}

	//根据接收人查询
	public static List<String> query(String name) {
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DAO.getConn();
			PreparedStatement ps = conn.prepareStatement("select * from outlineMessage where tname=?");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String fname = rs.getString("fname");
				String tname = rs.getString("tname");
				String message = rs.getString("message");
				result.add(fname + "@" + tname + "@" + message);
			}

			ps.close();
			conn.close();

		} catch (Exception e) {
//			JOptionPane.showMessageDialog(SC.SU, "连接失败");
			e.printStackTrace();
		}
		return result;
	}
}
