package com.shopExperience.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopExperience.entities.Card;

public class CardController {
	
	private EntityManager entityManager;
		
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	@RequestMapping(value = "/addCard", method = RequestMethod.POST)
	@Transactional
	public String addCard(@RequestParam("barcode") String barcode,
			@RequestParam("points") int points) {

		Card card = new Card();
		card.setBarcode(barcode);
		card.setPoints(points);

		try {
			entityManager.persist(card);
			entityManager.flush();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "RegistrationSuccess";
	}
	
}
