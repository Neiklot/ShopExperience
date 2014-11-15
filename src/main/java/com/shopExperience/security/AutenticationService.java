package com.shopExperience.security;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shopExperience.entities.User;

public class AutenticationService implements UserDetailsService {

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException, DataAccessException {

		List<User> users = entityManager.createNamedQuery("User.findAll",
				User.class).getResultList();
		User user = null;

		for (User userSearch : users) {
			if (userSearch.getUserName().equals(userName)) {
				user = userSearch;
			}
		}
		if (user == null)
			throw new UsernameNotFoundException("User not found");

		String username = user.getUserName();
		String password = user.getPassword();
		int type=user.getTipe();

		CustomUserDetails userValidated = new CustomUserDetails (username, password, true, true, true, true,
		            type);
		
		return userValidated;
	}
}
