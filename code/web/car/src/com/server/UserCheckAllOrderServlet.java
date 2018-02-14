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

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import com.mysql.jdbc.ResultSetMetaData;
import com.constants.Constants;
import com.utils.JsonReader;

/**
 * 车主查看所有订单
 * 
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/UserCheckAllOrderServlet")
public class UserCheckAllOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserCheckAllOrderServlet() {
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
			System.out.println("UserCheckAllOrderServlet_mobile" + mobile);
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
		String sql = "select * from order_table join businessinfo_table on order_table.business_id = businessinfo_table.id where user_mobile ='"
				+ mobile + "' order by timestart desc";
		// System.out.println("UserCheckAllOrder"+sql);
		try {
			Class.forName(Constants.DRIVER);
			con = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
					Constants.DB_PASSWORD);
			st = con.createStatement();
			rs = st.executeQuery(sql);
			String result = resultSetToJson(rs);
			System.out.println("UserCheckAllOrderRes" + result);
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		String msg = "error";
		return msg;
	}

	public String resultSetToJson(ResultSet rs) throws SQLException, JSONException {
		JSONArray array = new JSONArray();

		// 获取列数
		ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
		// int columnCount = metaData.getColumnCount();

		// 遍历ResultSet中的每条数据
		while (rs.next()) {
			JSONObject jsonObj = new JSONObject();

			// 遍历每一列
			for (int i = 1; i <= 14; i++) {
				String columnName = metaData.getColumnLabel(i);
				String value = rs.getString(columnName);
				jsonObj.put(columnName, value);
			}
			jsonObj.put("bLocationx", rs.getString("businessinfo_table.x"));
			jsonObj.put("bLocationy", rs.getString("businessinfo_table.y"));
			array.add(jsonObj);
		}
		return array.toString();
	}
}
