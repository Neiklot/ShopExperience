package com.shopExperience.pagination;


public class ModelTableClient {
	
	private int clientId;
	private String clientName;
	private String password;
	private String card;
	private int card_points;
	private int tipo;
	private String NIF;
	private String apellido1;
	private String apellido2;
	private String subnombre;
	private String direccion;
	private int codigoPostal;
	private String poblacion;
	private int telefono;
	private String movil;
	private String email;
	private int cuenta;
	private String cuentaIban;
	private String observaciones;
	private boolean baja;	

	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public int getCard_points() {
		return card_points;
	}
	public void setCard_points(int card_points) {
		this.card_points = card_points;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public String getNIF() {
		return NIF;
	}
	public void setNIF(String nIF) {
		NIF = nIF;
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
	public String getSubnombre() {
		return subnombre;
	}
	public void setSubnombre(String subnombre) {
		this.subnombre = subnombre;
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
