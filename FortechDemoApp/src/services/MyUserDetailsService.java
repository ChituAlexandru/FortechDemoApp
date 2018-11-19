package services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import models.User;
import models.UserRole;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) {
		Session sessionObj = new Configuration().configure()
				.buildSessionFactory().openSession();
		List<User> result = null;
		try {
			sessionObj.beginTransaction();
			Query<User> query = sessionObj.createQuery("FROM User WHERE username = ?");
			query.setString(1, username);
			result = query.getResultList();
			for(int i = 0; i < result.size(); i++) {
				System.out.println(result.get(i).getUsername() + result.get(i).getPassword());
			}
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
		for(int i = 0; i < result.size(); i++) {
			if (result.get(i) == null) {
				throw new UsernameNotFoundException(username);
				
			}
			List<GrantedAuthority> authorities = buildUserAuthority(result.get(i).getUserRole());
			return buildUserForAuthentication(result.get(i), authorities);
		}
		
		return null;
	}
	
	private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user, 
			List<GrantedAuthority> authorities) {
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), 
				user.isEnabled(), true, true, true, authorities);
	}
	
	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}
}
