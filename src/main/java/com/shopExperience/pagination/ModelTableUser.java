package com.shopExperience.pagination;

public class ModelTableUser {
	
	private int userId;
	private String userName;
	private int totalProducts;
	private String lastProduct;
	private String card;

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getTotalProducts() {
		return totalProducts;
	}
	public void setTotalProducts(int totalProducts) {
		this.totalProducts = totalProducts;
	}
	public String getLastProduct() {
		return lastProduct;
	}
	public void setLastProduct(String lastProduct) {
		this.lastProduct = lastProduct;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}

	
}
