package com.shopExperience.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity(name = "Client")
@NamedQueries({
@NamedQuery(name="Client.findAll",query="SELECT u FROM Client u")})
public class Client{
	
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="Client_NAME",nullable=false, length = 50)
    private String ClientName;

    @Column(name="PASSWORD",nullable=false, length = 50)
    private String password;
    
    @ManyToOne 
    @JoinColumn(name="shop_id")
    @LazyCollection(LazyCollectionOption.FALSE) 
 	private Shop shop;
    
    @OneToMany(cascade=CascadeType.PERSIST) 
    @JoinColumn(name="client_id")
    @LazyCollection(LazyCollectionOption.FALSE) 
    private List<Card> cards;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public String getClientName() {
		return ClientName;
	}

	public void setClientName(String ClientName) {
		this.ClientName = ClientName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
  
}
