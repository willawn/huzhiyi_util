/*
 * @(#)QueryResult.java 1.0 2010/07/25
 *
 * Copyright 2010 �ŷ�Ѷ , Inc. All rights reserved.
 */
package com.huzhiyi.model.abstraction;

import java.util.List;

/**
 * 
 * @ClassName: QueryResult
 * @Description: TODO(QueryResult ��̬���)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 * @param <T>
 */
public interface QueryResult<T> {

	/**
	 * �Լ�����ʽ��ȡ
	 * 
	 * @return
	 */
	public List<T> getList();

	/**
	 * �Ե�һʵ��������ʽ��ȡ
	 * 
	 * @return
	 */
	public T getUniqueResult();
}