package com.shopExperience.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shopExperience.entities.Client;

public class AutenticationService implements UserDetailsService {

	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	public UserDetails loadUserByUsername(String clientName)
			throws UsernameNotFoundException, DataAccessException {

		List<Client> clients = entityManager.createNamedQuery("Client.findAll",
				Client.class).getResultList();
		Client client = null;

		for (Client clientSearch : clients) {
			if (clientSearch.getClientName().equals(clientName)) {
				client = clientSearch;
			}
		}
		if (client == null)
			throw new UsernameNotFoundException("Client not found");

		String clientname = client.getClientName();
		String password = client.getPassword();

		@SuppressWarnings("unused")
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		@SuppressWarnings("deprecation")
		org.springframework.security.core.userdetails.User clientValidated = new org.springframework.security.core.userdetails.User(
				clientname,
				password,
				true,
				true,
				true,
				true,
				new GrantedAuthority[] { new GrantedAuthorityImpl("ROLE_USER") });

		return clientValidated;
	}
}
