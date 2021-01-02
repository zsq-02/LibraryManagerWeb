package com.ischoolbar.programmer.entity;

import com.ischoolbar.programmer.annotation.Column;


/**
 * �û�ʵ��
 * @author llq
 *
 */
public class User extends BaseEntity{
	@Column(name="username")
	private String username;
	@Column(name="password")
	private String password;
	@Column(name="type")
	private int type = 2;//�û�����
	@Column(name="status")
	private int status = 1;//״̬1��������0��ʾ����
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
