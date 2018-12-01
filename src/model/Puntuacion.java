package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the puntuacion database table.
 * 
 */
@Entity
@NamedQuery(name="Puntuacion.findAll", query="SELECT p FROM Puntuacion p")
public class Puntuacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private int valoracion;

	//bi-directional many-to-one association to Evaluacion
	@ManyToOne
	@JoinColumn(name="id_evaluacion")
	private Evaluacion evaluacion;

	//bi-directional many-to-one association to Item
	@ManyToOne
	@JoinColumn(name="id_item")
	private Item item;

	public Puntuacion() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getValoracion() {
		return this.valoracion;
	}

	public void setValoracion(int valoracion) {
		this.valoracion = valoracion;
	}

	public Evaluacion getEvaluacion() {
		return this.evaluacion;
	}

	public void setEvaluacion(Evaluacion evaluacion) {
		this.evaluacion = evaluacion;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}