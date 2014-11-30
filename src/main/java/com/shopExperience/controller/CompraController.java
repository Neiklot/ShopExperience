package com.shopExperience.controller;

import java.util.ArrayList;
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

import com.shopExperience.entities.Card;
import com.shopExperience.entities.Compra;

@Controller
@RequestMapping("/")
public class CompraController {

	private EntityManager entityManager;

	@Autowired
	ClientController cc;
	
	@Autowired
	CardController cca;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	@RequestMapping(value = "/addCompra", method = RequestMethod.GET)
	@ResponseBody
	@Transactional
	public void addCompra(@RequestParam("client_id") int card_id,
			@RequestParam("importe") String importe) {
		Compra compra = new Compra();
		compra.setCard(cca.searchCardById(card_id));
		compra.setImporte(Integer.parseInt(importe));
		entityManager.persist(compra);
		entityManager.flush();
	}

	public long searchSumComprasByClient(int clientId) {
		List<Integer> cardsId =new ArrayList<>();
		List<Card> clientsCards=cca.searchCardByClientId(clientId);
		
		for(Card clientCards:clientsCards){
		 cardsId.add(clientCards.getId());
		}
		
		StringBuilder queryS = new StringBuilder();
		long comprasTotales = 0;
		queryS.append("Select SUM(co.importe) from Compra co join co.card ca where :cardsId IN ca");
		//from Item item join item.labels lbls where 'hello' in (lbls)
		
		TypedQuery<Long> query = entityManager.createQuery(queryS.toString(),
				Long.class);
		query.setParameter("cardsId", cardsId);
		try{
		comprasTotales = query.getSingleResult();
		}catch(NullPointerException e){
			return 0;
		}
		return comprasTotales;
	}
	
	public List<Compra> searchComprasByClient(int clientId) {
		List<Integer> cardsId =new ArrayList<>();
		List<Card> clientsCards=cca.searchCardByClientId(clientId);
		List<Compra> compras=new ArrayList<>();
		for(Card clientCards:clientsCards){
		 cardsId.add(clientCards.getId());
		}
		
		StringBuilder queryS = new StringBuilder();
		queryS.append("Select co from Compra co join co.card ca where :cardsId IN ca");
		//from Item item join item.labels lbls where 'hello' in (lbls)
		
		TypedQuery<Compra> query = entityManager.createQuery(queryS.toString(),
				Compra.class);
		query.setParameter("cardsId", cardsId);
			compras = query.getResultList();
		return compras;
	}
	
	public long searchComprasByCard(int cardId) {

		StringBuilder queryS = new StringBuilder();
		long comprasTotales = 0;
		queryS.append("Select SUM(ca.importe) from Compra ca where ca.card.id = :cardId");

		TypedQuery<Long> query = entityManager.createQuery(queryS.toString(),
				Long.class);
		query.setParameter("cardId", cardId);
		try{
		comprasTotales = query.getSingleResult();
		}catch(NullPointerException e){
			return 0;
		}
		return comprasTotales;
	}

}
