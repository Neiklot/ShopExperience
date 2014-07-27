package com.shopExperience.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity(name = "Shopping")
@NamedQueries({
@NamedQuery(name="Shopping.findAll",query="SELECT so FROM Shopping so")})
public class Shopping {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SequenceGenerator(sequenceName = "SEQ_ID", name = "SEQ_ID", initialValue = 0, allocationSize = 1)
	private int id;
	
	@ManyToOne
    @JoinColumn(name="shop_id")
    @LazyCollection(LazyCollectionOption.FALSE) 
	private Shop shop;

	@ManyToOne
    @JoinColumn(name="client_id")
    @LazyCollection(LazyCollectionOption.FALSE) 
	private Client client;

	@ManyToOne
    @JoinColumn(name="card_id")
    @LazyCollection(LazyCollectionOption.FALSE) 
	private Card card;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}
}
