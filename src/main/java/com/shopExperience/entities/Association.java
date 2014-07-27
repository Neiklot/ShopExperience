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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity(name = "Association")
@NamedQueries({
@NamedQuery(name="Association.findAll",query="SELECT a FROM Association a")})
public class Association {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SequenceGenerator(sequenceName = "SEQ_ID", name = "SEQ_ID", initialValue = 0, allocationSize = 1)
	private int id;
	
	@Column(name = "NAME", nullable = false, length = 50)
	private String name;

	@Column(name = "DESCRIPTION", nullable = true, length = 250)
	private String description;
	
	@ManyToMany
	@JoinColumn(name = "client_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Client> clients;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Client> getUsers() {
		return clients;
	}

	public void setUsers(List<Client> clients) {
		this.clients = clients;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

}
