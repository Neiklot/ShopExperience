package com.shopExperience.utils;

import java.util.List;

import com.shopExperience.entities.Client;
import com.shopExperience.entities.Compra;

public class UserLogged {

	public Client client;
	public List<Compra> compras;
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public List<Compra> getCompras() {
		return compras;
	}
	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}
	
}
