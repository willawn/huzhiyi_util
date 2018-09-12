/*
 * @(#)Order.java 1.0 2010/07/25
 *
 * Copyright 2010 �ŷ�Ѷ , Inc. All rights reserved.
 */
package com.huzhiyi.comm;

import java.io.Serializable;

/**
 * 
 * @ClassName: Order
 * @Description: TODO(Order ���ݲ�ѯ����)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class Order implements Serializable {
	private static final long serialVersionUID = 5817961455713940090L;
	private boolean ascending;// �Ƿ�����
	private String propertyName;// ���������

	/**
	 * ָ���������ԣ����Ƿ�Ϊ����
	 * 
	 * @param propertyName
	 * @param ascending
	 */
	protected Order(String propertyName, boolean ascending) {
		this.propertyName = propertyName;
		this.ascending = ascending;
	}

	/**
	 * ����
	 * 
	 * @param propertyName
	 * @return
	 */
	public static Order asc(String propertyName) {
		return new Order(propertyName, true);
	}

	/**
	 * ����
	 * 
	 * @param propertyName
	 * @return
	 */
	public static Order desc(String propertyName) {
		return new Order(propertyName, false);
	}

	/**
	 * �����Ƿ�Ϊ����
	 * 
	 * @return
	 */
	public boolean isAscending() {
		return ascending;
	}

	/**
	 * ��ȡ���������
	 * 
	 * @return
	 */
	public String getPropertyName() {
		return propertyName;
	}
}