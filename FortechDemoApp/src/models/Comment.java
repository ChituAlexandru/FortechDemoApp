package models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="comments", schema="fortech-demo-app")
public class Comment {
	@Id
    @Column(name = "comment_id")
	private int id;
	@Column(name="text")
	private String text;
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinColumn(name="author_id")
	private User commentAuthor;
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	@JoinColumn(name="campground_id")
	private Campground campground;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public User getCommentAuthor() {
		return commentAuthor;
	}
	
	public void setCommentAuthor(User commentAuthor) {
		this.commentAuthor = commentAuthor;
	}
	
	public Campground getCampground() {
		return campground;
	}
	
	public void setCampground(Campground campground) {
		this.campground = campground;
	}
}
