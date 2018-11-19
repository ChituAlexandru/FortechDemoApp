package controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import models.Campground;
import models.Comment;
import models.User;
import models.UserRole;

@Controller
@RequestMapping("/comments")
public class CommentController {
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView newComment(int campgroundId, Model model, HttpServletRequest httpRequest, Principal principal) {
		model.addAttribute("contextName", httpRequest.getContextPath());
		Session sessionObj = new Configuration().configure()
				.buildSessionFactory().openSession();
		Campground campground = new Campground();
		UserRole userRole = new UserRole();
		try {
			sessionObj.beginTransaction();
			campground = (Campground) sessionObj.get(Campground.class, campgroundId);
			userRole = (UserRole) sessionObj.get(UserRole.class, 1);
			System.out.println("\n\n\n "+userRole.getRole());
			System.out.println("\n.......Records Fetched Successfully From The Database.......\n");

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		model.addAttribute("campground", campground);
		return new ModelAndView("comments/create", "command", new Comment());
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String newComment(@ModelAttribute("SpringWeb")Comment comment, int campgroundId, Model model) {
		Session sessionObj = new Configuration().configure()
				.buildSessionFactory().openSession();
		Campground campground = new Campground();
		User user = new User();
		try {
			sessionObj.beginTransaction();
			campground = (Campground) sessionObj.get(Campground.class, campgroundId);
			
			Query<User> query = sessionObj.createQuery("FROM User where username = ?0");
			query.setString(0, "alex");
			user = query.getSingleResult();
			System.out.println("\n.......Records Fetched Successfully From The Database.......\n");
			
			comment.setCommentAuthor(user);
			comment.setCampground(campground);
			sessionObj.save(comment);
			System.out.println("\n.......Records Saved Successfully To The Database.......\n");
			
			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return "redirect:/campgrounds/details?campgroundId="+ campground.getId();
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView editComment(int commentId, Model model, HttpServletRequest httpRequest, Principal principal) {
		model.addAttribute("contextName", httpRequest.getContextPath());
		Session sessionObj = new Configuration().configure()
				.buildSessionFactory().openSession();
		Comment comment = new Comment();
		try {
			sessionObj.beginTransaction();
			comment = (Comment) sessionObj.get(Comment.class, commentId);
			
			System.out.println("\n.......Records Fetched Successfully From The Database.......\n");

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		model.addAttribute("comment", comment);
		return new ModelAndView("comments/edit", "command", new Comment());
	}
	
	@RequestMapping(value="/editComment", method=RequestMethod.POST)
	public String editComment(@ModelAttribute("SpringWeb")Comment comment, Model model) {
		Session sessionObj = new Configuration().configure()
				.buildSessionFactory().openSession();
		Comment tempComment = new Comment();
		try {
			sessionObj.beginTransaction();
			tempComment = (Comment) sessionObj.get(Comment.class, comment.getId());
			tempComment.setText(comment.getText());
			sessionObj.save(tempComment);
			System.out.println("\n.......Records Updated Successfully To The Database.......\n");

			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return "redirect:/campgrounds/details?campgroundId="+tempComment.getCampground().getId();
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String deleteComment(int commentId, Model model, HttpServletRequest httpRequest, Principal principal) {
		Session sessionObj = new Configuration().configure()
				.buildSessionFactory().openSession();
		Comment tempComment = new Comment();
		try {
			sessionObj.beginTransaction();
			tempComment = (Comment) sessionObj.get(Comment.class, commentId);
			
			sessionObj.remove(tempComment);
			System.out.println("\n.......Records Deleted Successfully from The Database.......\n");
			// Committing The Transactions To The Database
			sessionObj.getTransaction().commit();
		} catch (Exception sqlException) {
			if (null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (sessionObj != null) {
				sessionObj.close();
			}
		}
		return "redirect:/campgrounds/details?campgroundId="+tempComment.getCampground().getId();
	}
}
