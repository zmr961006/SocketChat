package Test;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import DAO.DBUtils;
import vo.Message;

/**
 * @Description 测试数据库连接
 * @author Jason
 *
 */
public class test {
	public static void main(String[] args) {
		List<String> result = new ArrayList<>();
		DBUtils.insert(new Message("jason","ni","ilov"));
		result = DBUtils.query("ni");
		System.out.println(result.toString());
		StringTokenizer st = new StringTokenizer(result.get(0), "/@");
		System.out.println("fname"+st.nextToken());
		System.out.println("tname"+st.nextToken());
		System.out.println("message"+st.nextToken());
	}
}
