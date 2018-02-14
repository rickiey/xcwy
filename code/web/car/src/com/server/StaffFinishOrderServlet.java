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
 *Ա����������
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/StaffFinishOrderServlet")
public class StaffFinishOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public StaffFinishOrderServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		// ��ȡ����
		try {
			PrintWriter out = response.getWriter();
			JSONObject json = JsonReader.read(request);
			String mobile = json.getString("mobile");
			String id = json.getString("id");
			int order_id = Integer.parseInt(id);
			System.out.println("StaffFinishOrderServlet_mobile:" + mobile);
			System.out.println("StaffFinishOrderServlet_id:" + id);

			Connection conn = null; // ��ǰ�����ݿ�����
			PreparedStatement pst = null;// �����ݿⷢ��sql���
			ResultSet rs = null;// �����

			try {
				Class.forName(Constants.DRIVER);
				conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
						Constants.DB_PASSWORD);
				SimpleDateFormat timeend = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ
				String sql = "update staff_table set state=1,order_id='0' where mobile='" + mobile
						+ "'";
				System.out.println("StaffFinishOrderSQL:" + sql);
				pst = conn.prepareStatement(sql);
				pst.executeUpdate();
				sql = "update order_table set state=2,timeend='" + timeend.format(new Date())
						+ "' where id=" + order_id;
				pst = conn.prepareStatement(sql);
				pst.executeUpdate();
				out.write("1");
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
