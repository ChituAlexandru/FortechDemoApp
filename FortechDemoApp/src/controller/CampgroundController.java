package controller;

import java.security.Principal;
import java.util.List;

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
import models.User;

@Controller
@RequestMapping("/campgrounds")
public class CampgroundController {
	@RequestMapping(value="/show", method=RequestMethod.GET)
	public String showCampgrounds(Model model, HttpServletRequest httpRequest, Principal principal) {
		
		Session sessionObj = new Configuration().configure()
				.buildSessionFactory().openSession();
		List<Campground> result = null;
		try {
			sessionObj.beginTransaction();
			Query<Campground> query = sessionObj.createQuery("FROM Campground");
			result = query.getResultList();
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
		for(int i = 0; i < result.size(); i++) {
			for(int j = 0; j < result.get(i).getComments().size(); j++) {
				System.out.println("\n"+result.get(i).getComments().get(j).getText());
			}
		}
		User loggedUser = new User();
		model.addAttribute("campgrounds", result);
		model.addAttribute("loggedUser", loggedUser);
		return "campgrounds/show";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView newCampground(Model model, HttpServletRequest httpRequest, Principal principal) {
		model.addAttribute("contextName", httpRequest.getContextPath());
		return new ModelAndView("campgrounds/create", "command", new Campground());
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public String newCampground(@ModelAttribute("SpringWeb")Campground campground, Model model) {
		Session sessionObj = new Configuration().configure()
				.buildSessionFactory().openSession();
		User user = new User(); 
		try {
			sessionObj.beginTransaction();
			Query<User> query = sessionObj.createQuery("FROM User where username = ?0");
			query.setString(0, "alex");
			user = query.getSingleResult();
			System.out.println("\n\n\n " + user.getId() + " " + user.getUsername());
			campground.setAuthor(user);
			sessionObj.save(campground);
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
		
		return "redirect:/campgrounds/show";
	}
	
	@RequestMapping(value="/details", method = RequestMethod.GET)
	public String campgroundDetails(int campgroundId, Model model) {
		Session sessionObj = new Configuration().configure()
				.buildSessionFactory().openSession();
		Campground campground = new Campground();
		try {
			sessionObj.beginTransaction();
			Query<Campground> query = sessionObj.createQuery("FROM Campground where id=?0");
			query.setInteger(0, campgroundId);
			campground = query.getSingleResult();
			
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
		for(int i = 0; i < campground.getComments().size(); i++) {
			System.out.println("\n\n\n "+campground.getComments().get(i).getText());
		}
		model.addAttribute("campground", campground);
		return "/campgrounds/details";
	}
	
	@RequestMapping(value="/edit", method = RequestMethod.GET)
	public ModelAndView editCampground(int campgroundId, Model model, HttpServletRequest httpRequest, Principal principal) {
		model.addAttribute("contextName", httpRequest.getContextPath());
		Session sessionObj = new Configuration().configure()
				.buildSessionFactory().openSession();
		Campground campground = new Campground();
		try {
			sessionObj.beginTransaction();
			campground = (Campground) sessionObj.get(Campground.class, campgroundId);
			
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
		return new ModelAndView("campgrounds/edit", "command", new Campground());
	}
	
	@RequestMapping(value="/editCampground", method=RequestMethod.POST)
	public String editCampground(@ModelAttribute("SpringWeb")Campground campground, Model model) {
		Session sessionObj = new Configuration().configure()
				.buildSessionFactory().openSession();
		Campground tempCampground = new Campground();
		try {
			sessionObj.beginTransaction();
			tempCampground = (Campground) sessionObj.get(Campground.class, campground.getId());
			tempCampground.setName(campground.getName());
			tempCampground.setImage(campground.getImage());
			tempCampground.setDescription(campground.getDescription());
			sessionObj.save(tempCampground);
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
		return "redirect:/campgrounds/details?campgroundId="+tempCampground.getId();
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String deleteCampground(int campgroundId, Model model, HttpServletRequest httpRequest, Principal principal) {
		Session sessionObj = new Configuration().configure()
				.buildSessionFactory().openSession();
		Campground tempCampground = new Campground();
		try {
			sessionObj.beginTransaction();
			tempCampground = (Campground) sessionObj.get(Campground.class, campgroundId);
			
			sessionObj.remove(tempCampground);
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
		return "redirect:/campgrounds/show";
	}
}
