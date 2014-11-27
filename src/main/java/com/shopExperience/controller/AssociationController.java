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

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopExperience.entities.Association;
import com.shopExperience.entities.Card;
import com.shopExperience.entities.Client;
import com.shopExperience.entities.Shop;
import com.shopExperience.pagination.GridUtils;
import com.shopExperience.pagination.JqGridData;
import com.shopExperience.pagination.ModelTableAssociation;
import com.shopExperience.pagination.ModelTableClient;
import com.shopExperience.utils.JqgridFilter;
import com.shopExperience.utils.JqgridObjectMapper;


@Controller
@RequestMapping("/")
public class AssociationController {
	
	private EntityManager entityManager;
	
	List<ModelTableAssociation> associationsModel;
	
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	@RequestMapping(value = "getAssociations", method = RequestMethod.GET)
	@ResponseBody
	public JqGridData<ModelTableAssociation> getAssociations(@RequestParam("page") int page,
			@RequestParam("rows") int rows,
			@RequestParam("sidx") String sortColumnId,
			@RequestParam("sord") String sortDirection,@RequestParam("_search") boolean search,
			@RequestParam(value = "filters", required = false) String filters) {
		
		List<Association> associations = new ArrayList<Association>();
		associationsModel = new ArrayList<ModelTableAssociation>();
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaQuery<Association> cq = cb.createQuery(Association.class);
		Root<Association> from = cq.from(Association.class);
		TypedQuery<Association> query;

		cq.select(from);
		cq.orderBy(cb.asc(from.get(sortColumnId)));
		query = entityManager.createQuery(cq);

		if (search) {
			query = createFilter(query, cb, cq, from, filters);
		}

		associations = query.getResultList();

		// Table associations
		for (Association association : associations) {
			ModelTableAssociation mTableAssociation = new ModelTableAssociation();
			mTableAssociation.setAssociationId(association.getId());
			mTableAssociation.setAssociationName(association.getName());
			mTableAssociation.setnShops(association.getShops().size());
			mTableAssociation.setnClients(association.getUsers().size());
			associationsModel.add(mTableAssociation);
		}

		
		
		int totalNumberOfPages = GridUtils.getTotalNumberOfPages(associationsModel,
				rows);
		int currentPageNumber = GridUtils.getCurrentPageNumber(associationsModel,
				page, rows);
		int totalNumberOfRecords = associationsModel.size();
		List<ModelTableAssociation> pageData = GridUtils.getDataForPage(associationsModel,
				page, rows);

		JqGridData<ModelTableAssociation> gridData = new JqGridData<ModelTableAssociation>(
				totalNumberOfPages, currentPageNumber, totalNumberOfRecords,
				pageData);

		return gridData;
	}
	
	public TypedQuery<Association> createFilter(TypedQuery<Association> query,
			CriteriaBuilder cb, CriteriaQuery<Association> cq, Root<Association> from,
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
	
	private void defineOperation(CriteriaBuilder cb, CriteriaQuery<Association> cq,
			List<Predicate> predicates, JqgridFilter jqgridFilter) {
		if (jqgridFilter.getGroupOp().endsWith("OR")) {
			cq.where(cb.or(predicates.toArray(new Predicate[] {})));
		} else {
			cq.where(cb.and(predicates.toArray(new Predicate[] {})));
		}
	}
	
	public List<Association> getAllAssociations(){
		TypedQuery<Association> query = entityManager.createNamedQuery("Association.findAll",
				Association.class);
		return query.getResultList();
	}
	
	@RequestMapping(value = "/addAssociation", method = RequestMethod.POST)
	@Transactional
	public String addAssociation(@RequestParam("associationName") String associationName,
			@RequestParam("description") String description
			) {

		Association association = new Association();
		association.setName(associationName);
		association.setDescription(description);

		try {
			entityManager.persist(association);
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
