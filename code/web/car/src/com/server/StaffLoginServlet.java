package com.server;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.utils.JsonReader;

import net.sf.json.JSONObject;

/**
 *员工登陆
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/StaffLoginServlet")
public class StaffLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public StaffLoginServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		// 读取数据
		try {
			PrintWriter out = response.getWriter();
			JSONObject json = JsonReader.read(request);
			String mobile = json.getString("mobile");
			String password = json.getString("password");
			System.out.println("StaffLoginMobile:" + mobile);
			System.out.println("StaffLoginPassword:" + password);

			Connection conn = null; // 当前的数据库连接
			PreparedStatement pst = null;// 向数据库发送sql语句
			ResultSet rs = null;// 结果集

			try {
				Class.forName(Constants.DRIVER);
				conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
						Constants.DB_PASSWORD);
				String sql = "select * from staff_table where mobile='" + mobile + "'";
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery(sql);
				if (rs.next())// 账号存在
				{
					String query_password = rs.getString(2);
					if (query_password.equals(password)) {
						System.out.println("员工登陆成功");
						out.write("1");
					} else {
						System.out.println("员工登陆:密码错误");
						out.write("0");
					}
				} else // 账号不存在
				{
					System.out.println("员工登陆:账号不存在");
					out.write("-1");
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
