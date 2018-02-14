package com.server;

import java.io.IOException;
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

/**
 *商家拒绝订单
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/RejectServlet")
public class RejectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RejectServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// String business_id = (String) request.getSession().getAttribute("id");
		String orderid = request.getParameter("orderid");
		String service_condition = request.getParameter("service_condition");
		String state = request.getParameter("state");
		System.out.println("orderid:" + orderid);
		System.out.println("service_condition:" + service_condition);
		System.out.println("state:" + state);

		Connection conn = null; // 当前的数据库连接
		PreparedStatement pst = null;// 向数据库发送sql语句
		ResultSet rs = null;// 结果集

		try {
			Class.forName(Constants.DRIVER);
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
					Constants.DB_PASSWORD);
			SimpleDateFormat timeend = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			// System.out.println(timeend.format(new Date()));
			String sql = "update order_table set state=-1,timeend='" + timeend.format(new Date())
					+ "' where id='" + orderid + "'";
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			sql = "update staff_table set state=1,order_id='0' where order_id='" + orderid + "'";
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			response.getWriter().write("1");
			// TODO弹窗通知
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
