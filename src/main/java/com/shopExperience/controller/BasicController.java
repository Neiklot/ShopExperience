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
import com.shopExperience.entities.Product;
import com.shopExperience.entities.User;
import com.shopExperience.pagination.GridUtils;
import com.shopExperience.pagination.JqGridData;
import com.shopExperience.pagination.ModelTableUser;

@Controller
@RequestMapping("/")
public class BasicController {

	private EntityManager entityManager;

	List<ModelTableUser> usersModel;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String listUsers(ModelMap model) {

		usersModel = new ArrayList<ModelTableUser>();

		List<User> users = new ArrayList<User>();

		TypedQuery<User> query = entityManager.createNamedQuery("User.findAll",
				User.class);
		// .setMaxResults(pageSize).setFirstResult(currPosition).list();
		users = query.getResultList();
		int numProducts = 0, numCard = 0;

		for (User user : users) {
			ModelTableUser mTableUser = new ModelTableUser();
			mTableUser.setUserId(user.getId());
			mTableUser.setUserName(user.getUserName());
			numProducts = user.getProducts().size();
			mTableUser.setCard("A");
			if (user.getCards().size() > 0) {
				Card card = user.getCards().get(0);
				mTableUser.setCard("" + card.getId());
			}
			if (numProducts > 0) {
				mTableUser.setTotalProducts(numProducts);
				mTableUser.setLastProduct(user.getProducts()
						.get(numProducts - 1).getName());
			}
			usersModel.add(mTableUser);
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

	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	@Transactional
	public String addUSer(User user) {
		try{
		user.setProducts(getProducts());
		entityManager.persist(user);
		entityManager.flush();
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return "RegistrationSuccess";
	}
	
	public List<Product> getProducts(){
		TypedQuery<Product> query = entityManager.createNamedQuery("Product.findAll",
				Product.class);
		return query.getResultList();
	}

	@RequestMapping(value = "getUsers", method = RequestMethod.GET)
	@ResponseBody
	public JqGridData<ModelTableUser> getUsers(@RequestParam("page") int page,
			@RequestParam("rows") int rows,
			@RequestParam("sidx") String sortColumnId,
			@RequestParam("sord") String sortDirection) {

		int totalNumberOfPages = GridUtils.getTotalNumberOfPages(usersModel,
				rows);
		int currentPageNumber = GridUtils.getCurrentPageNumber(usersModel,
				page, rows);
		int totalNumberOfRecords = usersModel.size();
		List<ModelTableUser> pageData = GridUtils.getDataForPage(usersModel,
				page, rows);

		JqGridData<ModelTableUser> gridData = new JqGridData<ModelTableUser>(
				totalNumberOfPages, currentPageNumber, totalNumberOfRecords,
				pageData);

		return gridData;
	}
}
