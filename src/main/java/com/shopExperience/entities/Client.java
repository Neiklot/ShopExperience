package com.shopExperience.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity(name = "Client")
@NamedQueries({ @NamedQuery(name = "Client.findAll", query = "SELECT u FROM Client u") })
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "Tipo", nullable = false, length = 50)
	private int tipo;

	@Column(name = "NIF", nullable = false, length = 50)
	private String nif;

	@Column(name = "Apellido_1", nullable = false, length = 50)
	private String apellido1;

	@Column(name = "Apellido_2", nullable = false, length = 50)
	private String apellido2;

	@Column(name = "Subnombre", nullable = false, length = 150)
	private String subnomebre;

	@Column(name = "Direccion", nullable = false, length = 150)
	private String direccion;

	@Column(name = "Codigo_Postal", nullable = false, length = 50)
	private int codigoPostal;

	@Column(name = "Client_NAME", nullable = false, length = 50)
	private String ClientName;

	@Column(name = "Poblacion", nullable = false, length = 150)
	private String poblacion;

	@Column(name = "PASSWORD", nullable = false, length = 50)
	private String password;

	@Column(name = "Telefono", nullable = false, length = 50)
	private int telefono;

	@Column(name = "Movil", nullable = false, length = 50)
	private String movil;

	@Column(name = "Email", nullable = false, length = 50)
	private String email;

	@Column(name = "Cuenta", nullable = false, length = 50)
	private int cuenta;

	@Column(name = "Cuenta_IBAN", nullable = false, length = 50)
	private String cuentaIban;

	@Column(name = "Observaciones", nullable = false, length = 250)
	private String observaciones;

	@Column(name = "Baja", nullable = false, length = 50)
	private boolean baja;

	@ManyToOne
	@JoinColumn(name = "shop_id")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Shop shop;

	@OneToMany
	@JoinColumn(name = "client_id")
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

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getSubnomebre() {
		return subnomebre;
	}

	public void setSubnomebre(String subnomebre) {
		this.subnomebre = subnomebre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getMovil() {
		return movil;
	}

	public void setMovil(String movil) {
		this.movil = movil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCuenta() {
		return cuenta;
	}

	public void setCuenta(int cuenta) {
		this.cuenta = cuenta;
	}

	public String getCuentaIban() {
		return cuentaIban;
	}

	public void setCuentaIban(String cuentaIban) {
		this.cuentaIban = cuentaIban;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public boolean isBaja() {
		return baja;
	}

	public void setBaja(boolean baja) {
		this.baja = baja;
	}

}
