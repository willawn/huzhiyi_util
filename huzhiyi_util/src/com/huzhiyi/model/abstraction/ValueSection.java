/*
 * @(#)ValueSubsection.java 1.0 2010/07/25
 *
 * Copyright 2010 �ŷ�Ѷ , Inc. All rights reserved.
 */
package com.huzhiyi.model.abstraction;

/**
 * 
 * @ClassName: ValueSection
 * @Description: TODO(ֵ�ֶ�)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 * @param <T>
 */
public interface ValueSection<T> {

	/**
	 * ��ʼֵ
	 * 
	 * @return
	 * @return
	 */
	public T getBegin();

	/**
	 * ����ֵ
	 * 
	 * @return
	 * @return
	 */
	public T getEnd();
}
