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
 *商家录入员工信息
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/StaffRegisterServlet")
public class StaffRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public StaffRegisterServlet() {
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

		try {
			Class.forName(Constants.DRIVER);
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
					Constants.DB_PASSWORD);
			String sql = "select * from staff_table where mobile='" + mobile + "'";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery(sql);
			if (rs.next())// 账号已存在，不可注册
			{
				response.getWriter().write("0");
			} else // 注册
			{
				sql = "insert into staff_table (mobile,password,name,business_id) values(?,?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, mobile);
				pst.setString(2, password);
				pst.setString(3, name);
				pst.setString(4, id);
				pst.executeUpdate();
				response.getWriter().write("1");
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
