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
 *员工查询订单信息
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/StaffCheckOrderServlet")
public class StaffCheckOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public StaffCheckOrderServlet() {
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
			System.out.println("StaffCheckOrder_mobile:" + mobile);

			Connection conn = null; // 当前的数据库连接
			PreparedStatement pst = null;// 向数据库发送sql语句
			ResultSet rs = null, rs2 = null;// 结果集

			try {
				Class.forName(Constants.DRIVER);
				conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
						Constants.DB_PASSWORD);
				String sql = "select * from staff_table where state=0 and mobile='" + mobile + "'";
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery(sql);
				if (rs.next())// 找到账号
				{
					String temp_id = rs.getString(6);
					int order_id = Integer.parseInt(temp_id);
					sql = "select * from order_table where id=" + order_id;
					// System.out.println("query order_table"+sql);
					pst = conn.prepareStatement(sql);
					rs2 = pst.executeQuery(sql);
					if (rs2.next()) {
						String id = rs2.getString(1);
						String timestart = rs2.getString(2);
						String address = rs2.getString(4);
						String description = rs2.getString(5);
						String price = rs2.getString(6);
						String user_mobile = rs2.getString(7);
						String user_name = rs2.getString(8);
						String user_car = rs2.getString(9);
						String user_carnum = rs2.getString(10);
						String type = rs2.getString(14);
						String x = rs2.getString(17);
						String y = rs2.getString(18);
						JSONObject jsonObj = new JSONObject();
						jsonObj.put("id", id);
						jsonObj.put("timestart", timestart);
						jsonObj.put("address", address);
						jsonObj.put("description", description);
						jsonObj.put("price", price);
						jsonObj.put("user_mobile", user_mobile);
						jsonObj.put("user_name", user_name);
						jsonObj.put("user_car", user_car);
						jsonObj.put("user_carnum", user_carnum);
						jsonObj.put("type", type);
						jsonObj.put("x", x);
						jsonObj.put("y", y);
						String res = jsonObj.toString();
						System.out.println("StaffCheckOrder" + res);
						// System.out.println(res);
						out.write(res);
					}
				} else {
					out.write("");
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
