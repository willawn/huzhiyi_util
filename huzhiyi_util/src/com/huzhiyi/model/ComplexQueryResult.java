package com.huzhiyi.model;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @ClassName: ComplexQueryResult
 * @Description: TODO(描述类)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class ComplexQueryResult {

	public ComplexQueryResult(List<String> nameList, List<Object[]> result) {
		this.nameList = nameList;
		this.result = result;
	}

	public ComplexQueryResult(String[] names, List<Object[]> result) {
		nameList = Arrays.asList(names);
		this.result = result;
	}

	private List<String> nameList;
	private List<Object[]> result;

	public int getResultIndex(String name) {
		return nameList.indexOf(name);
	}

	public Object getResultValue(Object[] rs, String name) {
		int index = nameList.indexOf(name);
		if (index == -1) {
			throw new RuntimeException("未包含属性:" + name + "的值.");
		}
		return rs[index];
	}

	public Object getResultValue(int resultIndex, String name) {
		Object[] rs = result.get(resultIndex);
		return getResultValue(rs, name);
	}

	public List<String> getNameList() {
		return nameList;
	}

	public List<Object[]> getResult() {
		return result;
	}
}
