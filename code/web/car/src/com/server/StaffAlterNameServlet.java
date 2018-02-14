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
 *员工修改姓名
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/StaffAlterNameServlet")
public class StaffAlterNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public StaffAlterNameServlet() {
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
			String name = json.getString("name");
			// System.out.println("register_mobile:"+mobile);
			// System.out.println("register_password:"+password);

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
				if (rs.next())// 找到账号
				{
					if (name.equals(rs.getString(3)))// 新旧姓名相同
					{
						out.write("0");
					} else// 修改姓名
					{
						sql = "update staff_table set name='" + name + "' where mobile='" + mobile
								+ "'";
						pst.executeUpdate(sql);
						out.write("1");
					}
				} else // 手机号码未注册,严重错误
				{
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
