package com.shopExperience.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity(name = "User")
@NamedQueries({
@NamedQuery(name="User.findAll",query="SELECT u FROM User u")})
public class User {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="User_NAME",nullable=false, length = 50)
    private String UserName;

    @Column(name="PASSWORD",nullable=false, length = 50)
    private String password;
    
    @Column(name="User_TIPE",nullable=false,length=50)
    private int tipe;

	@ManyToOne 
    @JoinColumn(name="association_id")
    @LazyCollection(LazyCollectionOption.FALSE) 
 	private Association association;
    
    @ManyToOne
    @JoinColumn(name="shop_id")
    @LazyCollection(LazyCollectionOption.FALSE) 
    private Shop shop;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Association getAssociation() {
		return association;
	}

	public void setAssociation(Association association) {
		this.association = association;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
    
   public int getTipe() {
		return tipe;
	}

	public void setTipe(int tipe) {
		this.tipe = tipe;
	}


	
}
