/*
 * @(#)JdbcUtils.java 1.0 2010/07/25
 *
 * Copyright 2010 �ŷ�Ѷ , Inc. All rights reserved.
 */
package com.huzhiyi.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.huzhiyi.model.JdbcConnectionModel;
import com.huzhiyi.model.JdbcQueryModel;
import com.huzhiyi.model.JdbcUpdateModel;
import com.huzhiyi.model.abstraction.ConnectionProvider;

/**
 * 
 * @ClassName: JdbcUtils
 * @Description: TODO(Jdbc����������)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class JdbcUtils {
	ConnectionProvider connProvider = null; // �����ṩ��

	/**
	 * ���췽��
	 * 
	 * @param connProvider
	 *            �����ṩ��
	 */
	public JdbcUtils(ConnectionProvider connProvider) {
		this.connProvider = connProvider;
	}

	/**
	 * ��ȡJdbcͨ��������
	 * 
	 * @param clasLoader
	 * @param driverClassName
	 * @return
	 * @throws Exception
	 */
	public static Driver getJdbcDriver(ClassLoader clasLoader, String driverClassName) throws Exception {
		Driver driver = (Driver) clasLoader.loadClass(driverClassName).newInstance();
		return driver;
	}

	/**
	 * ͨ��SystemClassLoader��ȡjdbc������
	 * 
	 * @param driverClassName
	 * @return
	 * @throws Exception
	 */
	public static Driver getJdbcDriver(String driverClassName) throws Exception {
		return getJdbcDriver(ClassLoader.getSystemClassLoader(), driverClassName);
	}

	/**
	 * ͨ�����������Jdbc����
	 * 
	 * @param driver
	 * @param connModel
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection(Driver driver, JdbcConnectionModel connModel) throws SQLException {
		return driver.connect(connModel.getUrl(), connModel.getSecurity());
	}

	/**
	 * ִ�и���
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public JdbcUpdateModel executeUpdate(String sql, Object... params) throws SQLException {
		JdbcUpdateModel jdbcModel = new JdbcUpdateModel();
		Connection conn = connProvider.getConnection();
		jdbcModel.setConnection(conn);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		jdbcModel.setPreparedStatement(pstmt);
		int i = 1;
		for (Object param : params) {
			pstmt.setObject(i, param);
			i++;
		}
		int affectRows = pstmt.executeUpdate();
		jdbcModel.setAffectRows(affectRows);
		return jdbcModel;
	}

	/**
	 * ִ�в�ѯ
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public JdbcQueryModel executeQuery(String sql, Object... params) throws SQLException {
		JdbcQueryModel jdbcModel = new JdbcQueryModel();
		Connection conn = connProvider.getConnection();
		jdbcModel.setConnection(conn);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		jdbcModel.setPreparedStatement(pstmt);
		int i = 1;
		for (Object param : params) {
			pstmt.setObject(i, param);
			i++;
		}
		ResultSet rs = pstmt.executeQuery();
		jdbcModel.setResultSet(rs);
		return jdbcModel;
	}
}