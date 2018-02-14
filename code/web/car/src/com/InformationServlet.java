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
 *�޸��̼һ�����Ϣ
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/InformationServlet")
public class InformationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public InformationServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null; // ��ǰ�����ݿ�����
		PreparedStatement pst = null;// �����ݿⷢ��sql���
		ResultSet rs = null;// �����

		String id = (String) request.getSession().getAttribute("id");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String description = request.getParameter("description");

		try {
			Class.forName(Constants.DRIVER);// ������������
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
					Constants.DB_PASSWORD);// �������ݿ�
			String sql = "select * from businessinfo_table where id='" + id + "'";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery(sql);
			if (rs.next()) {
				if (!name.equals("")) {
					sql = "update businessinfo_table set name='" + name + "' where id='" + id + "'";
					pst.executeUpdate(sql);
				}
				if (!phone.equals("")) {
					sql = "update businessinfo_table set phone='" + phone + "' where id='" + id
							+ "'";
					pst.executeUpdate(sql);
				}
				if (!address.equals("")) {
					sql = "update businessinfo_table set address='" + address + "' where id='" + id
							+ "'";
					pst.executeUpdate(sql);
				}
				if (!description.equals("")) {
					sql = "update businessinfo_table set description='" + description
							+ "' where id='" + id + "'";
					pst.executeUpdate(sql);
				}
				response.getWriter().write("1");
			} else {
				sql = "insert into businessinfo_table (id,name,phone,address,description) values(?,?,?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, id);
				pst.setString(2, name);
				pst.setString(3, phone);
				pst.setString(4, address);
				pst.setString(5, description);
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
