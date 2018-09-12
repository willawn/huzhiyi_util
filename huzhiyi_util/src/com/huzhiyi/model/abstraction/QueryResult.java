/*
 * @(#)QueryResult.java 1.0 2010/07/25
 *
 * Copyright 2010 雅菲讯 , Inc. All rights reserved.
 */
package com.huzhiyi.model.abstraction;

import java.util.List;

/**
 * 
 * @ClassName: QueryResult
 * @Description: TODO(QueryResult 动态结果)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 * @param <T>
 */
public interface QueryResult<T> {

	/**
	 * 以集合形式获取
	 * 
	 * @return
	 */
	public List<T> getList();

	/**
	 * 以单一实例对象形式获取
	 * 
	 * @return
	 */
	public T getUniqueResult();
}