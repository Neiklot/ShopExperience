package com.shopExperience.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopExperience.entities.Card;
import com.shopExperience.entities.Client;
import com.shopExperience.entities.Product;
import com.shopExperience.entities.Shop;
import com.shopExperience.entities.User;
import com.shopExperience.pagination.GridUtils;
import com.shopExperience.pagination.JqGridData;
import com.shopExperience.pagination.ModelTableClient;
import com.shopExperience.pagination.ModelTableShop;

@Controller
@RequestMapping("/")
public class BasicController {

	private EntityManager entityManager;

	List<ModelTableClient> clientsModel;
	List<ModelTableShop> shopsModel;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String listClients(ModelMap model) {

		clientsModel = new ArrayList<ModelTableClient>();
		shopsModel = new ArrayList<ModelTableShop>();

		List<Client> clients = new ArrayList<Client>();
		List<Shop> shops = new ArrayList<Shop>();

		TypedQuery<Client> query = entityManager.createNamedQuery(
				"Client.findAll", Client.class);
		clients = query.getResultList();

		TypedQuery<Shop> queryShops = entityManager.createNamedQuery(
				"Shop.findAll", Shop.class);
		shops = queryShops.getResultList();
		int numProducts = 0, numCard = 0;

		// Table clients
		for (Client client : clients) {
			ModelTableClient mTableClient = new ModelTableClient();
			mTableClient.setClientId(client.getId());
			mTableClient.setClientName(client.getClientName());
			mTableClient.setCard("A");
			if (client.getCards().size() > 0) {
				Card card = client.getCards().get(0);
				mTableClient.setCard("" + card.getId());
			}
			if (numProducts > 0) {
				mTableClient.setTotalProducts(numProducts);
			}
			clientsModel.add(mTableClient);
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

	@RequestMapping(value = "/addClient", method = RequestMethod.GET)
	@Transactional
	public String addClient(Client client) {
		try {
			entityManager.persist(client);
			entityManager.flush();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "RegistrationSuccess";
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

	public List<Product> getProducts() {
		TypedQuery<Product> query = entityManager.createNamedQuery(
				"Product.findAll", Product.class);
		return query.getResultList();
	}

	public List<Card> getCards() {
		TypedQuery<Card> query = entityManager.createNamedQuery("Card.findAll",
				Card.class);
		return query.getResultList();
	}

	public Card searchCardByBarCode(String barCode) {
		//FIXME:EAN13 UPC_A reading error
		if(barCode.length()<13){barCode="0"+barCode;}
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
			@RequestParam("sord") String sortDirection) {

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
