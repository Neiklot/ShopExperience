package com.shopExperience.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "Compra")
@NamedQueries({
@NamedQuery(name="Compra.findAll",query="SELECT co FROM Compra co")})
public class Compra {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@SequenceGenerator(sequenceName = "SEQ_ID", name = "SEQ_ID", initialValue = 0, allocationSize = 1)
	private int id;

	@Column(name = "IMPORTE", nullable = false, length = 50)
	private int importe;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REG_DATE", nullable = true)
	private Date reg_date;

	@ManyToOne(fetch = FetchType.EAGER)
	private Card card;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getImporte() {
		return importe;
	}

	public void setImporte(int importe) {
		this.importe = importe;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public Card getClard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

}
