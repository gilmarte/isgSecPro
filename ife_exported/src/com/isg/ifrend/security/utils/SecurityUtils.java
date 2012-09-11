package com.isg.ifrend.security.utils;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.isg.ifrend.security.bean.SaltedUser;
import com.isg.ifrend.security.dao.CustomJdbcDaoImpl;
import com.isg.ifrend.security.service.SecurityServiceLocator;

public final class SecurityUtils {

	private static SecurityUtils securityUtilInstance;
	private CustomJdbcDaoImpl securityDAO = SecurityServiceLocator.getCustomJdbcDaoImpl();
	
	public static SecurityUtils getSecurityUtilsInstance() {
		if (securityUtilInstance == null) {
			securityUtilInstance = new SecurityUtils();
		}
		return securityUtilInstance;
	}
	
	public boolean createUserCredentials(String userID, String defaultPassword) throws Exception {
		if (userID.isEmpty()) {
			throw new NullPointerException("User ID provided is empty!");
		}
		else if(defaultPassword.isEmpty()) {
			throw new NullPointerException("Default password provided is empty!");
		}
		securityDAO.createUserCredentials(userID, defaultPassword);
		return true;
	}
	
	public boolean deleteUserCredentials(String userID) throws Exception {
		if (userID.isEmpty()) {
			throw new NullPointerException("User ID provided is empty!");
		}
		securityDAO.deleteUserCredentials(userID);
		return true;
	}
	
	public boolean updatePassword(String userName, String newPassword) {
		try {
			securityDAO.changePassword(userName, newPassword);
			securityDAO.enableUserFlag(userName);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isUserFlagged() {
		try {
			SaltedUser user = (SaltedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return user.isFlag();
		}
		catch (ClassCastException e) {
			return false;
		}
	}
	
	public boolean isUserGBCR() {
		try {
			User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(user.getAuthorities());
			if(authorities.contains(new GrantedAuthorityImpl("ROLE_GBCR_MAKER")) ||
					authorities.contains(new GrantedAuthorityImpl("ROLE_GBCR_CHECKER"))) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public boolean isUserSecurityAdmin() {
		try {
			User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(user.getAuthorities());
			if(authorities.contains(new GrantedAuthorityImpl("ROLE_ADMIN_MAKER")) ||
					authorities.contains(new GrantedAuthorityImpl("ROLE_ADMIN_CHECKER"))) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public String getUserName() {
		try {
			User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return user.getUsername();
		}
		catch (ClassCastException e) {
			return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		}
	}
	
	public boolean activateUser(String userID) {
		try {
			securityDAO.enableUser(userID);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deactivateUser(String userID) {
		try {
			securityDAO.disableUser(userID);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean hasMakerRole(String url) {
		// TODO: Define Authorities at the start of the application
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(user.getAuthorities());
		if(url.contains("gbcr")) {
			if(authorities.contains(new GrantedAuthorityImpl("ROLE_GBCR_MAKER"))) {
				return true;
			}
		}
		else if(url.contains("securityadmin")) {
			if(authorities.contains(new GrantedAuthorityImpl("ROLE_ADMIN_MAKER"))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasCheckerRole(String url) {
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(user.getAuthorities());
		if(url.contains("gbcr")) {
			if(authorities.contains(new GrantedAuthorityImpl("ROLE_GBCR_CHECKER"))) {
				return true;
			}
		}
		else if(url.contains("securityadmin")) {
			if(authorities.contains(new GrantedAuthorityImpl("ROLE_ADMIN_CHECKER"))) {
				return true;
			}
		}
		return false;
	}
	
//	public boolean canAuthorizeElement() {
//		return true;
//	}
//	
//	public boolean canAuthorizeCriteria() {
//		return true;
//	}
//	
//	public boolean canAuthorizeMli() {
//		return true;
//	}
//	
//	public boolean canAuthorizeScript() {
//		return true;
//	}
//	
//	public boolean canAuthorizeUserProfile() {
//		return true;
//	}
//	
//	public boolean canAuthorizeUserGroups() {
//		return true;
//	}
//	
//	public boolean canAuthorizeUserOrganization() {
//		return true;
//	}
}
