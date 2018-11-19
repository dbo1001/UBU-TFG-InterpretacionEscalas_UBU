package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the item database table.
 * 
 */
@Entity
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long numero;

	private String descripcion;

	@Column(name="edad_asignada")
	private BigDecimal edadAsignada;

	//bi-directional many-to-one association to Areafuncional
	@ManyToOne
	@JoinColumn(name="id_areafuncional")
	private Areafuncional areafuncional;

	//bi-directional many-to-one association to Puntuacion
	@OneToMany(mappedBy="item")
	private List<Puntuacion> puntuacions;

	public Item() {
	}

	public long getNumero() {
		return this.numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getEdadAsignada() {
		return this.edadAsignada;
	}

	public void setEdadAsignada(BigDecimal edadAsignada) {
		this.edadAsignada = edadAsignada;
	}

	public Areafuncional getAreafuncional() {
		return this.areafuncional;
	}

	public void setAreafuncional(Areafuncional areafuncional) {
		this.areafuncional = areafuncional;
	}

	public List<Puntuacion> getPuntuacions() {
		return this.puntuacions;
	}

	public void setPuntuacions(List<Puntuacion> puntuacions) {
		this.puntuacions = puntuacions;
	}

	public Puntuacion addPuntuacion(Puntuacion puntuacion) {
		getPuntuacions().add(puntuacion);
		puntuacion.setItem(this);

		return puntuacion;
	}

	public Puntuacion removePuntuacion(Puntuacion puntuacion) {
		getPuntuacions().remove(puntuacion);
		puntuacion.setItem(null);

		return puntuacion;
	}

}