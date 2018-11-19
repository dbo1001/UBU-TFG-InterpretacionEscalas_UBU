package model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the areafuncional database table.
 * 
 */
@Entity
@NamedQuery(name="Areafuncional.findAll", query="SELECT a FROM Areafuncional a")
public class Areafuncional implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String descripcion;

	@Column(name="puntuacion_maxima")
	private BigDecimal puntuacionMaxima;

	//bi-directional many-to-one association to Categorizacion
	@ManyToOne
	@JoinColumn(name="id_categorizacion")
	private Categorizacion categorizacion;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="areafuncional")
	private List<Item> items;

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

	public BigDecimal getPuntuacionMaxima() {
		return this.puntuacionMaxima;
	}

	public void setPuntuacionMaxima(BigDecimal puntuacionMaxima) {
		this.puntuacionMaxima = puntuacionMaxima;
	}

	public Categorizacion getCategorizacion() {
		return this.categorizacion;
	}

	public void setCategorizacion(Categorizacion categorizacion) {
		this.categorizacion = categorizacion;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Item addItem(Item item) {
		getItems().add(item);
		item.setAreafuncional(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setAreafuncional(null);

		return item;
	}

}