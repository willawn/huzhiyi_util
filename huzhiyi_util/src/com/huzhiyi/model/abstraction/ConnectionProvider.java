/*
 * @(#)ConnectionProvider.java 1.0 2010/07/25
 *
 * Copyright 2010 �ŷ�Ѷ , Inc. All rights reserved.
 */
package com.huzhiyi.model.abstraction;

import java.sql.Connection;

/**
 * 
 * @ClassName: ConnectionProvider
 * @Description: TODO(�����ṩ��)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public interface ConnectionProvider {

	/**
	 * ��ȡ�ļ�
	 * 
	 * @return
	 */
	public Connection getConnection();
}
