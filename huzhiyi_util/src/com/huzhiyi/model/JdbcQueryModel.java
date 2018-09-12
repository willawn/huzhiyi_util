/*
 * @(#)StringUtil.java 1.0 2010/07/25
 *
 * Copyright 2010 雅菲讯 , Inc. All rights reserved.
 */
package com.huzhiyi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @ClassName: JdbcQueryModel
 * @Description: TODO(JdbcModel Jdbc操作数据模型)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class JdbcQueryModel {
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}

	public void setPreparedStatement(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public void closeConnection() throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	public void closePreparedStatement() throws SQLException {
		if (preparedStatement != null) {
			preparedStatement.close();
		}
	}

	public void closeResultSet() throws SQLException {
		if (resultSet != null) {
			resultSet.close();
		}
	}

	public void close() throws SQLException {
		closeResultSet();
		closePreparedStatement();
		closeConnection();
	}
}