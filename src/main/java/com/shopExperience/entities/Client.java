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
    
    @ManyToMany(mappedBy="clients",fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	 private List<Product> products;
    
    @ManyToOne 
    @JoinColumn(name="association_id")
    @LazyCollection(LazyCollectionOption.FALSE) 
 	private Association association;
    
    @OneToMany 
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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public Association getAssociation() {
		return association;
	}

	public void setAssociation(Association association) {
		this.association = association;
	}
  
}
