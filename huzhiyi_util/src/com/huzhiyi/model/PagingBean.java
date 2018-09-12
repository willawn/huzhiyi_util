/*
 * @(#)PagingBean.java	1.0 2010/07/25
 *
 * Copyright 2010 �ŷ�Ѷ , Inc. All rights reserved.
 */
package com.huzhiyi.model;

import com.huzhiyi.model.abstraction.ValueSection;

/**
 * 
 * @ClassName: PagingBean
 * @Description: TODO(��ҳ���ܸ�����)
 *               <p>
 * @author willter
 * @date 2010-3-13
 * 
 */
public class PagingBean {
	private int maxPages;// ��ҳ��

	private int maxRows;// ������

	private int currentPage = 1;// ��ǰҳ,Ĭ��Ϊ��ʼҳΪ��һҳ

	private int pageRows = DEF_COUNT;// ÿҳ����
	
	private int beginRow = 0;// ��ʼ��������Ĭ�ϵ�һ��

	private int action = 1;

	public static final int DEF_COUNT = 10;
	
	protected static final int ACTION_FIRST = -1;

	protected static final int ACTION_PREVIOUS = -2;

	protected static final int ACTION_NEXT = -3;

	protected static final int ACTION_LAST = -4;

	/**
	 * ���ҳ�� checkPageNo
	 * 
	 * @param pageNo
	 * @return if pageNo==null or pageNo<1 then return 1 else return pageNo
	 */
	public static int cpn(Integer pageNo) {
		return (pageNo == null || pageNo < 1) ? 1 : pageNo;
	}
	
	/**
	 * @Title: cps
	 * @Description: ���ÿҳ����
	 * 		<p>
	 * @author willter
	 * @date 2012-9-23
	 * 		<p>
	 * @param pageSize
	 * @return
	 */
	public static int cps(Integer pageSize) {
		return (pageSize == null || pageSize < 1) ? DEF_COUNT : pageSize;
	}
	
	/**
	 * Ĭ�Ϲ��췽��
	 * 
	 */
	public PagingBean() {

	}

	/**
	 * Ĭ��ÿҳΪ10��
	 * 
	 * @param maxRows
	 *            ������
	 */
	public PagingBean(int maxRows) {
		this.maxRows = maxRows;
		maxPageCount();
	}

	/**
	 * ���췽��
	 * 
	 * @param maxRows
	 *            ������
	 * @param pageRows
	 *            ÿҳ����
	 */
	public PagingBean(int maxRows, int pageRows) {
		this.maxRows = maxRows;
		this.pageRows = pageRows;
		maxPageCount();
	}

	/**
	 * ����ÿҳ������
	 * 
	 * @param rows
	 */
	public void setPageRows(int rows) {
		this.pageRows = rows;
		maxPageCount();
	}

	/**
	 * ����������
	 * 
	 * @param maxRows
	 */
	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
		maxPageCount();
	}

	//
	// ������ҳ��
	//
	private void maxPageCount() {
		maxPages = ((maxRows + pageRows) - 1) / pageRows;
	}

	/**
	 * ��ȡ�е���ʼλ��
	 * 
	 * @return
	 */
	public int getBeginRow() {

		return (currentPage - 1) * pageRows;
	}

	/**
	 * �����е���ʼλ��
	 * 
	 */
	public void setBeginRow(int beginRow) {
		this.beginRow = beginRow;
		if (beginRow == 0) {
			currentPage = 1;
		} else {
			int result = (beginRow + 1) % pageRows;
			if (result == 0) {
				currentPage = beginRow / pageRows;
			} else {
				currentPage = beginRow / pageRows + 1;
			}
		}
	}
	
	/**
	 * ��ȡ�еĽ���λ��
	 * 
	 * @return
	 */
	public int getEndRow() {
		int end = currentPage * pageRows;
		if (end > maxRows) {
			return maxRows;
		}
		return end;
	}

	/**
	 * ��ȡ����ʼλ�úͽ���λ��
	 * 
	 * @return
	 */
	public ValueSection<Integer> getPosition() {
		return new ValueSection<Integer>() {
			public Integer getBegin() {
				// TODO Auto-generated method stub
				return getBeginRow();
			}

			public Integer getEnd() {
				return getEndRow();
			}

			public String toString() {
				return "[" + this.getBegin() + "," + this.getEnd() + "]";
			}
		};
	}

	/**
	 * ��ȡ��ǰҳ
	 * 
	 * @return ��ǰҳ
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * �����Ƿ�����һҳ
	 * 
	 * @return
	 */
	public boolean hasPrevious() {
		if (currentPage > 1) {
			return true;
		}
		return false;
	}

	/**
	 * �����Ƿ�����һҳ
	 * 
	 * @return
	 */
	public boolean hasNext() {
		if (currentPage < maxPages) {
			return true;
		}
		return false;
	}

	/**
	 * ִ�����ķ�ҳ���������·�ҳģ�͵ĵ�ǰҳֵ
	 */
	public void update() {
		switch (action) {
			case ACTION_PREVIOUS:
				if (hasPrevious()) {
					currentPage--;
				} else {
					throw new RuntimeException("��ǰҳ��Ϊ:" + currentPage + ",������ǰ��ҳ");
				}
				break;
			case ACTION_NEXT:
				if (hasNext()) {
					currentPage++;
				} else {
					throw new RuntimeException("��ҳ��Ϊ:" + maxPages + ",��ǰҳ��Ϊ:" + currentPage + ",�������ҳ");
				}
				break;
			case ACTION_FIRST:
				currentPage = 1;
				break;
			case ACTION_LAST:
				currentPage = maxPages;
				break;
			default:
				if (action > maxPages) {
					break;
				}
				currentPage = action;
		}
	}

	/**
	 * ��ǰ��ҳ
	 * 
	 * @return �Ƿ�ҳ�ɹ�
	 */
	public void previous() {
		action = ACTION_PREVIOUS;
	}

	/**
	 * ���ҳ
	 * 
	 * @return �Ƿ�ҳ�ɹ�
	 */
	public void next() {
		action = ACTION_NEXT;
	}

	/**
	 * ������ҳ
	 * 
	 */
	public void first() {
		action = ACTION_FIRST;
	}

	/**
	 * ����ĩҳ
	 * 
	 */
	public void last() {
		action = ACTION_LAST;
	}

	/**
	 * ���õ�ǰҳ��
	 * 
	 * @param pageNumber
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * ��ȡ��ҳ��
	 * 
	 * @return
	 */
	public int getMaxPages() {
		return maxPages;
	}

	/**
	 * ��ȡ������
	 * 
	 * @return
	 */
	public int getMaxRows() {
		return maxRows;
	}

	/**
	 * ��ȡÿҳ������
	 * 
	 * @return
	 */
	public int getPageRows() {
		return pageRows;
	}

	/**
	 * ����action����
	 * 
	 * @Title: setAction
	 * @author willter
	 * @date 2010-9-10
	 * @return void ��������
	 * @throws
	 */
	public void setAction(int action) {
		this.action = action;
	}
	
	public int getAction() {
		return action;
	}
}