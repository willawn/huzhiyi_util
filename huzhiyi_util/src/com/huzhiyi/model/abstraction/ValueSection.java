/*
 * @(#)ValueSubsection.java 1.0 2010/07/25
 *
 * Copyright 2010 雅菲讯 , Inc. All rights reserved.
 */
package com.huzhiyi.model.abstraction;

/**
 * 
 * @ClassName: ValueSection
 * @Description: TODO(值分段)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 * @param <T>
 */
public interface ValueSection<T> {

	/**
	 * 开始值
	 * 
	 * @return
	 * @return
	 */
	public T getBegin();

	/**
	 * 结束值
	 * 
	 * @return
	 * @return
	 */
	public T getEnd();
}
