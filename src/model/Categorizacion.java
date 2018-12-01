package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the categorizacion database table.
 * 
 */
@Entity
@Table(name="CATEGORIZACION", schema="public")
@NamedQuery(name="Categorizacion.findAll", query="SELECT c FROM Categorizacion c")
public class Categorizacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String descripcion;

	@Column(name="puntuacion_maxima")
	private int puntuacionMaxima;

	//bi-directional many-to-one association to Areafuncional
	@ManyToOne
	@JoinColumn(name="id_areafuncional")
	private Areafuncional areafuncional;

	//bi-directional many-to-one association to Item
	@OneToMany(mappedBy="categorizacion")
	private List<Item> items;

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

	public int getPuntuacionMaxima() {
		return this.puntuacionMaxima;
	}

	public void setPuntuacionMaxima(int puntuacionMaxima) {
		this.puntuacionMaxima = puntuacionMaxima;
	}

	public Areafuncional getAreafuncional() {
		return this.areafuncional;
	}

	public void setAreafuncional(Areafuncional areafuncional) {
		this.areafuncional = areafuncional;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Item addItem(Item item) {
		getItems().add(item);
		item.setCategorizacion(this);

		return item;
	}

	public Item removeItem(Item item) {
		getItems().remove(item);
		item.setCategorizacion(null);

		return item;
	}

}