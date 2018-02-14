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
 *车主取消订单
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/UserCancelOrderServlet")
public class UserCancelOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserCancelOrderServlet() {
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
			String id = json.getString("id");
			System.out.println("UserCancelOrderServlet_mobile:" + mobile);
			System.out.println("UserCancelOrderServlet_mobile:" + id);

			Connection conn = null; // 当前的数据库连接
			PreparedStatement pst = null;// 向数据库发送sql语句
			ResultSet rs = null;// 结果集

			try {
				Class.forName(Constants.DRIVER);
				conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
						Constants.DB_PASSWORD);
				SimpleDateFormat timeend = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
				String sql = "update order_table set state=2,timeend='" + timeend.format(new Date())
						+ "' where id='" + id + "'";
				pst = conn.prepareStatement(sql);
				pst.executeUpdate();
				sql = "update staff_table set state=1,order_id='0' where order_id='" + id + "'";
				pst = conn.prepareStatement(sql);
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
