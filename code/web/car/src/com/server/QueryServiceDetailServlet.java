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

import net.sf.json.JSONObject;

/**
 *车主查询服务细节
 * @author Chow
 * @since 0.0.1
 */
@WebServlet("/QueryServiceDetailServlet")
public class QueryServiceDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public QueryServiceDetailServlet() {
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
			String id = json.getString("id");
			System.out.println("QueryServiceDetail_id:" + id);

			Connection conn = null; // 当前的数据库连接
			PreparedStatement pst = null;// 向数据库发送sql语句
			ResultSet rs = null;// 结果集

			try {
				Class.forName(Constants.DRIVER);
				conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
						Constants.DB_PASSWORD);
				String sql = "select * from businessservice_table where id ='" + id + "'";
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery(sql);
				// rs.getString(8); //beauty
				// rs.getString(5); //electricity
				// rs.getString(6); //gasoline
				// rs.getString(3); //lock
				// rs.getString(9); //repair
				// rs.getString(7); //trail
				// rs.getString(2); //tyre
				// rs.getString(4); //water
				if (rs.next()) {
					String[] service_name = { "汽车美容","汽车维修", "搭电", "加油", "开锁", "拖车", "换胎", "送水" };
					String[] service_state = { "0", "0", "0", "0", "0", "0", "0", "0" };
					int tj = 0;
					if (rs.getString(8).equals("0")) {
						service_state[0] = "0";
					} else {
						service_state[0] = rs.getString(8);
						tj++;
					}

					if (rs.getString(9).equals("0")) {
						service_state[1] = "0";
					} else {
						service_state[1] = rs.getString(9);
						tj++;
					}

					if (rs.getString(5).equals("0")) {
						service_state[2] = "0";
					} else {
						service_state[2] = rs.getString(5);
						tj++;
					}

					if (rs.getString(6).equals("0"))
						service_state[3] = "0";
					else {
						service_state[3] = rs.getString(6);
						tj++;
					}

					if (rs.getString(3).equals("0"))
						service_state[4] = "0";
					else {
						service_state[4] = rs.getString(3);
						tj++;
					}

					if (rs.getString(7).equals("0"))
						service_state[5] = "0";
					else {
						service_state[5] = rs.getString(7);
						tj++;
					}

					if (rs.getString(2).equals("0"))
						service_state[6] = "0";
					else {
						service_state[6] = rs.getString(2);
						tj++;
					}

					if (rs.getString(4).equals("0"))
						service_state[7] = "0";
					else {
						service_state[7] = rs.getString(4);
						tj++;
					}

					String str = "[";
					for (int i = 0; i < 8; i++) {
						if (i < 7) {
							if (!service_state[i].equals("0")) {
								str += "{" + "\"bname\"" + ":" + "\"" + service_name[i].toString()
										+ "\"" + ",\"" + "description" + "\"" + ":" + "\""
										+ service_state[i] + "\"}" + '\n';
								if (tj > 1) {
									str += ",";
									tj--;
								}
							}
						} else if (tj == 1) {
							if (!service_state[i].equals("0")) {
								str += "{" + "\"bname\"" + ":" + "\"" + service_name[i].toString()
										+ "\"" + ",\"" + "description" + "\"" + ":" + "\""
										+ service_state[i] + "\"}" + '\n';
							}
							break;
						}
					}

					str += "]";
					System.out.println("Test-------" + str);
					out.write(str);
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
