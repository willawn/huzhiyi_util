package com.huzhiyi.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: JavaCodeFile
 * @Description: TODO(描述类)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class JavaCodeFile {
	private String superClassName;

	private boolean isInterface;

	private List<String> interfaces = new ArrayList<String>();

	private List<String> annotations = new ArrayList<String>();

	private String clzName;

	private String packageName;

	private List<String> packages = new ArrayList<String>();

	private List<String> fields = new ArrayList<String>();

	private Map<String, String> methods = new LinkedHashMap<String, String>();

	private Map<Integer, String> stepDescriptions = new LinkedHashMap<Integer, String>();

	private String modify = "public";

	int fieldStep = 0;

	int methodStep = 10000000;

	private String classDescription;

	boolean isLastMethod = true;

	public JavaCodeFile(String className, boolean isInterface) {
		this.isInterface = isInterface;
		String[] strArr = classNameSubPackage(className);
		if (strArr.length == 2) {
			this.packageName = strArr[0];
			clzName = strArr[1];
		} else {
			this.clzName = className;
		}
	}

	public JavaCodeFile(String className, boolean isInterface, String description) {
		this.isInterface = isInterface;
		String[] strArr = classNameSubPackage(className);
		if (strArr.length == 2) {
			this.packageName = strArr[0];
			clzName = strArr[1];
		} else {
			this.clzName = className;
		}
		classDescription = description;
	}

	public void setSuperClassName(String className) {
		this.superClassName = className;
	}

	public void addAnnotation(String annoco) {
		annotations.add(annoco);
	}

	public void implementInterface(String interfaceName) {
		implortPage(interfaceName);
		interfaces.add(classNameSubName(interfaceName));
	}

	public static final String NEWLINE = "\r\n";
	public static final String METHOD_NEWLINE = "\r\n\t";
	public static final String METHOD_BODY_NEWLINE = "\r\n\t\t";

	public void insertDescription(String description, boolean isMethod) {
		isLastMethod = isMethod;
		String str = "\t/**\r\n\t*" + description + "\r\n\t*/";
		if (isLastMethod) {
			stepDescriptions.put(methodStep, str);
		} else {
			stepDescriptions.put(fieldStep, str);
		}
	}

	public void addConstructor(Map<String, String> params) {
		String str = "";
		String str2 = "";
		if (params != null) {
			for (String key : params.keySet()) {
				str += params.get(key) + " " + key + ",";
				str2 += "this." + key + "=" + key + ";\r\n";
			}
			if (str.length() > 0) {
				str = str.substring(0, str.length() - 1);
			}
		}
		str2 = str2.trim();
		this.addMethod("public " + clzName + "(" + str + ")", str2);
	}

	public void implortPage(String className) {
		if (StringUtils.isNotEmpty(this.packageName)) {
			if (className.indexOf("java.lang") == -1 && className.indexOf(this.packageName) == -1 && packages.indexOf(className) == -1) {
				packages.add(className);
			}
		} else {
			if (className.indexOf("java.lang") == -1 && className.indexOf(".") != -1 && packages.indexOf(className) == -1) {
				packages.add(className);
			}
		}

	}

	public void addMethod(String methodDefine, String body) {
		methods.put(methodDefine, body);
		methodStep++;
		isLastMethod = true;
	}

	public void addField(String modify, String type, String fieldName, String init) {
		fields.add(modify + " " + type + " " + fieldName + init);
		fieldStep++;
		isLastMethod = false;
	}

	public void addGetter(String fieldName, String type) {
		this.addMethod("public " + type + " get" + StringUtils.firstToUpperCase(fieldName) + "()", "return this." + fieldName + ";");
	}

	public void addSetter(String fieldName, String type) {
		this.addMethod("public void set" + StringUtils.firstToUpperCase(fieldName) + "(" + type + " " + fieldName + ")", "this."
				+ fieldName + "=" + fieldName + ";");
	}

	public void addGetterAndSetter(String fieldName, String type) {
		this.addGetter(fieldName, type);
		this.addSetter(fieldName, type);
	}

	public static String[] classNameSubPackage(String className) {
		int index = className.lastIndexOf(".");
		if (index != -1) {
			String[] strArr = new String[2];
			strArr[0] = className.substring(0, index);
			strArr[1] = className.substring(index + 1);
			return strArr;
		}
		return new String[] { "", className };
	}

	public static String classNameSubName(String className) {
		int index = className.lastIndexOf(".");
		if (index != -1) {
			return className.substring(index + 1);
		}
		return className;
	}

	public void buider(File file) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		// 打包
		if (StringUtils.isNotEmpty(packageName)) {
			bw.write("package ");
			bw.write(packageName);
			bw.write(";");
			bw.write("\r\n");
		}
		// 导入包
		for (String packageName : packages) {
			bw.write("import ");
			bw.write(packageName);
			bw.write(";");
			bw.write("\r\n");
		}
		if (classDescription != null) {
			String description = "/**\r\n*" + classDescription + "\r\n*/";
			bw.write(description);
			bw.write("\r\n");
		}
		// 类
		for (String annoco : this.annotations) {
			bw.write("@" + annoco + NEWLINE);
		}
		if (isInterface) {
			bw.write(modify + " interface ");
		} else {
			bw.write(modify + " class ");
		}
		bw.write(clzName);
		if (this.superClassName != null) {
			bw.write(" extends " + this.superClassName);
		}
		if (interfaces.size() > 0) {
			bw.write(" implements ");
			String str = "";
			for (String interaceName : interfaces) {
				str += interaceName + ",";
			}
			str = str.substring(0, str.length() - 1);
			bw.write(str);
		}
		bw.write("{");
		bw.write("\r\n");
		int cnt = 0;
		// 字段
		for (String field : fields) {
			String description = stepDescriptions.get(cnt);
			if (description != null) {
				bw.write(description);
				bw.write("\r\n");
			}
			bw.write("\t");
			bw.write(field);
			bw.write("\r\n");
			cnt++;
		}
		cnt = 10000000;
		// 方法
		for (String method : methods.keySet()) {
			String description = stepDescriptions.get(cnt);
			if (description != null) {
				bw.write(description);
				bw.write("\r\n");
			}
			String m = methods.get(method);
			bw.write("\t");
			bw.write(method);
			if (m != null) {
				bw.write("{");
				bw.write("\r\n");
				bw.write("\t\t");
				bw.write(m);
				bw.write("\r\n");
				bw.write("\t}");
			} else {
				bw.write(";");
			}
			bw.write("\r\n");
			cnt++;
		}
		bw.write("}");
		bw.flush();
		bw.close();
	}

	public void setModify(String modify) {
		this.modify = modify;
	}
}
