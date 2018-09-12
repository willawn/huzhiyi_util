package com.huzhiyi.json;

import java.util.Collection;

/**
 * 
 * @ClassName: SimpleJsonArray
 * @Description: TODO(√Ë ˆ¿‡)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class SimpleJsonArray {

	public static String toJsonString(Collection coll) {
		StringBuilder sb = new StringBuilder();
		toJsonString(coll, sb);
		return sb.toString();
	}

	public static String toJsonString(Object[] objArray) {
		StringBuilder sb = new StringBuilder();
		toJsonString(objArray, sb);
		return sb.toString();
	}

	public static void toJsonString(Collection coll, StringBuilder sb) {
		sb.append("[");
		for (Object obj : coll) {
			SimpleJsonObject.toJsonString(obj, sb);
			sb.append(",");
		}
		int index = sb.lastIndexOf(",");
		if (index + 1 == sb.length()) {
			sb.deleteCharAt(index);
		}
		sb.append("]");
	}

	public static void toJsonString(Object[] objArray, StringBuilder sb) {
		sb.append("[");
		for (Object obj : objArray) {
			SimpleJsonObject.toJsonString(obj, sb);
			sb.append(",");
		}
		int index = sb.lastIndexOf(",");
		if (index + 1 == sb.length()) {
			sb.deleteCharAt(index);
		}
		sb.append("]");
	}
}
