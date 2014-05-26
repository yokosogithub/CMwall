package org.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.dboprate.DBoprate;
import org.dboprate.JSPConnectDB;

public class DistanceCalculate {
	public static double getDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double radLat1 = lat1 * Math.PI / 180;
		double radLat2 = lat2 * Math.PI / 180;
		double a = radLat1 - radLat2;
		double b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137.0;
		s = Math.round(s * 10000) / 10000;

		return s;
	}

	// 0,8 8,8 16,8
<<<<<<< HEAD
	public static ResultSet getNearby(HttpServletRequest request,double locationx, double locationy, int juge) {
		ResultSet rs = null;
		double[] around;

		// 获得以6000米为范围的经纬度范围
		// 最小纬度、经度，最大纬度、经度
		around = getAround(locationx, locationy, 6000);
=======
	public static ResultSet getNearby(HttpServletRequest request,
			double locationx, double locationy, int juge) {
		ResultSet rs = null;
		double[] around;

		// 获得以4000米为范围的经纬度范围
		// 最小纬度、经度，最大纬度、经度
		around = getAround(locationx, locationy, 4000);
>>>>>>> remotes/CMwall/master
		double minLat = around[0];
		double minLon = around[1];
		double maxLat = around[2];
		double maxLon = around[3];

		Connection connection = null;
		JSPConnectDB jspConnectDB = new JSPConnectDB();
		connection = jspConnectDB.connecDB(request);

<<<<<<< HEAD
		
		if( juge == 3 ){
			// 附近的所有状态
			
			try {
				String sql = "select * from tb_msg where locationx <> 0  "
=======
		if (juge == 0) {
			// 附近的表白

			try {
				String sql = "select * from tb_biaobai where locationx <> 0  "
						+ " and locationy > '" + minLon + "' "
						+ " and locationy < '" + maxLon + "' "
						+ " and locationx > '" + minLat + "' "
						+ " and locationx < '" + maxLat + "' "
						+ " order by create_time desc LIMIT 60 ";

				Statement stm = connection.createStatement();
				rs = stm.executeQuery(sql);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (juge == 1) {
			// 附近的心愿

			try {
				String sql = "select * from tb_xinyuan where locationx <> 0  "
>>>>>>> remotes/CMwall/master
						+ " and locationy > '" + minLon + "' "
						+ " and locationy < '" + maxLon + "' "
						+ " and locationx > '" + minLat + "' "
						+ " and locationx < '" + maxLat + "' "
<<<<<<< HEAD
						+ " order by create_time desc LIMIT 150 ";
=======
						+ " order by create_time desc LIMIT 60 ";

				Statement stm = connection.createStatement();
				rs = stm.executeQuery(sql);

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (juge == 2) {
			// 附近的勾搭

			try {
				String sql = "select * from tb_gouda where locationx <> 0  "
						+ " and locationy > '" + minLon + "' "
						+ " and locationy < '" + maxLon + "' "
						+ " and locationx > '" + minLat + "' "
						+ " and locationx < '" + maxLat + "' "
						+ " order by create_time desc LIMIT 60 ";
>>>>>>> remotes/CMwall/master

				Statement stm = connection.createStatement();
				rs = stm.executeQuery(sql);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
<<<<<<< HEAD
		// 查找附近的话题
		else if( juge == 4 ){
			try {
				// 按照最近回复优先排序（类似百度贴吧）
				String sql = "select * from tb_topic where locationx <> 0  "
=======
		else if( juge == 3 ){
			// 附近的所有状态
			
			try {
				String sql = "select * from tb_msg where locationx <> 0  "
>>>>>>> remotes/CMwall/master
						+ " and locationy > '" + minLon + "' "
						+ " and locationy < '" + maxLon + "' "
						+ " and locationx > '" + minLat + "' "
						+ " and locationx < '" + maxLat + "' "
<<<<<<< HEAD
						+ " order by last_reply_time desc LIMIT 100 ";
=======
						+ " order by create_time desc LIMIT 100 ";
>>>>>>> remotes/CMwall/master

				Statement stm = connection.createStatement();
				rs = stm.executeQuery(sql);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return rs;
	}

	/**
	 * 生成以中心点为中心的四方形经纬度
	 * 
	 * @param lat
	 *            纬度
	 * @param lon
	 *            精度
	 * @param raidus
	 *            半径（以米为单位）
	 * @return
	 */
	public static double[] getAround(double lat, double lon, int raidus) {

		Double latitude = lat;
		Double longitude = lon;

		Double degree = (24901 * 1609) / 360.0;
		double raidusMile = raidus;

		Double dpmLat = 1 / degree;
		Double radiusLat = dpmLat * raidusMile;
		Double minLat = latitude - radiusLat;
		Double maxLat = latitude + radiusLat;

		Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
		Double dpmLng = 1 / mpdLng; // 左方纬度
		Double radiusLng = dpmLng * raidusMile; // 上方精度
		Double minLng = longitude - radiusLng; // 右方纬度
		Double maxLng = longitude + radiusLng; // 下方精度
		return new double[] { minLat, minLng, maxLat, maxLng }; // 最小纬度、经度，最大纬度、经度
	}

}
