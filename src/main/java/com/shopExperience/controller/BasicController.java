package com.shopExperience.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopExperience.entities.Card;
import com.shopExperience.entities.Client;
import com.shopExperience.entities.Compra;
import com.shopExperience.entities.User;
import com.shopExperience.pagination.ModelTableAssociation;
import com.shopExperience.pagination.ModelTableCard;
import com.shopExperience.pagination.ModelTableClient;
import com.shopExperience.pagination.ModelTableShop;
import com.shopExperience.security.CustomUserDetails;
import com.shopExperience.utils.JqgridFilter;
import com.shopExperience.utils.JqgridObjectMapper;
import com.shopExperience.utils.UserLogged;

@Controller
@RequestMapping("/")
public class BasicController {

	private EntityManager entityManager;

	@Autowired
	ClientController cc;
	
	@Autowired
	AssociationController ac;
	
	@Autowired
	CardController cca;
	
	@Autowired
	ShopController sc;
	
	@Autowired
	CompraController co;
	
	List<ModelTableClient> clientsModel;
	List<ModelTableShop> shopsModel;
	List<ModelTableAssociation> associationsModel;
	List<ModelTableCard> cardsModel;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String createPages(ModelMap model) {
		String indexType = "index";
		CustomUserDetails user = (CustomUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		int userType = user.getType();
		String userName = user.getUsername();

		switch (userType) {
		case 1:
			indexType = "index";
			break;
		case 2:
			indexType = "indexShop";
			break;
		case 3:
			indexType = "indexClient";
			break;
		}
		return indexType;
	}

	@RequestMapping(value = "/getUserLogged", method = RequestMethod.GET)
	@ResponseBody
	public String getUserLogged() throws JsonGenerationException,
			JsonMappingException, IOException {
		CustomUserDetails user = (CustomUserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		UserLogged userLogged = new UserLogged();
		Client client = cc.searchClientByUserAndPassword(user.getUsername(),
				user.getPassword());
		userLogged.setClient(client);
		userLogged.setCompras(getComprasClient(client.getId()));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(userLogged);
	}

	public List<Compra> getComprasClient(int clientId) {
		return co.searchComprasByClient(clientId);

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		return "login";
	}

	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {

		model.addAttribute("error", "true");
		return "login";

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {

		return "login";

	}

	@RequestMapping(value = "/getSelectableShops", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getSelectableShops() {
		List<String> barcodes = new ArrayList<String>();
		for (Card card : cca.getAllCards()) {
			barcodes.add(card.getBarcode());
		}
		return barcodes;
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	@Transactional
	public String addUser(User user) {
		try {
			entityManager.persist(user);
			entityManager.flush();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "RegistrationSuccess";
	}

	public Card searchCardByBarCode(String barCode) {
		// FIXME:EAN13 UPC_A reading error
		if (barCode.length() < 13) {
			barCode = "0" + barCode;
		}
		Card cardFound = null;
		TypedQuery<Card> query = entityManager.createNamedQuery("Card.findAll",
				Card.class);
		for (Card card : query.getResultList()) {
			if (card.getBarcode().equals(barCode)) {
				cardFound = card;
			}
		}
		return cardFound;
	}

	public TypedQuery<Card> createFilterCard(TypedQuery<Card> query,
			CriteriaBuilder cb, CriteriaQuery<Card> cq, Root<Card> from,
			String filters) {
		JqgridFilter jqgridFilter = JqgridObjectMapper.map(filters);
		List<Predicate> predicates = new ArrayList<Predicate>();
		for (JqgridFilter.Rule rule : jqgridFilter.getRules()) {

			switch (rule.getOp()) {
			case "eq":
				predicates.add(cb.equal(from.<String> get(rule.getField()),
						rule.getData()));
				defineOperationCard(cb, cq, predicates, jqgridFilter);
				query = entityManager.createQuery(cq);
				break;
			case "bw":
				predicates.add(cb.like(from.<String> get(rule.getField()),
						rule.getData() + "%"));
				defineOperationCard(cb, cq, predicates, jqgridFilter);
				query = entityManager.createQuery(cq);
				break;
			case "ew":
				predicates.add(cb.like(from.<String> get(rule.getField()), "%"
						+ rule.getData()));
				defineOperationCard(cb, cq, predicates, jqgridFilter);
				query = entityManager.createQuery(cq);
				break;
			case "ne":
				predicates.add(cb.notEqual(from.<String> get(rule.getField()),
						rule.getData()));
				defineOperationCard(cb, cq, predicates, jqgridFilter);
				query = entityManager.createQuery(cq);
				break;
			case "cn":
				predicates.add(cb.like(from.<String> get(rule.getField()), "%"
						+ rule.getData() + "%"));
				defineOperationCard(cb, cq, predicates, jqgridFilter);
				query = entityManager.createQuery(cq);
				break;
			}
		}
		return query;
	}

	private void defineOperationCard(CriteriaBuilder cb,
			CriteriaQuery<Card> cq, List<Predicate> predicates,
			JqgridFilter jqgridFilter) {
		if (jqgridFilter.getGroupOp().endsWith("OR")) {
			cq.where(cb.or(predicates.toArray(new Predicate[] {})));
		} else {
			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
		}
	}
}
