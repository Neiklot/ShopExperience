package com.shopExperience.pagination;

public class ModelTableClient {
	
	private int clientId;
	private String ClientName;
	private String password;
	private String card;
	private int card_points;

	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return ClientName;
	}
	public void setClientName(String clientName) {
		this.ClientName = clientName;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public int getCard_points() {
		return card_points;
	}
	public void setCard_points(int card_points) {
		this.card_points = card_points;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
