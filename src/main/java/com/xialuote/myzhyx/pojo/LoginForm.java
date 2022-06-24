package com.xialuote.myzhyx.pojo;

import lombok.Data;

/**
 * 用户登录表单数据
 */
@Data
public class LoginForm {
	private String username;
	
	private String password;
	
	private String verifiCode;
	
	private Integer userType;
}
