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
 *�̼��ٻ�Ա��
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/WithdrawStaffServlet")
public class WithdrawStaffServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public WithdrawStaffServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null; // ��ǰ�����ݿ�����
		PreparedStatement pst = null;// �����ݿⷢ��sql���
		ResultSet rs = null;// �����

		String mobile = request.getParameter("mobile");
		String id = (String) request.getSession().getAttribute("id");
		System.out.println("WithdrawStaff_mobile:" + mobile);
		// System.out.println("WithdrawStaff_business_id:" + id);

		try {
			Class.forName(Constants.DRIVER);
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
					Constants.DB_PASSWORD);
			String sql = "update staff_table set state=1,order_id='0' where mobile='" + mobile
					+ "' and business_id='" + id + "'";
			pst = conn.prepareStatement(sql);
			pst.executeUpdate();
			response.getWriter().write("1");
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
