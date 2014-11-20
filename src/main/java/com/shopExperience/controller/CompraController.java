package com.shopExperience.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopExperience.entities.Client;
import com.shopExperience.entities.Compra;

@Controller
@RequestMapping("/")
public class CompraController {

	private EntityManager entityManager;
	
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}  
	
	@RequestMapping(value = "/addCompra", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public void searchClientByBarCode(@RequestParam("client_id") int client_id,@RequestParam("importe") String importe) {
		Compra compra=new Compra();
		compra.setClient(searchClientById(client_id));
		compra.setImporte(Integer.parseInt(importe));
		entityManager.persist(compra);
		entityManager.flush();
	}
	
	
	public Client searchClientById(int clientId) {
		Client client = new Client();
		StringBuilder queryS = new StringBuilder();
		queryS.append("Select cl from Client cl where cl.id = :clientId");

		TypedQuery<Client> query = entityManager.createQuery(queryS.toString(),
				Client.class);
		query.setParameter("clientId", clientId);
		client = query.getSingleResult();
		return client;
	}
}
