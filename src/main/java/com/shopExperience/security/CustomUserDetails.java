package com.shopExperience.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type;
	
	@SuppressWarnings("deprecation")
	public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, int type){
	     super(username,
					password,
					true,
					true,
					true,
					true,
					new GrantedAuthority[] { new GrantedAuthorityImpl("ROLE_USER") });
	     this.type = type;
	}


	public int getType() { return type;}

	public void setType(int type) { this.type = type;}
	}