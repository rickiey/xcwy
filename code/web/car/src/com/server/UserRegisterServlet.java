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
 *车主注册
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/UserRegisterServlet")
public class UserRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserRegisterServlet() {
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
			String name = json.getString("name");
			String car = json.getString("car");
			String carnum = json.getString("carnum");
			System.out.println("register_mobile:" + mobile);
			System.out.println("register_password:" + password);
			System.out.println("register_name:" + name);
			System.out.println("register_car:" + car);
			System.out.println("register_carnum:" + carnum);

			Connection conn = null; // 当前的数据库连接
			PreparedStatement pst = null;// 向数据库发送sql语句
			ResultSet rs = null;// 结果集

			try {
				Class.forName(Constants.DRIVER);
				conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
						Constants.DB_PASSWORD);
				String sql = "select * from user_table where mobile='" + mobile + "'";
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery(sql);
				if (rs.next())// 账号已存在，不可注册
				{
					out.write("0");
				} else // 注册
				{
					sql = "insert into user_table (mobile,password) values(?,?)";
					pst = conn.prepareStatement(sql);
					pst.setString(1, mobile);
					pst.setString(2, password);
					pst.executeUpdate();
					sql = "insert into userinfo_table (mobile,name,car,carnum) values(?,?,?,?)";
					pst = conn.prepareStatement(sql);
					pst.setString(1, mobile);
					pst.setString(2, name);
					pst.setString(3, car);
					pst.setString(4, carnum);
					pst.executeUpdate();
					out.write("1");
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
