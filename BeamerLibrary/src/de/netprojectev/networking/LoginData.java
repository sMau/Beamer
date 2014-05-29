package de.netprojectev.networking;

import java.io.Serializable;

public class LoginData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5784399877829356516L;
	private final String alias;
	private final String key;

	public LoginData(String alias, String key) {
		this.alias = alias;
		this.key = key;
	}

	public String getAlias() {
		return alias;
	}

	public String getKey() {
		return key;
	}

}
