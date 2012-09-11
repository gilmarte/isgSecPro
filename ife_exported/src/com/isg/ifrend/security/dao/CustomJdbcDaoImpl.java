package com.isg.ifrend.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import com.isg.ifrend.security.bean.SaltedUser;


public class CustomJdbcDaoImpl extends JdbcDaoImpl {
	private PasswordEncoder passwordEncoder;
	private SaltSource saltSource;
	private int saltLength = 8;
	
	private String createUserStatement;
	private String deleteUserStatement;
	private String changePasswordStatement;
	private String enableUserStatement;
	private String enableUserFlagStatement;

	public void createUserCredentials(String userName, String password) {
		String salt = RandomStringUtils.randomAlphanumeric(saltLength);
		String encodedPassword = passwordEncoder.encodePassword(password, salt);
		StringBuffer sb = new StringBuffer(salt.substring(0, 4));
		sb.append(encodedPassword).append(salt.substring(4, 8));
		
		getJdbcTemplate().update(createUserStatement,
				new Object[]{userName, sb.toString(), 0, 1});
	}
	
	public void deleteUserCredentials(String userName) {
		getJdbcTemplate().update(deleteUserStatement,
				new Object[]{userName});
	}
	
	public void changePassword(String username, String password) {
		SaltedUser user = (SaltedUser)loadUserByUsername(username);
		String salt = RandomStringUtils.randomAlphanumeric(saltLength);
		user.setSalt(salt);
		String encodedPassword = passwordEncoder.encodePassword(password, salt);
		StringBuffer sb = new StringBuffer(salt.substring(0, 4));
		sb.append(encodedPassword).append(salt.substring(4, 8));
		getJdbcTemplate().update(changePasswordStatement,
				new Object[]{sb.toString(), username});
	}
	
	public boolean validatePassword(String password, String passwordProvided, String salt) {
		return passwordEncoder.isPasswordValid(password, passwordProvided, salt);
	}
	
	public void enableUser(String username) {
		getJdbcTemplate().update(enableUserStatement,
				new Object[] {"1", username});
	}

	public void disableUser(String username) {
		getJdbcTemplate().update(enableUserStatement,
				new Object[] {"0", username});
	}
	
	public void enableUserFlag(String username) {
		getJdbcTemplate().update(enableUserFlagStatement,
				new Object[]{"1", username});
	}

	public void disableUserFlag(String username) {
		getJdbcTemplate().update(enableUserFlagStatement,
				new Object[] {"0", username});
	}

	@Override
	protected UserDetails createUserDetails(String username,
			UserDetails userFromUserQuery,
			List<GrantedAuthority> combinedAuthorities) {
		String returnUsername = userFromUserQuery.getUsername();
		if (!isUsernameBasedPrimaryKey()) {
			returnUsername = username;
		}
		return new SaltedUser(returnUsername, userFromUserQuery.getPassword(),
				userFromUserQuery.isEnabled(), true, true, true,
				combinedAuthorities, ((SaltedUser) userFromUserQuery).getSalt(),
				((SaltedUser) userFromUserQuery).isFlag());
	}

	@Override
	protected List<UserDetails> loadUsersByUsername(String username) {
		return getJdbcTemplate().query(getUsersByUsernameQuery(),
				new String[] { username }, new RowMapper<UserDetails>() {
					public UserDetails mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						String username = rs.getString(1);
						StringBuffer sb = new StringBuffer(rs.getString(2));
						String password = sb.substring(4, 36);
						String salt = sb.insert(4, sb.substring(36)).substring(0, 8);
						boolean enabled = rs.getBoolean(3);
						boolean flag = rs.getBoolean(4);
						return new SaltedUser(username, password, enabled,
								true, true, true,
								AuthorityUtils.NO_AUTHORITIES, salt, flag);
					}
				});
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public SaltSource getSaltSource() {
		return saltSource;
	}

	public void setSaltSource(SaltSource saltSource) {
		this.saltSource = saltSource;
	}

	public void setSaltLength(int saltLength) {
		this.saltLength = saltLength;
	}

	public int getSaltLength() {
		return saltLength;
	}

	public String getCreateUserStatement() {
		return createUserStatement;
	}

	public void setCreateUserStatement(String createUserStatement) {
		this.createUserStatement = createUserStatement;
	}

	public String getDeleteUserStatement() {
		return deleteUserStatement;
	}

	public void setDeleteUserStatement(String deleteUserStatement) {
		this.deleteUserStatement = deleteUserStatement;
	}

	public String getChangePasswordStatement() {
		return changePasswordStatement;
	}

	public void setChangePasswordStatement(String changePasswordStatement) {
		this.changePasswordStatement = changePasswordStatement;
	}

	public String getEnableUserStatement() {
		return enableUserStatement;
	}

	public void setEnableUserStatement(String enableUserStatement) {
		this.enableUserStatement = enableUserStatement;
	}

	public String getEnableUserFlagStatement() {
		return enableUserFlagStatement;
	}

	public void setEnableUserFlagStatement(String enableUserFlagStatement) {
		this.enableUserFlagStatement = enableUserFlagStatement;
	}

}
