package com.huzhiyi.model;

import java.util.Properties;

/**
 * 
 * @ClassName: JdbcConnectionModel
 * @Description: TODO(√Ë ˆ¿‡)
 *               <p>
 * @author willter
 * @date Jul 26, 2010
 * @version V1.0
 * 
 */
public class JdbcConnectionModel {
	private String url;
	private Properties security = new Properties();

	public String getPassword() {
		return security.getProperty("password");
	}

	public void setPassword(String password) {
		security.put("password", password);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return security.getProperty("user");
	}

	public void setUser(String user) {
		security.put("user", user);
	}

	public Properties getSecurity() {
		return security;
	}
}