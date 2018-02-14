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
 *商家修改密码
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/ModifyPasswordServlet")
public class ModifyPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ModifyPasswordServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null; // 当前的数据库连接
		PreparedStatement pst = null;// 向数据库发送sql语句
		ResultSet rs = null;// 结果集

		String id = (String) request.getSession().getAttribute("id");
		System.out.println(id);
		String oldpassword = request.getParameter("oldpassword");
		String password = request.getParameter("password");

		try {
			Class.forName(Constants.DRIVER);
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
					Constants.DB_PASSWORD);
			String sql = "select password from business_table where id='" + id + "'";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery(sql);
			if (rs.next()) {
				if (oldpassword.equals(rs.getString(1)))// 旧密码输入正确
				{
					if (oldpassword.equals(password))// 新旧密码相同
					{
						response.getWriter().write("-1");
					} else {
						sql = "update business_table set password='" + password + "' where id='"
								+ id + "'";
						pst.executeUpdate(sql);
						response.getWriter().write("1");
					}
				} else {
					response.getWriter().write("0");
				}
			} else {
				System.out.println("error!");
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
