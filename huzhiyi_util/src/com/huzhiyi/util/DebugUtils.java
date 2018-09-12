package com.huzhiyi.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 
 * @ClassName: DebugUtils
 * @Description: TODO(描述类)
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
		buf.append("***********字段信息***********\r\n");
		for (Field field : fields) {
			buf.append("名字:");
			buf.append(field.getName());
			buf.append(" 值:");
			try {
				buf.append(field.get(obj));
			} catch (Exception e) {
			}
		}
		buf.append("***********方法***********\r\n");
		Method[] methods = clz.getMethods();
		for (Method method : methods) {
			buf.append("名字:");
			buf.append(method.getName());
			buf.append(" 参数:");
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
			buf.append(" 值:");
			buf.append(val);
			buf.append("\r\n");
		}
		return buf;
	}
}
