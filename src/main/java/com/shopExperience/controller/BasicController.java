package com.shopExperience.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopExperience.entities.Association;
import com.shopExperience.entities.Card;
import com.shopExperience.entities.Client;
import com.shopExperience.entities.Shop;
import com.shopExperience.entities.User;
import com.shopExperience.pagination.GridUtils;
import com.shopExperience.pagination.JqGridData;
import com.shopExperience.pagination.ModelTableAssociation;
import com.shopExperience.pagination.ModelTableClient;
import com.shopExperience.pagination.ModelTableShop;
import com.shopExperience.utils.JqgridFilter;
import com.shopExperience.utils.JqgridObjectMapper;

@Controller
@RequestMapping("/")
public class BasicController {

	private EntityManager entityManager;

	List<ModelTableClient> clientsModel;
	List<ModelTableShop> shopsModel;
	List<ModelTableAssociation> associationsModel;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String listClients(ModelMap model) {

		shopsModel = new ArrayList<ModelTableShop>();
		associationsModel = new ArrayList<ModelTableAssociation>();

		List<Shop> shops = new ArrayList<Shop>();
		List<Association> associations = new ArrayList<Association>();

		TypedQuery<Shop> queryShops = entityManager.createNamedQuery(
				"Shop.findAll", Shop.class);
		shops = queryShops.getResultList();

		TypedQuery<Association> queryAssociation = entityManager
				.createNamedQuery("Association.findAll", Association.class);
		associations = queryAssociation.getResultList();
		int numProducts = 0, numCard = 0;

		// Table associations
		for (Association association : associations) {
			ModelTableAssociation mTableAssociation = new ModelTableAssociation();
			mTableAssociation.setAssociationId(association.getId());
			mTableAssociation.setAssociationName(association.getName());
			associationsModel.add(mTableAssociation);
		}
		// Table shops
		for (Shop shop : shops) {
			ModelTableShop mTableShop = new ModelTableShop();
			mTableShop.setShopId(shop.getId());
			mTableShop.setShopName(shop.getName());
			shopsModel.add(mTableShop);
		}
		return "index";
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

	@RequestMapping(value = "/addClient", method = RequestMethod.POST)
	@Transactional
	public String addClient(
			@RequestParam("clientName") String clientName,
			@RequestParam("password") String password,
			@RequestParam("card") String card,
			@RequestParam("card_points") String points) {
		
		Client client=new Client();
		client.setClientName(clientName);
		client.setPassword(password);
		
		try {
			entityManager.persist(client);
			entityManager.flush();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "RegistrationSuccess";
	}
	
	@RequestMapping(value = "/getSelectableShops",method = RequestMethod.GET)
	@ResponseBody
	public List<String> getSelectableShops() {
		List<String> results=new ArrayList<>();
		results.add("User1");
		results.add("User2");
			    return results;
			}

	@RequestMapping(value = "/addCard", method = RequestMethod.GET)
	@Transactional
	public String addCard(Card card) {
		try {
			entityManager.persist(card);
			entityManager.flush();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "RegistrationSuccess";
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

	public List<Card> getCards() {
		TypedQuery<Card> query = entityManager.createNamedQuery("Card.findAll",
				Card.class);
		return query.getResultList();
	}

	public List<Association> getAssociations() {
		TypedQuery<Association> query = entityManager.createNamedQuery(
				"Association.findAll", Association.class);
		return query.getResultList();
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

	@RequestMapping(value = "getClients", method = RequestMethod.GET)
	@ResponseBody
	public JqGridData<ModelTableClient> getClients(
			@RequestParam("page") int page, @RequestParam("rows") int rows,
			@RequestParam("sidx") String sortColumnId,
			@RequestParam("sord") String sortDirection,
			@RequestParam("_search") boolean search,
			@RequestParam(value = "filters", required = false) String filters) {

		List<Client> clients = new ArrayList<Client>();
		clientsModel = new ArrayList<ModelTableClient>();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Client> cq = cb.createQuery(Client.class);
		Root<Client> from = cq.from(Client.class);
		TypedQuery<Client> query;

		cq.select(from);
		cq.orderBy(cb.asc(from.get(sortColumnId)));
		query = entityManager.createQuery(cq);

		if (search) {
			query = createFilter(query, cb, cq, from, filters);
		}

		clients = query.getResultList();

		// Table clients
		for (Client client : clients) {
			ModelTableClient mTableClient = new ModelTableClient();
			mTableClient.setClientId(client.getId());
			mTableClient.setClientName(client.getClientName());
			mTableClient.setCard("" + client.getCards().size());
			if (client.getCards().size() > 0) {
				Card card = client.getCards().get(0);
				mTableClient.setCard("" + card.getId());
			}
			clientsModel.add(mTableClient);
		}

		int totalNumberOfPages = GridUtils.getTotalNumberOfPages(clientsModel,
				rows);
		int currentPageNumber = GridUtils.getCurrentPageNumber(clientsModel,
				page, rows);
		int totalNumberOfRecords = clientsModel.size();
		List<ModelTableClient> pageData = GridUtils.getDataForPage(
				clientsModel, page, rows);

		JqGridData<ModelTableClient> gridData = new JqGridData<ModelTableClient>(
				totalNumberOfPages, currentPageNumber, totalNumberOfRecords,
				pageData);

		return gridData;
	}

	public TypedQuery<Client> createFilter(TypedQuery<Client> query,
			CriteriaBuilder cb, CriteriaQuery<Client> cq, Root<Client> from,
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

	private void defineOperation(CriteriaBuilder cb, CriteriaQuery<Client> cq,
			List<Predicate> predicates, JqgridFilter jqgridFilter) {
		if (jqgridFilter.getGroupOp().endsWith("OR")) {
			cq.where(cb.or(predicates.toArray(new Predicate[] {})));
		} else {
			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
		}
	}

	@RequestMapping(value = "getShops", method = RequestMethod.GET)
	@ResponseBody
	public JqGridData<ModelTableShop> getShops(@RequestParam("page") int page,
			@RequestParam("rows") int rows,
			@RequestParam("sidx") String sortColumnId,
			@RequestParam("sord") String sortDirection) {

		int totalNumberOfPages = GridUtils.getTotalNumberOfPages(shopsModel,
				rows);
		int currentPageNumber = GridUtils.getCurrentPageNumber(shopsModel,
				page, rows);
		int totalNumberOfRecords = shopsModel.size();
		List<ModelTableShop> pageData = GridUtils.getDataForPage(shopsModel,
				page, rows);

		JqGridData<ModelTableShop> gridData = new JqGridData<ModelTableShop>(
				totalNumberOfPages, currentPageNumber, totalNumberOfRecords,
				pageData);

		return gridData;
	}
}
