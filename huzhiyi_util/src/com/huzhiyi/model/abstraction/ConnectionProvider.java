/*
 * @(#)ConnectionProvider.java 1.0 2010/07/25
 *
 * Copyright 2010 雅菲讯 , Inc. All rights reserved.
 */
package com.huzhiyi.model.abstraction;

import java.sql.Connection;

/**
 * 
 * @ClassName: ConnectionProvider
 * @Description: TODO(连接提供者)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public interface ConnectionProvider {

	/**
	 * 获取文件
	 * 
	 * @return
	 */
	public Connection getConnection();
}
