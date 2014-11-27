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

import com.shopExperience.entities.Shop;
import com.shopExperience.pagination.GridUtils;
import com.shopExperience.pagination.JqGridData;
import com.shopExperience.pagination.ModelTableShop;
import com.shopExperience.utils.JqgridFilter;
import com.shopExperience.utils.JqgridObjectMapper;


@Controller
@RequestMapping("/")
public class ShopController {

	private EntityManager entityManager;

	List<ModelTableShop> shopsModel;
	
	@Autowired
	AssociationController ac;
	
	@Autowired
	ClientController cc;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	@RequestMapping(value = "getShops", method = RequestMethod.GET)
	@ResponseBody
	public JqGridData<ModelTableShop> getShops(@RequestParam("page") int page,
			@RequestParam("rows") int rows,
			@RequestParam("sidx") String sortColumnId,
			@RequestParam("sord") String sortDirection,@RequestParam("_search") boolean search,
			@RequestParam(value = "filters", required = false) String filters) {
		
		List<Shop> shops = new ArrayList<Shop>();
		shopsModel = new ArrayList<ModelTableShop>();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Shop> cq = cb.createQuery(Shop.class);
		Root<Shop> from = cq.from(Shop.class);
		TypedQuery<Shop> query;

		cq.select(from);
		cq.orderBy(cb.asc(from.get(sortColumnId)));
		query = entityManager.createQuery(cq);

		if (search) {
			query = createFilter(query, cb, cq, from, filters);
		}

		shops = query.getResultList();
		
		// Table shops
		for (Shop shop : shops) {
			ModelTableShop mTableShop = new ModelTableShop();
			mTableShop.setShopName(shop.getName());
			mTableShop.setAssociation(shop.getAssociation().getName());
			mTableShop.setClients(shop.getClients().size());
			shopsModel.add(mTableShop);
		}


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
	
	public TypedQuery<Shop> createFilter(TypedQuery<Shop> query,
			CriteriaBuilder cb, CriteriaQuery<Shop> cq, Root<Shop> from,
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
	
	private void defineOperation(CriteriaBuilder cb, CriteriaQuery<Shop> cq,
			List<Predicate> predicates, JqgridFilter jqgridFilter) {
		if (jqgridFilter.getGroupOp().endsWith("OR")) {
			cq.where(cb.or(predicates.toArray(new Predicate[] {})));
		} else {
			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
		}
	}
	
	@RequestMapping(value = "/addShop", method = RequestMethod.POST)
	@Transactional
	public String addShop(
			@RequestParam("shopName") String shopName,
			@RequestParam("description") String description,
			@RequestParam(value = "shopId", required = true) int shopId) {

		Shop shop = new Shop();
		shop.setName(shopName);
		shop.setDescription(description);

		try {
			entityManager.persist(shop);
			entityManager.flush();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "RegistrationSuccess";
	}

	public Shop getShop(int shopId) {
		Shop shop = new Shop();
		StringBuilder queryS = new StringBuilder();
		queryS.append("Select as from Shop as where as.id = :shopId");

		TypedQuery<Shop> query = entityManager.createQuery(
				queryS.toString(), Shop.class);
		query.setParameter("shopId", shopId);
		shop = query.getSingleResult();

		return shop;
	}

	public List<Shop> getAllShops() {
		List<Shop> shops = new ArrayList<Shop>();
		TypedQuery<Shop> queryShops = entityManager.createNamedQuery(
				"Shop.findAll", Shop.class);
		shops = queryShops.getResultList();
		return shops;
	}
}
