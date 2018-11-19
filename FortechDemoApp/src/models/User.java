package models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="user", schema="fortech-demo-app")
public class User {
	@Id
    @Column(name = "user_id")
	private int id;
	@Column(name="username")
	private String username;
	@Column(name="password")
	private String password;
	@OneToMany(mappedBy="author")
	private List<Campground> campgrounds;
	@OneToMany(mappedBy="commentAuthor")
	private List<Comment> comments;
	@Column(name="enabled")
	private boolean enabled;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "users")
	private Set<UserRole> userRole = new HashSet<UserRole>(0);
	
	public User() {
	}

	public User(String username, String password, boolean enabled) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public User(String username, String password, 
		boolean enabled, Set<UserRole> userRole) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.userRole = userRole;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
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
	
	public List<Campground> getCampgrounds() {
		return campgrounds;
	}
	
	public void setCampgrounds(List<Campground> campgrounds) {
		this.campgrounds = campgrounds;
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Set<UserRole> getUserRole() {
		return this.userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}
}
