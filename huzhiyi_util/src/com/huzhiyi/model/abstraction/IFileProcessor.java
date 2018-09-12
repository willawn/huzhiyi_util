/*
 * @(#)IFileProcessor.java 1.0 2010/07/25
 *
 * Copyright 2010 �ŷ�Ѷ , Inc. All rights reserved.
 */
package com.huzhiyi.model.abstraction;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @ClassName: IFileProcessor
 * @Description: TODO(�ļ�������)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public interface IFileProcessor {

	/**
	 * �����ļ�
	 * 
	 * @param file
	 * @throws IOException
	 * @return �Ƿ����
	 */
	public boolean processFile(File file) throws IOException;

	/**
	 * �����ļ���
	 * 
	 * @param directory
	 * @throws IOException
	 * @return �Ƿ����
	 */
	public boolean processDirectory(File directory) throws IOException;
}