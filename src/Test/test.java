package Test;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import DAO.DBUtils;

/**
 * @Description 测试数据库连接
 * @author Jason
 *
 */
public class test {
	public static void main(String[] args) {
		List<String> result = new ArrayList<>();
		result = DBUtils.query("Jason");
		System.out.println(result.toString());
		StringTokenizer st = new StringTokenizer(result.get(0), "/@");
		System.out.println("fname"+st.nextToken());
		System.out.println("tname"+st.nextToken());
		System.out.println("message"+st.nextToken());
	}
}
