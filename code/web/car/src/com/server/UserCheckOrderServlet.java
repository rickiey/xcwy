package com.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.mysql.jdbc.ResultSetMetaData;
import com.constants.Constants;
import com.utils.JsonReader;

/**
 *车主确认订单
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/UserCheckOrderServlet")
public class UserCheckOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserCheckOrderServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		try {
			JSONObject json = JsonReader.read(request);
			String mobile = json.getString("mobile");
			System.out.println("UserCheckOrderServlet_mobile" + mobile);
			String Inform = selectInform(mobile);
			PrintWriter out = response.getWriter();
			out.write(Inform);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public String selectInform(String mobile) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "select * from userinfo_table where mobile ='" + mobile + "'";
		System.out.println(sql);
		try {
			Class.forName(Constants.DRIVER);
			con = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
					Constants.DB_PASSWORD);
			st = con.createStatement();
			rs = st.executeQuery(sql);
			String result = resultSetToJson(rs);
			System.out.println(result);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		String msg = "error";
		return msg;
	}

	public String resultSetToJson(ResultSet rs) throws SQLException, JSONException {
		ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
		rs.next();
		int columnCount = metaData.getColumnCount();

		JSONObject jsonObj = new JSONObject();

		for (int i = 1; i <= columnCount; i++) {
			String columnName = metaData.getColumnLabel(i);
			String value = rs.getString(columnName);
			jsonObj.put(columnName, value);
		}
		return jsonObj.toString();
	}
}
