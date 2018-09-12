package com.huzhiyi.model.abstraction;

import javax.servlet.http.HttpServletRequest;

import com.huzhiyi.model.FileInfo;

/**
 * 
 * @ClassName: IFileProvider
 * @Description: TODO(描述类)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public interface IFileProvider {

	/**
	 * 获取文件信息
	 * 
	 * @param request
	 * @return
	 */
	public FileInfo getFile(HttpServletRequest request);
}