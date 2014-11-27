package com.shopExperience.pagination;

public class ModelTableCard {
	
	private int cardId;
	private String clientIdentification;
	private String barcode;
	private long points;
	
	public int getCardId() {
		return cardId;
	}
	public void setCardId(int cardId) {
		this.cardId = cardId;
	}
	public String getBarcode() {
		return barcode;
	}
	public String getClientIdentification() {
		return clientIdentification;
	}
	public void setClientIdentification(String clientIdentification) {
		this.clientIdentification = clientIdentification;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public long getPoints() {
		return points;
	}
	public void setPoints(long points) {
		this.points = points;
	}

	
}
