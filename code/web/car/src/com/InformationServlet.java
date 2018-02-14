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
 *修改商家基本信息
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
		Connection conn = null; // 当前的数据库连接
		PreparedStatement pst = null;// 向数据库发送sql语句
		ResultSet rs = null;// 结果集

		String id = (String) request.getSession().getAttribute("id");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String description = request.getParameter("description");

		try {
			Class.forName(Constants.DRIVER);// 加载驱动程序
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
					Constants.DB_PASSWORD);// 连接数据库
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
