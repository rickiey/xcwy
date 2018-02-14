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
 *�޸��̼ҷ�����Ϣ
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/ServiceServlet")
public class ServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ServiceServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null; // ��ǰ�����ݿ�����
		PreparedStatement pst = null;// �����ݿⷢ��sql���
		ResultSet rs = null;// �����

		String id = (String) request.getSession().getAttribute("id");
		String tyre = request.getParameter("tyre");
		String unlocking = request.getParameter("unlocking");
		String water = request.getParameter("water");
		String electricity = request.getParameter("electricity");
		String gasoline = request.getParameter("gasoline");
		String trailer = request.getParameter("trailer");
		String repair = request.getParameter("repair");
		String beauty = request.getParameter("beauty");

		try {
			Class.forName(Constants.DRIVER);
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
					Constants.DB_PASSWORD);
			String sql = "select * from businessservice_table where id='" + id + "'";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery(sql);
			if (rs.next()) {
				sql = "update businessservice_table set tyre=" + tyre + ",unlocking=" + unlocking
						+ ",water=" + water + ",electricity=" + electricity + ",gasoline="
						+ gasoline + ",trailer=" + trailer + " where id='" + id + "'";
				System.out.println("TEST-------" + sql);
				pst = conn.prepareStatement(sql);
				pst.executeUpdate();
				sql = "update businessservice_table set repair='" + repair + "',beauty='" + beauty
						+ "' where id='" + id + "'";
				pst = conn.prepareStatement(sql);
				pst.executeUpdate();
				response.getWriter().write("1");
			} else {
				sql = "insert into businessservice_table (id,tyre,unlocking,water,electricity,gasoline,trailer) values(?,?,?,?,?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, id);
				pst.setString(2, tyre);
				pst.setString(3, unlocking);
				pst.setString(4, water);
				pst.setString(5, electricity);
				pst.setString(6, gasoline);
				pst.setString(7, trailer);
				pst.executeUpdate();
				sql = "update businessservice_table set repair=" + repair + ",beauty=" + beauty
						+ " where id='" + id + "'";
				pst = conn.prepareStatement(sql);
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
