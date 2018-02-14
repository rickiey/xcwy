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
 *�̼�¼��Ա����Ϣ
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
		Connection conn = null; // ��ǰ�����ݿ�����
		PreparedStatement pst = null;// �����ݿⷢ��sql���
		ResultSet rs = null;// �����

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
			if (rs.next())// �˺��Ѵ��ڣ�����ע��
			{
				response.getWriter().write("0");
			} else // ע��
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