/*
 * @(#)IFileProcessor.java 1.0 2010/07/25
 *
 * Copyright 2010 雅菲讯 , Inc. All rights reserved.
 */
package com.huzhiyi.model.abstraction;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @ClassName: IFileProcessor
 * @Description: TODO(文件处理器)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public interface IFileProcessor {

	/**
	 * 处理文件
	 * 
	 * @param file
	 * @throws IOException
	 * @return 是否继续
	 */
	public boolean processFile(File file) throws IOException;

	/**
	 * 处理文件夹
	 * 
	 * @param directory
	 * @throws IOException
	 * @return 是否继续
	 */
	public boolean processDirectory(File directory) throws IOException;
}