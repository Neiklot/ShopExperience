package com.shopExperience.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	ClientController cc;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	@RequestMapping(value = "/addCompra", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public void addCompra(@RequestParam("client_id") int client_id,
			@RequestParam("importe") String importe) {
		Compra compra = new Compra();
		compra.setClient(cc.searchClientById(client_id));
		compra.setImporte(Integer.parseInt(importe));
		entityManager.persist(compra);
		entityManager.flush();
	}

	public long searchComprasByClient(int clientId) {
		StringBuilder queryS = new StringBuilder();
		long comprasTotales = 0;
		queryS.append("Select SUM(ca.importe) from Compra ca where ca.client.id = :clientId");

		TypedQuery<Long> query = entityManager.createQuery(queryS.toString(),
				Long.class);
		query.setParameter("clientId", clientId);
		try{
		comprasTotales = query.getSingleResult();
		}catch(NullPointerException e){
			return 0;
		}
		return comprasTotales;
	}
	
	public long searchComprasByCard(Client client) {
		
		int clientId=client.getId();
				
		StringBuilder queryS = new StringBuilder();
		long comprasTotales = 0;
		queryS.append("Select SUM(ca.importe) from Compra ca where ca.client.id = :clientId");

		TypedQuery<Long> query = entityManager.createQuery(queryS.toString(),
				Long.class);
		query.setParameter("clientId", clientId);
		try{
		comprasTotales = query.getSingleResult();
		}catch(NullPointerException e){
			return 0;
		}
		return comprasTotales;
	}

}
