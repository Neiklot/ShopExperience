package com.shopExperience.pagination;

public class ModelTableAssociation {
	
	private int associationId;
	private String associationName;
	private int nShops;
	private int nClients;

	public int getAssociationId() {
		return associationId;
	}

	public void setAssociationId(int shopId) {
		this.associationId = shopId;
	}

	public String getAssociationName() {
		return associationName;
	}

	public void setAssociationName(String associationName) {
		this.associationName = associationName;
	}

	public int getnShops() {
		return nShops;
	}

	public void setnShops(int nShops) {
		this.nShops = nShops;
	}

	public int getnClients() {
		return nClients;
	}

	public void setnClients(int nClients) {
		this.nClients = nClients;
	}
}
