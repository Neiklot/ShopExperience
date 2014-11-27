package com.shopExperience.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopExperience.entities.Card;
import com.shopExperience.entities.Client;
import com.shopExperience.pagination.GridUtils;
import com.shopExperience.pagination.JqGridData;
import com.shopExperience.pagination.ModelTableCard;
import com.shopExperience.utils.JqgridFilter;
import com.shopExperience.utils.JqgridObjectMapper;

@Controller
@RequestMapping("/")
public class CardController {
	
	private EntityManager entityManager;
	
	List<ModelTableCard> cardsModel;
	
	@Autowired
	CompraController cc;
		
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
	
	@RequestMapping(value = "getCards", method = RequestMethod.GET)
	@ResponseBody
	public JqGridData<ModelTableCard> getCards(
			@RequestParam("page") int page, @RequestParam("rows") int rows,
			@RequestParam("sidx") String sortColumnId,
			@RequestParam("sord") String sortDirection,
			@RequestParam("_search") boolean search,
			@RequestParam(value = "filters", required = false) String filters) {
		
	
		
		List<Card> cards = new ArrayList<Card>();
		cardsModel = new ArrayList<ModelTableCard>();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Card> cq = cb.createQuery(Card.class);
		Root<Card> from = cq.from(Card.class);
		TypedQuery<Card> query;

		cq.select(from);
		cq.orderBy(cb.asc(from.get(sortColumnId)));
		query = entityManager.createQuery(cq);

		if (search) {
			query = createFilter(query, cb, cq, from, filters);
		}

		cards = query.getResultList();
		
		
		for (Card card : cards) {
			ModelTableCard mTableCard = new ModelTableCard();
			mTableCard.setCardId(card.getId());
			mTableCard.setBarcode(card.getBarcode());
			Client client=card.getClient();
			mTableCard.setPoints(cc.searchComprasByCard(client));	
			mTableCard.setClientIdentification(client.getClientName()+" "+client.getApellido1()+" "+client.getApellido2());
			cardsModel.add(mTableCard);
		}

		int totalNumberOfPages = GridUtils.getTotalNumberOfPages(cardsModel,
				rows);
		int currentPageNumber = GridUtils.getCurrentPageNumber(cardsModel,
				page, rows);
		int totalNumberOfRecords = cardsModel.size();
		List<ModelTableCard> pageData = GridUtils.getDataForPage(
				cardsModel, page, rows);

		JqGridData<ModelTableCard> gridData = new JqGridData<ModelTableCard>(
				totalNumberOfPages, currentPageNumber, totalNumberOfRecords,
				pageData);

		return gridData;
		
		
	}
	
	public TypedQuery<Card> createFilter(TypedQuery<Card> query,
			CriteriaBuilder cb, CriteriaQuery<Card> cq, Root<Card> from,
			String filters) {
		JqgridFilter jqgridFilter = JqgridObjectMapper.map(filters);
		List<Predicate> predicates = new ArrayList<Predicate>();
		for (JqgridFilter.Rule rule : jqgridFilter.getRules()) {

			switch (rule.getOp()) {
			case "eq":
				predicates.add(cb.equal(from.<String> get(rule.getField()),
						rule.getData()));
				defineOperation(cb, cq, predicates, jqgridFilter);
				query = entityManager.createQuery(cq);
				break;
			case "bw":
				predicates.add(cb.like(from.<String> get(rule.getField()),
						rule.getData() + "%"));
				defineOperation(cb, cq, predicates, jqgridFilter);
				query = entityManager.createQuery(cq);
				break;
			case "ew":
				predicates.add(cb.like(from.<String> get(rule.getField()), "%"
						+ rule.getData()));
				defineOperation(cb, cq, predicates, jqgridFilter);
				query = entityManager.createQuery(cq);
				break;
			case "ne":
				predicates.add(cb.notEqual(from.<String> get(rule.getField()),
						rule.getData()));
				defineOperation(cb, cq, predicates, jqgridFilter);
				query = entityManager.createQuery(cq);
				break;
			case "cn":
				predicates.add(cb.like(from.<String> get(rule.getField()), "%"
						+ rule.getData() + "%"));
				defineOperation(cb, cq, predicates, jqgridFilter);
				query = entityManager.createQuery(cq);
				break;
			}
		}
		return query;
	}

	private void defineOperation(CriteriaBuilder cb, CriteriaQuery<Card> cq,
			List<Predicate> predicates, JqgridFilter jqgridFilter) {
		if (jqgridFilter.getGroupOp().endsWith("OR")) {
			cq.where(cb.or(predicates.toArray(new Predicate[] {})));
		} else {
			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
		}
	}
	
	public List<Card> getAllCards() {
		TypedQuery<Card> query = entityManager.createNamedQuery("Card.findAll",
				Card.class);
		return query.getResultList();
	}

	
}
