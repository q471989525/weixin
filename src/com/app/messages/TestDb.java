package com.app.messages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDb {

	public static void main(String[] args) {

		Connection conn = getOrclConnection(); // 同样先要获取连接，即连接到数据库
		try {
			String sql = "select * from SYS_ROLE_RESOURCE t"; // 查询数据的sql语句
			Statement st = conn.createStatement(); // 创建用于执行静态sql语句的Statement对象，st属局部变量
			ResultSet rs = st.executeQuery(sql); // 执行sql查询语句，返回查询数据的结果集
			while (rs.next()) { // 判断是否还有下一个数据

				String RESOURCEID = rs.getString("RESOURCEID");
				String ROLE_ID = rs.getString("ROLE_ID");
				String RESOURCE_ID = rs.getString("RESOURCE_ID");

//				System.out.println(RESOURCEID + " " + PARENT_ID + " " + REGION_NAME + " " + REGION_TYPE + " " +ORDER_BY);
				
				String insertSql = "INSERT INTO SYS_ROLE_RESOURCE(RESOURCEID, ROLE_ID, RESOURCE_ID) "
						+ "VALUES ('"+RESOURCEID+"', '"+ROLE_ID+"','"+RESOURCE_ID+"');";  // 插入数据的sql语句 
				System.out.println(insertSql);
			}

		} catch (SQLException e) {
			System.out.println("查询数据失败");
		}

		// getMySqlConnection();
	}

	public static Connection getMySqlConnection() {
		Connection con = null; // 创建用于连接数据库的Connection对象
		try {
			Class.forName("com.mysql.jdbc.Driver");// 加载Mysql数据驱动
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/app3", "root", "zhoufeng");// 创建数据连接
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return con; // 返回所建立的数据库连接
	}

	public static Connection getOrclConnection() {
		Connection con = null; // 创建用于连接数据库的Connection对象
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Mysql数据驱动
			con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "zf", "zhoufeng");// 创建数据连接
		} catch (Exception e) {
			System.out.println("数据库连接失败" + e.getMessage());
		}
		return con; // 返回所建立的数据库连接
	}

}
