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
 *�̼ҵ�¼
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null; // ��ǰ�����ݿ�����
		PreparedStatement pst = null;// �����ݿⷢ��sql���
		ResultSet rs = null;// �����

		String id = request.getParameter("id");
		String password = request.getParameter("password");

		try {
			Class.forName(Constants.DRIVER);// ������������
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
					Constants.DB_PASSWORD);// �������ݿ�
			String sql = "select * from business_table where id='" + id + "'";
			pst = conn.prepareStatement(sql); // ����Statement����
			rs = pst.executeQuery(sql);
			if (rs.next()) {
				String query_password = rs.getString(2);
				System.out.println("id:" + id);
				System.out.println("query_password:" + query_password);
				System.out.println("password:" + password);

				if (query_password.equals(password)) {
					System.out.println("��½�ɹ�");
					request.getSession().setAttribute("id", id);
					response.getWriter().write("1");
					// request.getRequestDispatcher("index.jsp").forward(request, response);
				} else {
					System.out.println("�������!");
					response.getWriter().write("-1");
				}
			} else {
				System.out.println("�˺Ų�����!");
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
