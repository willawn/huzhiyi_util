/*
 * @(#)IOUtils.java 1.0 2010/07/25
 *
 * Copyright 2010 雅菲讯 , Inc. All rights reserved.
 */
package com.huzhiyi.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import sun.misc.URLClassPath;

import com.huzhiyi.model.abstraction.IFileProcessor;

/**
 * 
 * @ClassName: IOUtils
 * @Description: TODO(描述类)
 * 		<p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 *
 */
public class IOUtils {
	
	/**
	 * 默认读写缓冲大小
	 */
	private static final int BUFFER_SIZE = 1024;

	/**
	 * 保存IO输入流到输入流。
	 * 
	 * @param source
	 * @param targer
	 * @param isFlushCloseOutputStream
	 *            是否刷新和关闭输出流
	 * @throws IOException
	 */
	public static void savaStream(InputStream source, OutputStream targer, boolean isFlushCloseOutputStream) throws IOException {
		try {
			int bufferSize = BUFFER_SIZE;
			byte[] buffer = new byte[bufferSize];
			int len;
			for (; (len = source.read(buffer, 0, bufferSize)) > 0;) {
				targer.write(buffer, 0, len);
			}
		} finally {
			if (isFlushCloseOutputStream) {
				targer.flush();
				targer.close();
			}
			if(source!=null){
				source.close();
			}
		}
	}

	/**
	 * 保存IO输入流到输入流。输出流自动刷新和关闭
	 * 
	 * @param source
	 * @param targer
	 * @throws IOException
	 */
	public static void savaStream(InputStream source, OutputStream targer) throws IOException {
		savaStream(source, targer, true);
	}

	/**
	 * 把输入流转为字节输出流
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static ByteArrayOutputStream toByteArrayOutputStream(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(is.available());
		savaStream(is, bos);
		return bos;
	}

	/**
	 * 把输入流转为字节组
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static byte[] getBytes(InputStream is) throws IOException {
		ByteArrayOutputStream bos = toByteArrayOutputStream(is);
		byte[] bytes = bos.toByteArray();
		return bytes;
	}

	/**
	 * 保存文件
	 * 
	 * @param source
	 * @param targer
	 * @throws IOException
	 */
	public static void savaFile(InputStream source, File target) throws IOException {
		OutputStream targerStream = new FileOutputStream(target);
		savaStream(source, targerStream);
	}

	/**
	 * 保存文件
	 * 
	 * @param source
	 * @param targer
	 * @throws IOException
	 * @return 是否保存成功,当文件不能访问时会返回false
	 */
	public static boolean copyFile(File source, File target) throws IOException {
		InputStream sourceStream = getReadableStream(source);
		if (sourceStream != null) {
			OutputStream targerStream = new FileOutputStream(target);
			savaStream(sourceStream, targerStream);
			return true;
		}
		return false;
	}

	/**
	 * 保存数据
	 * 
	 * @param source
	 * @param targer
	 * @throws IOException
	 */
	public static void savaData(byte[] source, File targer) throws IOException {
		InputStream sourceStream = new ByteArrayInputStream(source);
		OutputStream targerStream = new FileOutputStream(targer);
		savaStream(sourceStream, targerStream);
	}

	/**
	 * 创建文件的目录
	 * 
	 * @param file
	 * @return
	 */
	public static boolean createDirectoryByFile(File file) {
		File directory = file.getParentFile();
		if (directory != null) {
			return directory.mkdirs();
		}
		return false;
	}

	/**
	 * 复制多个文件到文件夹下
	 * 
	 * @param fileList
	 * @param directory
	 * @throws IOException
	 */
	public static void copyFileList(File[] fileList, File directory) throws IOException {
		for (File file : fileList) {
			File newFile = new File(directory, file.getName());
			if (file.isFile()) {
				copyFile(file, newFile);
			} else {
				newFile.mkdir();// 创建文件夹
			}
		}
	}

	/**
	 * 深度遍历文件夹
	 * 
	 * @param directory
	 *            文件夹
	 * @param processor
	 *            文件处理器
	 * @throws IOException
	 */
	public static void deepForeachDirectory(File directory, IFileProcessor processor) throws IOException {
		for (File file : directory.listFiles()) {
			if (file.isFile()) {
				if (!processor.processFile(file)) {
					break;
				}
			} else {
				if (!processor.processDirectory(file)) {
					break;
				}
				deepForeachFiles(file.listFiles(), processor);
			}
		}
	}

	/**
	 * 深度遍历文件组
	 * 
	 * @param fileList
	 * @param processor
	 * @throws IOException
	 */
	public static void deepForeachFiles(File[] fileList, IFileProcessor processor) throws IOException {
		for (File file : fileList) {
			if (file.isFile()) {
				if (!processor.processFile(file)) {
					break;
				}
			} else {
				if (!processor.processDirectory(file)) {
					break;
				}
				deepForeachFiles(file.listFiles(), processor);
			}
		}
	}

	/**
	 * 遍历文件组
	 * 
	 * @param fileList
	 * @param processor
	 * @throws IOException
	 */
	public static void foreachFiles(File[] fileList, IFileProcessor processor) throws IOException {
		for (File file : fileList) {
			if (file.isFile()) {
				if (!processor.processFile(file)) {
					break;
				}
			} else {
				if (!processor.processDirectory(file)) {
					break;
				}
			}
		}
	}

	/**
	 * 遍历文件夹
	 * 
	 * @param directory
	 * @param processor
	 * @throws IOException
	 */
	public static void foreachDirectory(File directory, IFileProcessor processor) throws IOException {
		for (File file : directory.listFiles()) {
			if (file.isFile()) {
				if (!processor.processFile(file)) {
					break;
				}
			} else {
				if (!processor.processDirectory(file)) {
					break;
				}
			}
		}
	}

	/**
	 * 深度文件夹复制
	 * 
	 * @param source
	 * @param target
	 * @throws IOException
	 */
	public static void deepCopyDirectory(final File source, final File target) throws IOException {
		final int pathLen = source.getAbsolutePath().length();
		deepForeachDirectory(source, new IFileProcessor() {
			public boolean processDirectory(File directory) {
				String fileName = directory.getAbsolutePath().substring(pathLen);
				new File(target, fileName).mkdir();
				directory.mkdir();// 创建文件夹
				return true;
			}

			public boolean processFile(File file) throws IOException {
				String fileName = file.getAbsolutePath().substring(pathLen);
				copyFile(file, new File(target, fileName));
				return true;
			}
		});
	}

	/**
	 * 复制文件夹
	 * 
	 * @param source
	 * @param target
	 * @throws IOException
	 */
	public static void copyDirectory(final File source, final File target) throws IOException {
		final int pathLen = source.getAbsolutePath().length();
		foreachDirectory(source, new IFileProcessor() {
			public boolean processDirectory(File directory) {
				String fileName = directory.getAbsolutePath().substring(pathLen);
				new File(target, fileName).mkdir();
				directory.mkdir();// 创建文件夹
				return true;
			}

			public boolean processFile(File file) throws IOException {
				String fileName = file.getAbsolutePath().substring(pathLen);
				copyFile(file, new File(target, fileName));
				return true;
			}
		});
	}

	/**
	 * 文件重命名
	 * 
	 * @param file
	 * @param name
	 * @return
	 */
	public static boolean renameFile(File file, String name) {
		return file.renameTo(new File(file.getParent(), name));
	}

	/**
	 * 文件重命名
	 * 
	 * @param file
	 * @param name
	 * @return
	 */
	public static boolean renameFile(String file, String name) {
		return renameFile(new File(file), name);
	}

	/**
	 * 获取相对路径
	 * 
	 * @param baseFile
	 * @param file
	 * @return
	 */
	public static String getRelativePath(File baseFile, File file) {
		return getRelativePath(baseFile.getAbsolutePath(), file.getAbsolutePath());
	}

	/**
	 * 获取相对路径
	 * 
	 * @param basePath
	 * @param path
	 * @return
	 */
	public static String getRelativePath(String basePath, String path) {
		return basePath.replace(path, "");
	}

	/**
	 * 获取可读流，若是流为不可读，返回null
	 * 
	 * @param file
	 * @return
	 */
	public static InputStream getReadableStream(File file) {
		try {
			final InputStream is = new FileInputStream(file);
			final int readByte = is.read();
			return new InputStream() {
				int i = 0;

				@Override
				public int read() throws IOException {
					if (i++ == 0) {
						return readByte;
					}
					return is.read();
				}

			};
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * 获取可读流，若是流为不可读，返回null
	 * 
	 * @param ins
	 *            当获得流返回后，ins会指向null地址
	 * @return
	 */
	public static InputStream getReadableStream(InputStream ins) {
		try {
			final InputStream is = ins;
			ins = null;
			final int readByte = is.read();
			return new InputStream() {
				int i = 0;

				@Override
				public int read() throws IOException {
					if (i++ == 0) {
						return readByte;
					}
					return is.read();
				}
			};
		} catch (IOException e) {
		}
		return null;
	}
	
	/**
	 * 对jar文件进行解压
	 * @param jarSource
	 * @param targetDir
	 * @throws IOException
	 */
	public static void jarDecompression(File jarSource,File targetDir) throws IOException{
		jarDecompression(jarSource.toURL(),targetDir);
	}

	/**
	 * 对jar文件进行解压
	 * @param url
	 * @param targetDir
	 * @throws IOException
	 */
	public static void jarDecompression(URL url, File targetDir) throws IOException{
		// TODO 自动生成方法存根
		if(!targetDir.exists()){
			throw new FileNotFoundException(targetDir.getAbsolutePath()+"(系统找不到指定的路径。)");
		}
		JarInputStream jis=null;
		jis=new JarInputStream(url.openStream());
		try {
			URLClassPath ucp=new URLClassPath(new URL[]{url});
			JarEntry entry=null;
			for(;(entry=jis.getNextJarEntry())!=null;){
				String name=entry.getName();
				entry.clone();
				File f=new File(targetDir,name);
				if(entry.isDirectory()){
					f.mkdir();
				}else{
					savaFile(ucp.getResource(name).getInputStream(), f);
				}
			}
		} finally{
			if(jis!=null){
				jis.close();
			}
		}
	}
	
	/**
	 * 转化为字节数组
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static byte[] toBytes(InputStream is) throws IOException{
		byte[] data=new byte[is.available()];
		int i;
		int cnt=0;
		for(;(i=is.read())>0;){
			data[cnt]=(byte) i;
		}
		return data;
	}
}
