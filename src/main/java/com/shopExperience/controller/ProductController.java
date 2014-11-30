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

import com.shopExperience.entities.Product;
import com.shopExperience.entities.Shop;
import com.shopExperience.pagination.GridUtils;
import com.shopExperience.pagination.JqGridData;
import com.shopExperience.pagination.ModelTableProduct;
import com.shopExperience.utils.JqgridFilter;
import com.shopExperience.utils.JqgridObjectMapper;


@Controller
@RequestMapping("/")
public class ProductController {
	
	private EntityManager entityManager;
	
	@Autowired
	ShopController cs;
	
	List<ModelTableProduct> productsModel;
	
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	@RequestMapping(value = "getProducts", method = RequestMethod.GET)
	@ResponseBody
	public JqGridData<ModelTableProduct> getProducts(@RequestParam("page") int page,
			@RequestParam("rows") int rows,
			@RequestParam("sidx") String sortColumnId,
			@RequestParam("sord") String sortDirection,@RequestParam("_search") boolean search,
			@RequestParam(value = "filters", required = false) String filters) {
		
		List<Product> products = new ArrayList<Product>();
		productsModel = new ArrayList<ModelTableProduct>();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Product> cq = cb.createQuery(Product.class);
		Root<Product> from = cq.from(Product.class);
		TypedQuery<Product> query;

		cq.select(from);
		cq.orderBy(cb.asc(from.get(sortColumnId)));
		query = entityManager.createQuery(cq);

		if (search) {
			query = createFilter(query, cb, cq, from, filters);
		}

		products = query.getResultList();

		// Table associations
		for (Product product : products) {
			ModelTableProduct mTableProduct = new ModelTableProduct();
			mTableProduct.setDescription(product.getDescription());
			mTableProduct.setImage_url(product.getImage_url());
			mTableProduct.setName(product.getName());
//			mTableProduct.setShopName(product.getShop().getName());
			mTableProduct.setValue(product.getValue());
			productsModel.add(mTableProduct);
		}

		
		
		int totalNumberOfPages = GridUtils.getTotalNumberOfPages(productsModel,
				rows);
		int currentPageNumber = GridUtils.getCurrentPageNumber(productsModel,
				page, rows);
		int totalNumberOfRecords = productsModel.size();
		List<ModelTableProduct> pageData = GridUtils.getDataForPage(productsModel,
				page, rows);

		JqGridData<ModelTableProduct> gridData = new JqGridData<ModelTableProduct>(
				totalNumberOfPages, currentPageNumber, totalNumberOfRecords,
				pageData);

		return gridData;
	}
	
	public TypedQuery<Product> createFilter(TypedQuery<Product> query,
			CriteriaBuilder cb, CriteriaQuery<Product> cq, Root<Product> from,
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
	
	private void defineOperation(CriteriaBuilder cb, CriteriaQuery<Product> cq,
			List<Predicate> predicates, JqgridFilter jqgridFilter) {
		if (jqgridFilter.getGroupOp().endsWith("OR")) {
			cq.where(cb.or(predicates.toArray(new Predicate[] {})));
		} else {
			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
		}
	}
	
	public List<Product> getAllProducts(){
		TypedQuery<Product> query = entityManager.createNamedQuery("Product.findAll",
				Product.class);
		return query.getResultList();
	}
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public String addProduct(@RequestParam("name") String productName,
			@RequestParam("description") String description,
			@RequestParam("value") long value,
			@RequestParam("shop_name") int shopId
			) {
		
//		Shop shop=cs.getShop(shopId);

		Product product = new Product();
		product.setName(productName);
		product.setDescription(description);
		product.setValue(value);
//		product.setShop(shop);

		try {
			entityManager.persist(product);
			entityManager.flush();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return "RegistrationSuccess";
	}

//	public Association getAssociationByClientId(int clientId){
//		Association association=new Association();
//		StringBuilder queryS = new StringBuilder();
//		queryS.append("Select a from Association a where a.client.id = :clientId");
//
//		TypedQuery<Association> query = entityManager.createQuery(queryS.toString(),
//				Association.class);
//		query.setParameter("clientId", clientId);
//		association= query.getSingleResult();
//
//		return association;
//	}
}
