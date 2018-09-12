/*
 * @(#)Condition.java 1.0 2010/07/25
 *
 * Copyright 2010 �ŷ�Ѷ , Inc. All rights reserved.
 */
package com.huzhiyi.comm;

import java.io.Serializable;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Property;

/**
 * 
 * @ClassName: Condition
 * @Description: TODO(Condition ���ݲ�ѯ����)
 *               <p>
 * @author willter
 * @date Jul 25, 2010
 * @version V1.0
 * 
 */
public final class Condition implements Serializable {
	private static final long serialVersionUID = 6932480108101552006L;
	private static final int EQUAL = 0;// ����
	private static final int LESS = 1;// С��
	private static final int GREAT = 3;// ����
	private static final int LIKE = 4;// ����

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
	 * ���
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static Condition eq(String property, Object value) {
		return new Condition(property, value, EQUAL);
	}

	/**
	 * С�ڻ����
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static Condition le(String property, Object value) {
		return new Condition(property, value, LESS & EQUAL);
	}

	/**
	 * ���ڻ����
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static Condition ge(String property, Object value) {
		return new Condition(property, value, GREAT & EQUAL);
	}

	/**
	 * С��
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static Condition lt(String property, Object value) {
		return new Condition(property, value, LESS);
	}

	/**
	 * ����
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static Condition gt(String property, Object value) {
		return new Condition(property, value, GREAT);
	}

	/**
	 * ����
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static Condition like(String property, Object value) {
		return new Condition(property, value, LIKE);
	}

	/**
	 * �Ƚ��������
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static Condition eqProperty(String property, Object value) {
		return new Condition(property, value, EQUAL & PROPERTY);
	}

	/**
	 * �Ƚ�����С�ڻ����
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static Condition leProperty(String property, Object value) {
		return new Condition(property, value, LESS & PROPERTY);
	}

	/**
	 * �Ƚ����Դ��ڻ����
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static Condition geProperty(String property, Object value) {
		return new Condition(property, value, GREAT & EQUAL & PROPERTY);
	}

	/**
	 * �Ƚ�����С��
	 * 
	 * @param property
	 * @param property2
	 * @return
	 */
	public static Condition ltProperty(String property, String property2) {
		return new Condition(property, property, LESS & PROPERTY);
	}

	/**
	 * �Ƚ����Դ���
	 * 
	 * @param property
	 * @param property2
	 * @return
	 */
	public static Condition gtProperty(String property, String property2) {
		return new Condition(property, property2, GREAT & PROPERTY);
	}

	/**
	 * ��ֵ��
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
	 * ת��ΪHibernate������
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
