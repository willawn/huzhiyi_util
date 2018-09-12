/*
 * @(#)DriverClassSearcher.java 1.0 2010/07/25
 *
 * Copyright 2010 雅菲讯 , Inc. All rights reserved.
 */
package com.huzhiyi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

/**
 * 
 * @ClassName: DriverClassSearcher
 * @Description: TODO(JDBC驱动类搜索器)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class DriverClassSearcher {

	/**
	 * 从Jar包里搜索出所有JDBC驱动类
	 * 
	 * @param jarFileList
	 * @return
	 */
	public static List<String> search(File[] jarFileList) {
		List<String> driverClassList = new ArrayList<String>(1);// 默认为1个驱动类
		ZipEntry ze;
		try {
			URL[] urls = new URL[jarFileList.length];
			for (int i = 0; i < jarFileList.length; i++) {
				urls[i] = jarFileList[i].toURL();
			}
			ClassLoader classLoader = new URLClassLoader(urls);
			for (URL url : urls) {
				JarInputStream jarIn = new JarInputStream(new FileInputStream(url.getFile()));
				ze = jarIn.getNextEntry();
				while (ze != null) {
					String name = ze.getName();
					if (name.endsWith("Driver.class")) {
						int index = name.lastIndexOf("class");
						if (index != -1) {
							String className = name.replace("/", ".");
							if (className.endsWith("Driver"))
								;
							className = className.substring(0, index - 1);
							Class clz;
							try {
								clz = classLoader.loadClass(className);
								if (java.sql.Driver.class.isAssignableFrom(clz) && !Modifier.isAbstract(clz.getModifiers())) {
									driverClassList.add(className);
								}
							} catch (Exception e) {

							}
						}
					}
					ze = jarIn.getNextEntry();
				}
			}
		} catch (IOException e) {

		}
		return driverClassList;
	}

	/**
	 * 从Jar包里搜索出所有JDBC驱动类
	 * 
	 * @param jarPaths
	 * @return
	 */
	public static List<String> search(String[] jarPaths) {
		File[] jarFileList = new File[jarPaths.length];
		for (int i = 0; i < jarFileList.length; i++) {
			jarFileList[i] = new File(jarPaths[i]);
		}
		return search(jarFileList);
	}

}