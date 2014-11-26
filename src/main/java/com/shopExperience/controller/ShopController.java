package com.shopExperience.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopExperience.entities.Association;
import com.shopExperience.entities.Shop;
import com.shopExperience.pagination.GridUtils;
import com.shopExperience.pagination.JqGridData;
import com.shopExperience.pagination.ModelTableShop;

@Controller
@RequestMapping("/")
public class ShopController {

	private EntityManager entityManager;

	List<ModelTableShop> shopsModel;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
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

	@RequestMapping(value = "/addShop", method = RequestMethod.POST)
	@Transactional
	public String addShop(
			@RequestParam("shopName") String shopName,
			@RequestParam("description") String description,
			@RequestParam(value = "associationId", required = true) int associationId) {

		Shop shop = new Shop();
		shop.setName(shopName);
		shop.setDescription(description);
		shop.setAssociation(getAssociation(associationId));

		try {
			entityManager.persist(shop);
			entityManager.flush();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "RegistrationSuccess";
	}

	public Association getAssociation(int associationId) {
		Association association = new Association();
		StringBuilder queryS = new StringBuilder();
		queryS.append("Select as from Association as where as.id = :associationId");

		TypedQuery<Association> query = entityManager.createQuery(
				queryS.toString(), Association.class);
		query.setParameter("associationId", associationId);
		association = query.getSingleResult();

		return association;
	}

	public List<Shop> getAllShops() {
		List<Shop> shops = new ArrayList<Shop>();
		TypedQuery<Shop> queryShops = entityManager.createNamedQuery(
				"Shop.findAll", Shop.class);
		shops = queryShops.getResultList();
		return shops;
	}
}
