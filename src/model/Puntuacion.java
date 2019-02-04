package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the puntuacion database table.
 * 
 */
@Entity
@Table(name = "PUNTUACION", schema = "public")
@NamedQueries({ 
	@NamedQuery(name = "Puntuacion.findAll", query = "SELECT p FROM Puntuacion p"),
	@NamedQuery(name = "Puntuacion.findById", query = "SELECT p FROM Puntuacion p WHERE p.id = :id")
})
public class Puntuacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PUNTUACION_ID_GENERATOR", sequenceName = "SEQ_PUNTUACION", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PUNTUACION_ID_GENERATOR")
	private long id;

	private int valoracion;

	// bi-directional many-to-one association to Evaluacion
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "id_evaluacion") 
	private Evaluacion evaluacion;

	// bi-directional many-to-one association to Item
	@ManyToOne
	@JoinColumn(name = "id_item")
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

	@Override
	public boolean equals(Object pun) {
		if (pun instanceof Puntuacion && ((Puntuacion) pun).getItem().getNumero() == this.item.getNumero()
				&& ((Puntuacion) pun).getEvaluacion().getId() == this.evaluacion.getId()) {
			return true;
		} else {
			return false;
		}
	}

}