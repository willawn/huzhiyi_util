/*
 * @(#)Condition.java 1.0 2010/07/25
 *
 * Copyright 2010 雅菲讯 , Inc. All rights reserved.
 */
package com.huzhiyi.comm;

import java.io.Serializable;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Property;

/**
 * 
 * @ClassName: Condition
 * @Description: TODO(Condition 数据查询条件)
 *               <p>
 * @author willter
 * @date Jul 25, 2010
 * @version V1.0
 * 
 */
public final class Condition implements Serializable {
	private static final long serialVersionUID = 6932480108101552006L;
	private static final int EQUAL = 0;// 等于
	private static final int LESS = 1;// 小于
	private static final int GREAT = 3;// 大于
	private static final int LIKE = 4;// 相似

	private static final int PROPERTY = 5;
	private static final int BETWEEN = 6;

	private String property;
	private Object value;
	private Object value2;
	private int op;

	private Condition() {
	}

	private Condition(String property, Object value, int op) {
		this.property = property;
		this.value = value;
		this.op = op;
	}

	/**
	 * 相等
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static Condition eq(String property, Object value) {
		return new Condition(property, value, EQUAL);
	}

	/**
	 * 小于或等于
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static Condition le(String property, Object value) {
		return new Condition(property, value, LESS & EQUAL);
	}

	/**
	 * 大于或等于
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static Condition ge(String property, Object value) {
		return new Condition(property, value, GREAT & EQUAL);
	}

	/**
	 * 小于
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static Condition lt(String property, Object value) {
		return new Condition(property, value, LESS);
	}

	/**
	 * 大于
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static Condition gt(String property, Object value) {
		return new Condition(property, value, GREAT);
	}

	/**
	 * 相似
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static Condition like(String property, Object value) {
		return new Condition(property, value, LIKE);
	}

	/**
	 * 比较属性相等
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static Condition eqProperty(String property, Object value) {
		return new Condition(property, value, EQUAL & PROPERTY);
	}

	/**
	 * 比较属性小于或等于
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static Condition leProperty(String property, Object value) {
		return new Condition(property, value, LESS & PROPERTY);
	}

	/**
	 * 比较属性大于或等于
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static Condition geProperty(String property, Object value) {
		return new Condition(property, value, GREAT & EQUAL & PROPERTY);
	}

	/**
	 * 比较属性小于
	 * 
	 * @param property
	 * @param property2
	 * @return
	 */
	public static Condition ltProperty(String property, String property2) {
		return new Condition(property, property, LESS & PROPERTY);
	}

	/**
	 * 比较属性大于
	 * 
	 * @param property
	 * @param property2
	 * @return
	 */
	public static Condition gtProperty(String property, String property2) {
		return new Condition(property, property2, GREAT & PROPERTY);
	}

	/**
	 * 两值间
	 * 
	 * @param property
	 * @param value
	 * @param value2
	 * @return
	 */
	public static Condition between(String property, Object value, Object value2) {
		Condition c = new Condition(property, value, BETWEEN);
		c.value2 = value2;
		return c;
	}

	/**
	 * 转化为Hibernate的条件
	 * 
	 * @return
	 */
	public Criterion toCrieria() {
		Property p = Property.forName(property);
		if (op == EQUAL) {
			return p.eq(value);
		} else if (op == (LESS & EQUAL)) {
			return p.le(value);
		} else if (op == GREAT) {
			return p.gt(value);
		} else if (op == (GREAT & EQUAL)) {
			return p.ge(value);
		} else if (op == LIKE) {
			return p.like(value);
		} else if (op == (LESS & PROPERTY)) {
			return p.ltProperty((String) value);
		} else if (op == (GREAT & PROPERTY)) {
			return p.gtProperty((String) value);
		} else if (op == BETWEEN) {
			return p.between(value, value2);
		}
		return null;
	}
}
