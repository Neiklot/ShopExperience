package com.shopExperience.pagination;

public class ModelTableClient {
	
	private int clientId;
	private String clientName;
	private int totalProducts;
	private String lastProduct;
	private String card;

	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
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
