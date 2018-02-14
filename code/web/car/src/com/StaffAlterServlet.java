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
 *商家修改员工信息
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/StaffAlterServlet")
public class StaffAlterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public StaffAlterServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null; // 当前的数据库连接
		PreparedStatement pst = null;// 向数据库发送sql语句
		ResultSet rs = null;// 结果集

		String mobile = request.getParameter("mobile");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String id = (String) request.getSession().getAttribute("id");
		System.out.println("mobile" + mobile);
		System.out.println("password" + password);
		System.out.println("name" + name);

		try {
			Class.forName(Constants.DRIVER);
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
					Constants.DB_PASSWORD);
			String sql = "update staff_table set password='" + password + "',name='" + name
					+ "' where mobile='" + mobile + "' and business_id='" + id + "'";
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			response.getWriter().write("1");
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
