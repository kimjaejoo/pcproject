package kr.co.jaejoo.reProject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class CatchMindStingleton {

	String url = "jdbc:mysql://jsptest.cpkkponxntb7.ap-northeast-2.rds.amazonaws.com:3306/pcproject"; // 뒤의
																										// 3306은
																										// 생략이
																										// 가능하나
																										// 써주는것이
																										// 좋습니다.

	String driver = "com.mysql.jdbc.Driver";

	String user = "test";

	String password = "test1234";

	String dbName = "pcproject";

	private static CatchMindStingleton cms = null;

	private Connection conn = null;

	public CatchMindStingleton() {
		System.out.println("singleton연결시도");
	}

	public static CatchMindStingleton getInstance() {
		if (cms == null) {
			cms = new CatchMindStingleton();
		}
		return cms;
	}

	public Connection getConnection() {
		if (conn == null) {
			try {
				Class.forName(driver);
				DriverManager.getConnection(url, user, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

}
