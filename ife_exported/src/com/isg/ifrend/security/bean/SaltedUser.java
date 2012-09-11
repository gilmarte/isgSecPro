package com.isg.ifrend.security.bean;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SaltedUser extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String salt;
	private boolean flag;
	
	public SaltedUser(String username, String password,
	                    boolean enabled, boolean accountNonExpired,
	                    boolean credentialsNonExpired, boolean accountNonLocked,
	                    List<GrantedAuthority> authorities, String salt, boolean flag) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		this.salt = salt;
	    this.flag = flag;
	}
	
	public String getSalt() {
		return salt;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
	  
	public boolean isFlag() {
		return flag;
	}
	
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
