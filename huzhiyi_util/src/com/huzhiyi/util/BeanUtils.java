/*
 * @(#)BeanUtil.java 1.0 2010/07/25
 *
 * Copyright 2010 雅菲讯 , Inc. All rights reserved.
 */
package com.huzhiyi.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.huzhiyi.model.TypeNamingModel;

/**
 * 
 * @ClassName: BeanUtils
 * @Description: TODO(javabean工具类)
 * 		<p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 *
 */
public class BeanUtils {
	/**
	 * 复制对象属性
	 * 
	 * @param <T>
	 * @param source
	 * @param target
	 * @param properties
	 * @throws Exception
	 */
	public static <T> void copyProperties(T source, T target, String[] properties) throws Exception {
		for (String prop : properties) {
			Class clz = source.getClass();
			String name = prop.substring(0, 1).toUpperCase() + prop.substring(1);
			Method m = clz.getMethod("get".concat(name));
			Object value = m.invoke(source);
			clz.getMethod("set" + name, m.getReturnType()).invoke(target, value);
		}

	}

	/**
	 * 获得对象值
	 * 
	 * @param source
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	public static Object getValue(Object source, String propertyName) throws Exception {
		Class clz = source.getClass();
		String name = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
		Method m = clz.getMethod("get".concat(name));
		Object value = m.invoke(source);
		return value;
	}

	/**
	 * 对象赋值
	 * 
	 * @param source
	 * @param propertyName
	 * @param value
	 * @throws Exception
	 */
	public static void setValue(Object source, String propertyName, Object value) throws Exception {
		Class clz = source.getClass();
		String name = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
		Method[] methods = clz.getMethods();
		for (Method method : methods) {
			if (method.equals("set".concat(name))) {
				method.invoke(source, value);
			}
		}
	}

	/**
	 * 是否是原始数据类型的包装类型
	 * 
	 * @param clz
	 * @return
	 */
	public static boolean isPrimitiveWrapClass(Class clz) {
		try {
			return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 判断是否为基本数据类型或其包装类
	 * 
	 * @param clz
	 * @return
	 */
	public static boolean isPrimitive(Class clz) {
		return clz.isPrimitive() || isPrimitiveWrapClass(clz);
	}

	/**
	 * 是否是集合类型
	 * 
	 * @param clz
	 * @return
	 */
	public static boolean isCollection(Class clz) {
		return List.class.isAssignableFrom(clz) || Set.class.isAssignableFrom(clz);
	}

	/**
	 * 获取所有的Get方法
	 * 
	 * @param clz
	 * @return
	 */
	public static List<TypeNamingModel> findGetterList(Class clz) {
		List<TypeNamingModel> attributeList = new ArrayList<TypeNamingModel>();
		String name = null;
		int index;
		for (Method m : clz.getMethods()) {
			if (m.getModifiers() == Modifier.PUBLIC) {
				name = m.getName();
				index = name.indexOf("get");
				if (index == 0) {
					TypeNamingModel attrInfo = new TypeNamingModel();
					attrInfo.setName(StringUtils.firstToLowerCase(name.substring(3)));
					attrInfo.setType(m.getReturnType());
					attributeList.add(attrInfo);
				} else if ((index = name.indexOf("is")) == 0) {
					TypeNamingModel attrInfo = new TypeNamingModel();
					attrInfo.setName(StringUtils.firstToLowerCase(name.substring(2)));
					attrInfo.setType(m.getReturnType());
					attributeList.add(attrInfo);
				}
			}
		}
		return attributeList;
	}

	/**
	 * 获取所有的Set方法
	 * 
	 * @param clz
	 * @return
	 */
	public static List<TypeNamingModel> findSetterList(Class clz) {
		List<TypeNamingModel> attributeList = new ArrayList<TypeNamingModel>();
		String name = null;
		int index;
		for (Method m : clz.getMethods()) {
			if (m.getModifiers() == Modifier.PUBLIC && m.getParameterTypes().length > 0) {
				name = m.getName();
				if ((index = name.indexOf("set")) == 0) {
					TypeNamingModel attrInfo = new TypeNamingModel();
					attrInfo.setName(StringUtils.firstToLowerCase(name.substring(3)));
					attrInfo.setType(m.getParameterTypes()[0]);
					attributeList.add(attrInfo);
				}
			}
		}
		return attributeList;
	}
}
