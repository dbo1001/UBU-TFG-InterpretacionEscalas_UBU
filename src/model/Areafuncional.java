package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the areafuncional database table.
 * 
 */
@Entity
@Table(name="AREAFUNCIONAL", schema="public")
@NamedQuery(name="Areafuncional.findAll", query="SELECT a FROM Areafuncional a")
public class Areafuncional implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String descripcion;

	@Column(name="puntuacion_maxima")
	private int puntuacionMaxima;

	//bi-directional many-to-one association to Categorizacion
	@OneToMany(mappedBy="areafuncional")
	private List<Categorizacion> categorizacions;

	public Areafuncional() {
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

	public int getPuntuacionMaxima() {
		return this.puntuacionMaxima;
	}

	public void setPuntuacionMaxima(int puntuacionMaxima) {
		this.puntuacionMaxima = puntuacionMaxima;
	}

	public List<Categorizacion> getCategorizacions() {
		return this.categorizacions;
	}

	public void setCategorizacions(List<Categorizacion> categorizacions) {
		this.categorizacions = categorizacions;
	}

	public Categorizacion addCategorizacion(Categorizacion categorizacion) {
		getCategorizacions().add(categorizacion);
		categorizacion.setAreafuncional(this);

		return categorizacion;
	}

	public Categorizacion removeCategorizacion(Categorizacion categorizacion) {
		getCategorizacions().remove(categorizacion);
		categorizacion.setAreafuncional(null);

		return categorizacion;
	}

}