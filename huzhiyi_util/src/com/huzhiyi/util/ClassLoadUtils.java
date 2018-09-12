package com.huzhiyi.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * 
 * @ClassName: ClassLoadUtils
 * @Description: TODO(√Ë ˆ¿‡)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class ClassLoadUtils {
	private String projectPath;
	private URL[] urls;

	public ClassLoadUtils(String projectPath) {
		this.projectPath = projectPath;
	}

	public URL[] load() {
		try {
			File classPathFile = new File(projectPath, ".classpath");
			SAXBuilder builder = Utils.createJdomSaxBuilder(false);
			Element root = builder.build(classPathFile).getRootElement();
			List<Element> els = root.getChildren("classpathentry");
			List<URL> urls = new ArrayList<URL>();
			for (Element e : els) {
				String kind = e.getAttributeValue("kind");
				if (kind.equals("lib") || kind.equals("output")) {
					String path = e.getAttributeValue("path");
					File f = null;
					if (path.startsWith("WebRoot")) {
						f = new File(projectPath, path);
					} else {
						f = new File(path);
					}
					urls.add(f.toURL());
				}
			}
			return urls.toArray(new URL[urls.size()]);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public Class loadClass(String className) throws ClassNotFoundException {
		if (urls == null) {
			urls = load();
		}
		URLClassLoader ucl = new URLClassLoader(urls);
		if (urls == null) {
			throw new ClassNotFoundException(className);
		} else {
			return ucl.loadClass(className);
		}
	}

	public Class loadClass(String className, boolean useSystemLoader) throws ClassNotFoundException {
		if (useSystemLoader) {
			try {
				return ClassLoader.getSystemClassLoader().loadClass(className);
			} catch (ClassNotFoundException e) {
			}
		}
		return loadClass(className);
	}
}
