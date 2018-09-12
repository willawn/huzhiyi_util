package com.huzhiyi.model;

/**
 * 
 * @ClassName: TypeNamingModel
 * @Description: TODO(√Ë ˆ¿‡)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class TypeNamingModel {
	public TypeNamingModel() {
	}

	public TypeNamingModel(String name, Class type) {
		this.name = name;
		this.type = type;
	}

	private Class type;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		return this.name.equals(((TypeNamingModel) obj).name);
	}
}