package com.shopExperience.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

@Entity(name = "Card")
@NamedQueries({
@NamedQuery(name="Card.findAll",query="SELECT c FROM Card c")})
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SequenceGenerator(sequenceName = "SEQ_ID", name = "SEQ_ID", initialValue = 0, allocationSize = 1)
	private int id;

	@Column(name = "BARCODE", nullable = false, length = 50)
	private String barcode;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Client client;

	@Column(name="POINTS",nullable=false,length=50)
	private int points;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "client")
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

}
