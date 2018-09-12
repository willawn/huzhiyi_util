/*
 * @(#)IOUtils.java 1.0 2010/07/25
 *
 * Copyright 2010 �ŷ�Ѷ , Inc. All rights reserved.
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
 * @Description: TODO(������)
 * 		<p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 *
 */
public class IOUtils {
	
	/**
	 * Ĭ�϶�д�����С
	 */
	private static final int BUFFER_SIZE = 1024;

	/**
	 * ����IO����������������
	 * 
	 * @param source
	 * @param targer
	 * @param isFlushCloseOutputStream
	 *            �Ƿ�ˢ�º͹ر������
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
	 * ����IO����������������������Զ�ˢ�º͹ر�
	 * 
	 * @param source
	 * @param targer
	 * @throws IOException
	 */
	public static void savaStream(InputStream source, OutputStream targer) throws IOException {
		savaStream(source, targer, true);
	}

	/**
	 * ��������תΪ�ֽ������
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
	 * ��������תΪ�ֽ���
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
	 * �����ļ�
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
	 * �����ļ�
	 * 
	 * @param source
	 * @param targer
	 * @throws IOException
	 * @return �Ƿ񱣴�ɹ�,���ļ����ܷ���ʱ�᷵��false
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
	 * ��������
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
	 * �����ļ���Ŀ¼
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
	 * ���ƶ���ļ����ļ�����
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
				newFile.mkdir();// �����ļ���
			}
		}
	}

	/**
	 * ��ȱ����ļ���
	 * 
	 * @param directory
	 *            �ļ���
	 * @param processor
	 *            �ļ�������
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
	 * ��ȱ����ļ���
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
	 * �����ļ���
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
	 * �����ļ���
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
	 * ����ļ��и���
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
				directory.mkdir();// �����ļ���
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
	 * �����ļ���
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
				directory.mkdir();// �����ļ���
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
	 * �ļ�������
	 * 
	 * @param file
	 * @param name
	 * @return
	 */
	public static boolean renameFile(File file, String name) {
		return file.renameTo(new File(file.getParent(), name));
	}

	/**
	 * �ļ�������
	 * 
	 * @param file
	 * @param name
	 * @return
	 */
	public static boolean renameFile(String file, String name) {
		return renameFile(new File(file), name);
	}

	/**
	 * ��ȡ���·��
	 * 
	 * @param baseFile
	 * @param file
	 * @return
	 */
	public static String getRelativePath(File baseFile, File file) {
		return getRelativePath(baseFile.getAbsolutePath(), file.getAbsolutePath());
	}

	/**
	 * ��ȡ���·��
	 * 
	 * @param basePath
	 * @param path
	 * @return
	 */
	public static String getRelativePath(String basePath, String path) {
		return basePath.replace(path, "");
	}

	/**
	 * ��ȡ�ɶ�����������Ϊ���ɶ�������null
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
	 * ��ȡ�ɶ�����������Ϊ���ɶ�������null
	 * 
	 * @param ins
	 *            ����������غ�ins��ָ��null��ַ
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
	 * ��jar�ļ����н�ѹ
	 * @param jarSource
	 * @param targetDir
	 * @throws IOException
	 */
	public static void jarDecompression(File jarSource,File targetDir) throws IOException{
		jarDecompression(jarSource.toURL(),targetDir);
	}

	/**
	 * ��jar�ļ����н�ѹ
	 * @param url
	 * @param targetDir
	 * @throws IOException
	 */
	public static void jarDecompression(URL url, File targetDir) throws IOException{
		// TODO �Զ����ɷ������
		if(!targetDir.exists()){
			throw new FileNotFoundException(targetDir.getAbsolutePath()+"(ϵͳ�Ҳ���ָ����·����)");
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
	 * ת��Ϊ�ֽ�����
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
