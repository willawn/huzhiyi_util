package com.huzhiyi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 
 * @ClassName: JdbcUpdateModel
 * @Description: TODO(√Ë ˆ¿‡)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class JdbcUpdateModel {
	private int affectRows;
	private Connection connection;
	private PreparedStatement preparedStatement;

	public int getAffectRows() {
		return affectRows;
	}

	public void setAffectRows(int affectRows) {
		this.affectRows = affectRows;
	}

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

	public void close() throws SQLException {
		closePreparedStatement();
		closeConnection();
	}
}