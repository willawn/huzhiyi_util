/*
 * @(#)Order.java 1.0 2010/07/25
 *
 * Copyright 2010 雅菲讯 , Inc. All rights reserved.
 */
package com.huzhiyi.comm;

import java.io.Serializable;

/**
 * 
 * @ClassName: Order
 * @Description: TODO(Order 数据查询排序)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class Order implements Serializable {
	private static final long serialVersionUID = 5817961455713940090L;
	private boolean ascending;// 是否升序
	private String propertyName;// 排序的属性

	/**
	 * 指定排序属性，并是否为升序
	 * 
	 * @param propertyName
	 * @param ascending
	 */
	protected Order(String propertyName, boolean ascending) {
		this.propertyName = propertyName;
		this.ascending = ascending;
	}

	/**
	 * 升序
	 * 
	 * @param propertyName
	 * @return
	 */
	public static Order asc(String propertyName) {
		return new Order(propertyName, true);
	}

	/**
	 * 降序
	 * 
	 * @param propertyName
	 * @return
	 */
	public static Order desc(String propertyName) {
		return new Order(propertyName, false);
	}

	/**
	 * 检索是否为升序
	 * 
	 * @return
	 */
	public boolean isAscending() {
		return ascending;
	}

	/**
	 * 获取排序的属性
	 * 
	 * @return
	 */
	public String getPropertyName() {
		return propertyName;
	}
}