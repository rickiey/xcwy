package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.constants.Constants;

/**
 *商家登录
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null; // 当前的数据库连接
		PreparedStatement pst = null;// 向数据库发送sql语句
		ResultSet rs = null;// 结果集

		String id = request.getParameter("id");
		String password = request.getParameter("password");

		try {
			Class.forName(Constants.DRIVER);// 加载驱动程序
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
					Constants.DB_PASSWORD);// 连接数据库
			String sql = "select * from business_table where id='" + id + "'";
			pst = conn.prepareStatement(sql); // 创建Statement对象
			rs = pst.executeQuery(sql);
			if (rs.next()) {
				String query_password = rs.getString(2);
				System.out.println("id:" + id);
				System.out.println("query_password:" + query_password);
				System.out.println("password:" + password);

				if (query_password.equals(password)) {
					System.out.println("登陆成功");
					request.getSession().setAttribute("id", id);
					response.getWriter().write("1");
					// request.getRequestDispatcher("index.jsp").forward(request, response);
				} else {
					System.out.println("密码错误!");
					response.getWriter().write("-1");
				}
			} else {
				System.out.println("账号不存在!");
				response.getWriter().write("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally// 资源清理
		{
			try {
				rs.close(); // 关闭ResultSet结果集
			} catch (Exception e2) {
			}
			try {
				pst.close();// 关闭Statement对象
			} catch (Exception e3) {
			}
			try {
				conn.close();// 关闭数据库连接
			} catch (Exception e4) {
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
