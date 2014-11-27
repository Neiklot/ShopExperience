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
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopExperience.entities.Card;
import com.shopExperience.entities.Client;
import com.shopExperience.entities.User;
import com.shopExperience.pagination.GridUtils;
import com.shopExperience.pagination.JqGridData;
import com.shopExperience.pagination.ModelTableClient;
import com.shopExperience.utils.JqgridFilter;
import com.shopExperience.utils.JqgridObjectMapper;

@Controller
@RequestMapping("/")
public class ClientController {

	private EntityManager entityManager;
	
	@Autowired
	CompraController cc;
	
	@Autowired
	AssociationController ac;

	List<ModelTableClient> clientsModel;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
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
			mTableClient.setPassword(client.getPassword());
			mTableClient.setApellido1(client.getApellido1());
			mTableClient.setApellido2(client.getApellido2());
			mTableClient.setBaja(client.getBaja());
			mTableClient.setCodigoPostal(client.getCodigoPostal());
			mTableClient.setCuenta(client.getCuenta());
			mTableClient.setCuentaIban(client.getCuentaIban());
			mTableClient.setDireccion(client.getDireccion());
			mTableClient.setEmail(client.getEmail());
			mTableClient.setMovil(client.getMovil());
			mTableClient.setNIF(client.getNif());
			mTableClient.setObservaciones(client.getObservaciones());
			mTableClient.setPoblacion(client.getPoblacion());
			mTableClient.setSubnombre(client.getSubnomebre());
			mTableClient.setTelefono(client.getTelefono());
			mTableClient.setTipo(client.getTipo());
			mTableClient.setCard("" + client.getCards().size());
			if (client.getCards().size() > 0) {
				Card card = client.getCards().get(0);
				mTableClient.setCard("" + card.getId());
			}		
			mTableClient.setPuntos(cc.searchComprasByClient(client.getId()));
//			mTableClient.setAssociation(ac.getAssociationByClientId(client.getId()).getName());
			
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

	@RequestMapping(value = "/addClient", method = RequestMethod.POST)
	@Transactional
	public String addClient(@RequestParam("clientName") String clientName,
			@RequestParam("password") String password,
			@RequestParam("card") String card,
			@RequestParam("card_points") String points,
			@RequestParam("tipo") int tipo, @RequestParam("NIF") String NIF,
			@RequestParam("apellido1") String apellido1,
			@RequestParam("apellido2") String apellido2,
			@RequestParam("subNombre") String subnombre,
			@RequestParam("direccion") String direccion,
			@RequestParam("codigoPostal") int codigoPostal,
			@RequestParam("poblacion") String poblacion,
			@RequestParam("telefono") int telefono,
			@RequestParam("movil") String movil,
			@RequestParam("email") String email,
			@RequestParam("cuenta") int cuenta,
			@RequestParam("cuentaIban") String cuentaIban,
			@RequestParam("observaciones") String observaciones,
			@RequestParam("baja") boolean baja) {

		Client client = new Client();
		client.setClientName(clientName);
		client.setPassword(password);
		client.setApellido1(apellido1);
		client.setApellido2(apellido2);
		client.setBaja(baja);
		client.setCodigoPostal(codigoPostal);
		client.setCuenta(cuenta);
		client.setCuentaIban(cuentaIban);
		client.setDireccion(direccion);
		client.setEmail(email);
		client.setMovil(movil);
		client.setNif(NIF);
		client.setObservaciones(observaciones);
		client.setPoblacion(poblacion);
		client.setSubnomebre(subnombre);
		client.setTelefono(telefono);
		client.setTipo(tipo);
		client.setCards(getCardToClient(card));

		User user = new User();
		user.setUserName(subnombre);
		user.setPassword(password);
		user.setTipe(3);

		try {
			entityManager.persist(client);
			entityManager.persist(user);
			entityManager.flush();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "RegistrationSuccess";
	}

	public List<Card> getCardToClient(String barcode) {
		List<Card> cards = new ArrayList<>();
		Card card = new Card();
		StringBuilder queryS = new StringBuilder();
		queryS.append("Select ca from Card ca where ca.barcode = :barcode");

		TypedQuery<Card> query = entityManager.createQuery(queryS.toString(),
				Card.class);
		query.setParameter("barcode", barcode);
		card = query.getSingleResult();
		if (card.getClient() == null) {
			cards.add(card);
		}

		return cards;
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

	@RequestMapping(value = "/deleteClient", method = RequestMethod.POST)
	@Transactional
	public String deleteClient(@RequestParam("id") int clientId) {
		Client client = this.searchClientById(clientId);

		try {
			entityManager.remove(client);
			entityManager.flush();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "RegistrationSuccess";
	}

	public Client searchClientByUserAndPassword(String userName, String password) {
		Client client = new Client();
		StringBuilder queryS = new StringBuilder();
		queryS.append("Select cl from Client cl where cl.subnombre = :userName and cl.password=:password");

		TypedQuery<Client> query = entityManager.createQuery(queryS.toString(),
				Client.class);
		query.setParameter("userName", userName);
		query.setParameter("password", password);
		client = query.getSingleResult();
		return client;
	}

	@RequestMapping(value = "/searchClientByBarcode", method = RequestMethod.GET)
	@ResponseBody
	public String searchClientByBarCode(@RequestParam("barcode") String barcode)
			throws JsonGenerationException, JsonMappingException, IOException {
		Client clientFound = new Client();
		clientFound.setClientName("Usuario no encontrado");
		// FIXME:EAN13 UPC_A reading error
		if (barcode.matches("[0-9]+")) {

			if (barcode.length() < 13) {
				barcode = "0" + barcode;
			}

			TypedQuery<Card> query = entityManager.createNamedQuery(
					"Card.findAll", Card.class);

			for (Card card : query.getResultList()) {
				if (card.getBarcode().equals(barcode)) {
					clientFound = card.getClient();
				}
			}
		} else {
			TypedQuery<Client> query = entityManager.createNamedQuery(
					"Client.findAll", Client.class);
			for (Client client : query.getResultList()) {
				if (client.getClientName().equals(barcode)) {
					clientFound = client;
				}
			}

		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(clientFound);
	}
}
