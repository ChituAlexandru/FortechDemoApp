package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="user_roles", schema="fortech-demo-app",
uniqueConstraints = @UniqueConstraint(
		columnNames = { "role", "user_id" }))
public class UserRole {
	@Id
	@Column(name="user_role_id")
	private int userRoleId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User users;
	
	@Column(name="role")
	private String role;
	
	
	public UserRole() {
	}

	public UserRole(User users, String role) {
		this.users = users;
		this.role = role;
	}
	
	public Integer getUserRoleId() {
		return this.userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}
	
	public User getUsers() {
		return this.users;
	}

	public void setUsers(User users) {
		this.users = users;
	}
	
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
