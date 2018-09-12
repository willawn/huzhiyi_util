package com.huzhiyi.json;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Date;

import com.huzhiyi.util.BeanUtils;
import com.huzhiyi.util.StringUtils;

/**
 * 
 * @ClassName: SimpleJsonObject
 * @Description: TODO(������)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class SimpleJsonObject {

	public static String toJsonString(Object obj) {
		StringBuilder sb = new StringBuilder();
		toJsonString(obj, sb);
		return sb.toString();
	}

	public static void toJsonString(Object obj, StringBuilder sb) {
		if (obj == null) {
			sb.append("null");
			return;
		}
		Class clz = obj.getClass();
		// ����ǻ�����������
		if (clz == String.class) {
			sb.append("'");
			sb.append(obj);
			sb.append("'");
			return;
		}
		// ��������
		else if (BeanUtils.isPrimitive(clz)) {
			sb.append(obj);
			return;
		}
		// ��������
		else if (Date.class.isAssignableFrom(clz)) {
			sb.append("'");
			sb.append(((Date) obj).toGMTString());
			sb.append("'");
			return;
		}
		// �������
		else if (Collection.class.isAssignableFrom(clz)) {
			SimpleJsonArray.toJsonString(((Collection) obj), sb);
			return;
		}
		// ���������
		else if (clz.getName().startsWith("[Ljava")) {
			SimpleJsonArray.toJsonString(((Object[]) obj), sb);
			return;
		}

		sb.append("{");

		Method[] methods = clz.getMethods();
		for (Method method : methods) {
			String name = method.getName();
			if (method.getModifiers() == Modifier.PUBLIC && name.startsWith("get")) {
				Class returnType = method.getReturnType();
				try {
					Object val = method.invoke(obj);
					sb.append(StringUtils.subStrFirstLowerCase(name, 3));
					sb.append("=");
					if (val == null) {
						sb.append("null,");
						continue;
					}
					// ����ǻ�����������
					if (BeanUtils.isPrimitive(returnType)) {
						sb.append(val);
					}
					// ��������
					else if (Date.class.isAssignableFrom(returnType)) {
						sb.append("'");
						sb.append(((Date) val).toGMTString());
						sb.append("'");
					}
					// �������
					else if (Collection.class.isAssignableFrom(returnType)) {
						sb.append("[]");
					} else {
						sb.append("'");
						sb.append(val);
						sb.append("'");
					}
					sb.append(",");
				} catch (Exception e) {
				}

			}
		}
		int index = sb.lastIndexOf(",");
		if (index + 1 == sb.length()) {
			sb.deleteCharAt(index);
		}
		sb.append("}");
	}

}
