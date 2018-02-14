package com.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.constants.Constants;
import com.utils.JsonReader;

import net.sf.json.JSONObject;

/**
 *车主提交订单
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/UserSubmitOrderServlet")
public class UserSubmitOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserSubmitOrderServlet() {
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
			String address = json.getString("address");
			String description = json.getString("description");
			String price = json.getString("price");
			String user_mobile = json.getString("user_mobile");
			String user_name = json.getString("user_name");
			String user_car = json.getString("user_car");
			String user_carnum = json.getString("user_carnum");
			String business_id = json.getString("business_id");
			String business_name = json.getString("business_name");
			String business_phone = json.getString("business_phone");
			String type = json.getString("type");
			String service_condition = json.getString("condition");
			String x = json.getString("X");
			String y = json.getString("Y");

			// SimpleDateFormat date_timestart = new SimpleDateFormat(timestart);

			float float_price = Float.parseFloat(price);
			// System.out.println("timestart:" + timestart);
			System.out.println("address:" + address);
			System.out.println("description:" + description);
			System.out.println("float_price:" + float_price);
			System.out.println("user_mobile:" + user_mobile);
			System.out.println("user_name:" + user_name);
			System.out.println("user_car:" + user_car);
			System.out.println("user_carnum:" + user_carnum);
			System.out.println("business_id:" + business_id);
			System.out.println("business_name:" + business_name);
			System.out.println("business_phone:" + business_phone);
			System.out.println("type:" + type);
			System.out.println("service_condition:" + service_condition);
			System.out.println("X:" + x);
			System.out.println("Y:" + y);

			Connection conn = null; // 当前的数据库连接
			PreparedStatement pst = null;// 向数据库发送sql语句
			ResultSet rs = null;// 结果集

			try {
				Class.forName(Constants.DRIVER);
				conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
						Constants.DB_PASSWORD);
				SimpleDateFormat timeend = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
				String sql = "insert into order_table (timestart,address,description,price,user_mobile,user_name,user_car,user_carnum,business_id,business_name,business_phone,type,state,service_condition,x,y) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, timeend.format(new Date()).toString());
				pst.setString(2, address);
				pst.setString(3, description);
				pst.setFloat(4, float_price); // 转
				pst.setString(5, user_mobile);
				pst.setString(6, user_name);
				pst.setString(7, user_car);
				pst.setString(8, user_carnum);
				pst.setString(9, business_id);
				pst.setString(10, business_name);
				pst.setString(11, business_phone);
				pst.setString(12, type);
				pst.setInt(13, 0);
				pst.setString(14, service_condition);
				pst.setString(15, x);
				pst.setString(16, y);
				pst.executeUpdate();
				out.write("1");
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
