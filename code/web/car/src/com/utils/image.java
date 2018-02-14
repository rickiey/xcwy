package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.constants.Constants;

/**
 * 商家上传图片
 * 
 * @author Chow
 * @since 0.0.1
 */
public class image {
	public static void readImage2DB(String id, String path) {
		Connection conn = null; // 当前的数据库连接
		PreparedStatement pst = null;// 向数据库发送sql语句
		ResultSet rs = null;// 结果集

		try {
			Class.forName(Constants.DRIVER);
			conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USERNAME,
					Constants.DB_PASSWORD);
			String sql = "select * from businessinfo_table where id='" + id + "'";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery(sql);
			if (rs.next()) {
				sql = "update businessinfo_table set photo='" + path + "' where id='" + id + "'";
				pst = conn.prepareStatement(sql);
				pst.executeUpdate();
			} else {
				sql = "insert into businessinfo_table (id,photo) values(?,?)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, id);
				pst.setString(2, path);
				pst.executeUpdate();
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
}