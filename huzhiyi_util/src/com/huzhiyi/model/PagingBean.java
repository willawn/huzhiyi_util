/*
 * @(#)PagingBean.java	1.0 2010/07/25
 *
 * Copyright 2010 雅菲讯 , Inc. All rights reserved.
 */
package com.huzhiyi.model;

import com.huzhiyi.model.abstraction.ValueSection;

/**
 * 
 * @ClassName: PagingBean
 * @Description: TODO(分页功能辅助类)
 *               <p>
 * @author willter
 * @date 2010-3-13
 * 
 */
public class PagingBean {
	private int maxPages;// 总页数

	private int maxRows;// 总行数

	private int currentPage = 1;// 当前页,默认为起始页为第一页

	private int pageRows = DEF_COUNT;// 每页行数
	
	private int beginRow = 0;// 起始行索引，默认第一行

	private int action = 1;

	public static final int DEF_COUNT = 10;
	
	protected static final int ACTION_FIRST = -1;

	protected static final int ACTION_PREVIOUS = -2;

	protected static final int ACTION_NEXT = -3;

	protected static final int ACTION_LAST = -4;

	/**
	 * 检查页码 checkPageNo
	 * 
	 * @param pageNo
	 * @return if pageNo==null or pageNo<1 then return 1 else return pageNo
	 */
	public static int cpn(Integer pageNo) {
		return (pageNo == null || pageNo < 1) ? 1 : pageNo;
	}
	
	/**
	 * @Title: cps
	 * @Description: 检查每页行数
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
	 * 默认构造方法
	 * 
	 */
	public PagingBean() {

	}

	/**
	 * 默认每页为10行
	 * 
	 * @param maxRows
	 *            总行数
	 */
	public PagingBean(int maxRows) {
		this.maxRows = maxRows;
		maxPageCount();
	}

	/**
	 * 构造方法
	 * 
	 * @param maxRows
	 *            总行数
	 * @param pageRows
	 *            每页行数
	 */
	public PagingBean(int maxRows, int pageRows) {
		this.maxRows = maxRows;
		this.pageRows = pageRows;
		maxPageCount();
	}

	/**
	 * 设置每页多少行
	 * 
	 * @param rows
	 */
	public void setPageRows(int rows) {
		this.pageRows = rows;
		maxPageCount();
	}

	/**
	 * 设置总行数
	 * 
	 * @param maxRows
	 */
	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
		maxPageCount();
	}

	//
	// 计算总页数
	//
	private void maxPageCount() {
		maxPages = ((maxRows + pageRows) - 1) / pageRows;
	}

	/**
	 * 获取行的起始位置
	 * 
	 * @return
	 */
	public int getBeginRow() {

		return (currentPage - 1) * pageRows;
	}

	/**
	 * 设置行的起始位置
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
	 * 获取行的结束位置
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
	 * 获取行起始位置和结束位置
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
	 * 获取当前页
	 * 
	 * @return 当前页
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 检索是否有上一页
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
	 * 检索是否有下一页
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
	 * 执行最后的翻页动作，更新分页模型的当前页值
	 */
	public void update() {
		switch (action) {
			case ACTION_PREVIOUS:
				if (hasPrevious()) {
					currentPage--;
				} else {
					throw new RuntimeException("当前页数为:" + currentPage + ",不能向前翻页");
				}
				break;
			case ACTION_NEXT:
				if (hasNext()) {
					currentPage++;
				} else {
					throw new RuntimeException("总页数为:" + maxPages + ",当前页数为:" + currentPage + ",不能向后翻页");
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
	 * 向前翻页
	 * 
	 * @return 是否翻页成功
	 */
	public void previous() {
		action = ACTION_PREVIOUS;
	}

	/**
	 * 向后翻页
	 * 
	 * @return 是否翻页成功
	 */
	public void next() {
		action = ACTION_NEXT;
	}

	/**
	 * 翻到首页
	 * 
	 */
	public void first() {
		action = ACTION_FIRST;
	}

	/**
	 * 翻到末页
	 * 
	 */
	public void last() {
		action = ACTION_LAST;
	}

	/**
	 * 设置当前页数
	 * 
	 * @param pageNumber
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 获取总页数
	 * 
	 * @return
	 */
	public int getMaxPages() {
		return maxPages;
	}

	/**
	 * 获取总行数
	 * 
	 * @return
	 */
	public int getMaxRows() {
		return maxRows;
	}

	/**
	 * 获取每页的行数
	 * 
	 * @return
	 */
	public int getPageRows() {
		return pageRows;
	}

	/**
	 * 设置action动作
	 * 
	 * @Title: setAction
	 * @author willter
	 * @date 2010-9-10
	 * @return void 返回类型
	 * @throws
	 */
	public void setAction(int action) {
		this.action = action;
	}
	
	public int getAction() {
		return action;
	}
}