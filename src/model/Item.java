package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the item database table.
 * 
 */
@Entity
@Table(name="ITEM", schema="public")
@NamedQuery(name="Item.findAll", query="SELECT i FROM Item i")
public class Item implements Serializable,Comparable<Item> {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ITEM_NUMERO_GENERATOR", sequenceName="SEQ_ITEM", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ITEM_NUMERO_GENERATOR")
	private long numero;

	private String descripcion;

	@Column(name="edad_asignada")
	private int edadAsignada;

	//bi-directional many-to-one association to Categorizacion
	@ManyToOne
	@JoinColumn(name="id_categorizacion")
	private Categorizacion categorizacion;

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

	public int getEdadAsignada() {
		return this.edadAsignada;
	}

	public void setEdadAsignada(int edadAsignada) {
		this.edadAsignada = edadAsignada;
	}

	public Categorizacion getCategorizacion() {
		return this.categorizacion;
	}

	public void setCategorizacion(Categorizacion categorizacion) {
		this.categorizacion = categorizacion;
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
	
	@Override
	public boolean equals(Object it) {
		if(it instanceof Item && ((Item) it).getNumero()==this.numero) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public int compareTo(Item it) {
		return Long.compare(this.numero, it.getNumero());
	}

}