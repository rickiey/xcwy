package com.server;

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
 *�̼ҽ��ܶ���
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/AcceptServlet")
public class AcceptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AcceptServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null; // ��ǰ�����ݿ�����
		PreparedStatement pst = null;// �����ݿⷢ��sql���
		ResultSet rs = null;// �����

		String business_id = (String) request.getSession().getAttribute("id");
		String orderid = request.getParameter("orderid");
		String service_condition = request.getParameter("service_condition");
		String state = request.getParameter("state");
		System.out.println("Accept_orderid:" + orderid);
		System.out.println("Accept_service_condition:" + service_condition);
		System.out.println("Accept_state:" + state);

		try {
			Class.forName(Constants.DRIVER);
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
					Constants.DB_PASSWORD);
			if (service_condition.equals("2")) {
				String sql = "update order_table set state=2 where id='" + orderid
						+ "' and business_id='" + business_id + "'";
				pst = conn.prepareStatement(sql);
				pst.executeUpdate();
				response.getWriter().write("1");
			} else if (service_condition.equals("1")) {
				String sql = "update order_table set state=1 where id='" + orderid
						+ "' and business_id='" + business_id + "'";
				pst = conn.prepareStatement(sql);
				pst.executeUpdate();
				response.getWriter().write("1");
			} else {
				response.getWriter().write("0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally// ��Դ����
		{
			try {
				rs.close(); // �ر�ResultSet�����
			} catch (Exception e2) {
			}
			try {
				pst.close();// �ر�Statement����
			} catch (Exception e3) {
			}
			try {
				conn.close();// �ر����ݿ�����
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
