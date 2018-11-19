package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the categorizacion database table.
 * 
 */
@Entity
@NamedQuery(name="Categorizacion.findAll", query="SELECT c FROM Categorizacion c")
public class Categorizacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String descripcion;

	@Column(name="puntuacion_maxima")
	private BigDecimal puntuacionMaxima;

	//bi-directional many-to-one association to Areafuncional
	@OneToMany(mappedBy="categorizacion")
	private List<Areafuncional> areafuncionals;

	public Categorizacion() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPuntuacionMaxima() {
		return this.puntuacionMaxima;
	}

	public void setPuntuacionMaxima(BigDecimal puntuacionMaxima) {
		this.puntuacionMaxima = puntuacionMaxima;
	}

	public List<Areafuncional> getAreafuncionals() {
		return this.areafuncionals;
	}

	public void setAreafuncionals(List<Areafuncional> areafuncionals) {
		this.areafuncionals = areafuncionals;
	}

	public Areafuncional addAreafuncional(Areafuncional areafuncional) {
		getAreafuncionals().add(areafuncional);
		areafuncional.setCategorizacion(this);

		return areafuncional;
	}

	public Areafuncional removeAreafuncional(Areafuncional areafuncional) {
		getAreafuncionals().remove(areafuncional);
		areafuncional.setCategorizacion(null);

		return areafuncional;
	}

}