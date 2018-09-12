package com.huzhiyi.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 
 * @ClassName: DebugUtils
 * @Description: TODO(������)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class DebugUtils {
	public static StringBuilder getBeanInfo(Object obj) {
		StringBuilder buf = new StringBuilder();
		Class clz = obj.getClass();
		Field[] fields = clz.getFields();
		buf.append("***********�ֶ���Ϣ***********\r\n");
		for (Field field : fields) {
			buf.append("����:");
			buf.append(field.getName());
			buf.append(" ֵ:");
			try {
				buf.append(field.get(obj));
			} catch (Exception e) {
			}
		}
		buf.append("***********����***********\r\n");
		Method[] methods = clz.getMethods();
		for (Method method : methods) {
			buf.append("����:");
			buf.append(method.getName());
			buf.append(" ����:");
			buf.append("[");
			buf.append("");
			Object val = "";
			Class[] paramTypes = method.getParameterTypes();
			if (paramTypes.length > 0) {
				for (Class paramType : paramTypes) {
					buf.append(paramType.getName());
					buf.append(",");
				}
				buf.deleteCharAt(buf.length() - 1);
			} else {
				try {
					val = method.invoke(obj);
				} catch (Exception e) {
				}
			}
			buf.append("]");
			buf.append(" ֵ:");
			buf.append(val);
			buf.append("\r\n");
		}
		return buf;
	}
}
