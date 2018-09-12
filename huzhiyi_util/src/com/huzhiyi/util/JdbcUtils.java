/*
 * @(#)JdbcUtils.java 1.0 2010/07/25
 *
 * Copyright 2010 雅菲讯 , Inc. All rights reserved.
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
 * @Description: TODO(Jdbc操作辅助类)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class JdbcUtils {
	ConnectionProvider connProvider = null; // 连接提供器

	/**
	 * 构造方法
	 * 
	 * @param connProvider
	 *            连接提供者
	 */
	public JdbcUtils(ConnectionProvider connProvider) {
		this.connProvider = connProvider;
	}

	/**
	 * 获取Jdbc通用驱动器
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
	 * 通过SystemClassLoader获取jdbc驱动器
	 * 
	 * @param driverClassName
	 * @return
	 * @throws Exception
	 */
	public static Driver getJdbcDriver(String driverClassName) throws Exception {
		return getJdbcDriver(ClassLoader.getSystemClassLoader(), driverClassName);
	}

	/**
	 * 通过驱动器获得Jdbc连接
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
	 * 执行更新
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
	 * 执行查询
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