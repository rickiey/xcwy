package com.server;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.utils.JsonReader;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *车主查询商家
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/QueryBusinessServlet")
public class QueryBusinessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public QueryBusinessServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		// 读取数据
		try {
			PrintWriter out = response.getWriter();
			JSONObject json = JsonReader.read(request);
			String trailer = json.getString("trail");
			String water = json.getString("water");
			String tyre = json.getString("tyre");
			String beauty = json.getString("beauty");
			String gasoline = json.getString("gasoline");
			String electricity = json.getString("electricity");
			String unlocking = json.getString("lock");
			String repair = json.getString("repair");

			System.out.println("QueryBusiness_trail:" + trailer);
			System.out.println("QueryBusiness_water:" + water);
			System.out.println("QueryBusiness_tyre:" + tyre);
			System.out.println("QueryBusiness_beauty:" + beauty);
			System.out.println("QueryBusiness_gasoline:" + gasoline);
			System.out.println("QueryBusiness_electricity:" + electricity);
			System.out.println("QueryBusiness_unlocking:" + unlocking);
			System.out.println("QueryBusiness_repair:" + repair);

			String[] service_name = { "trailer", "water", "tyre", "beauty", "gasoline",
					"electricity", "repair", "unlocking" };
			int[] service_state = { 0, 0, 0, 0, 0, 0, 0, 0 };
			if (trailer.equals("trail")) {
				service_state[0] = 1;
			}
			if (water.equals("water")) {
				service_state[1] = 1;
			}
			if (tyre.equals("tyre")) {
				service_state[2] = 1;
			}
			if (beauty.equals("beauty"))
				service_state[3] = 1;
			if (gasoline.equals("gasoline"))
				service_state[4] = 1;
			if (electricity.equals("electricity"))
				service_state[5] = 1;
			if (repair.equals("repair"))
				service_state[6] = 1;
			if (unlocking.equals("lock"))
				service_state[7] = 1;

			Connection conn = null; // 当前的数据库连接
			PreparedStatement pst = null;// 向数据库发送sql语句
			ResultSet rs = null;// 结果集

			try {
				Class.forName(Constants.DRIVER);
				conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
						Constants.DB_PASSWORD);
				String sql = "select * from businessinfo_table join businessservice_table on businessinfo_table.id=businessservice_table.id";
				int b = 0;
				for (int i = 0; i < 8; i++) {
					if (b == 0 && service_state[i] == 1) {
						sql += " and " + service_name[i] + "!='0'";
						b++;
					} else if (b == 1 && service_state[i] == 1)
						sql += " and " + service_name[i] + "!='0'";
				}
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery(sql);
				JSONArray jsonarray = new JSONArray();
				while (rs.next()) {
					String id = rs.getString("id");
					String name = rs.getString("name");
					String phone = rs.getString("phone");
					String address = rs.getString("address");
					String description = rs.getString("description");
					String photo = rs.getString("photo");
					String x = rs.getString("x");
					String y = rs.getString("y");
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("id", id);
					jsonobj.put("bname", name);
					jsonobj.put("phone", phone);
					jsonobj.put("site", address);
					jsonobj.put("description", description);
					jsonobj.put("photo", photo);
					jsonobj.put("bLocationx", x);
					jsonobj.put("bLocationy", y);
					jsonarray.add(jsonobj);
				}
				String str = jsonarray.toString();
				System.out.println(str);
				out.write(str);
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
