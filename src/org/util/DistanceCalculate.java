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

		// �����6000��Ϊ��Χ�ľ�γ�ȷ�Χ
		// ��Сγ�ȡ����ȣ����γ�ȡ�����
		around = getAround(locationx, locationy, 6000);
=======
	public static ResultSet getNearby(HttpServletRequest request,
			double locationx, double locationy, int juge) {
		ResultSet rs = null;
		double[] around;

		// �����4000��Ϊ��Χ�ľ�γ�ȷ�Χ
		// ��Сγ�ȡ����ȣ����γ�ȡ�����
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
			// ����������״̬
			
			try {
				String sql = "select * from tb_msg where locationx <> 0  "
=======
		if (juge == 0) {
			// �����ı��

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
			// ��������Ը

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
			// �����Ĺ���

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
		// ���Ҹ����Ļ���
		else if( juge == 4 ){
			try {
				// ��������ظ������������ưٶ����ɣ�
				String sql = "select * from tb_topic where locationx <> 0  "
=======
		else if( juge == 3 ){
			// ����������״̬
			
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
	 * ���������ĵ�Ϊ���ĵ��ķ��ξ�γ��
	 * 
	 * @param lat
	 *            γ��
	 * @param lon
	 *            ����
	 * @param raidus
	 *            �뾶������Ϊ��λ��
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
		Double dpmLng = 1 / mpdLng; // ��γ��
		Double radiusLng = dpmLng * raidusMile; // �Ϸ�����
		Double minLng = longitude - radiusLng; // �ҷ�γ��
		Double maxLng = longitude + radiusLng; // �·�����
		return new double[] { minLat, minLng, maxLat, maxLng }; // ��Сγ�ȡ����ȣ����γ�ȡ�����
	}

}
