package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the evaluacion database table.
 * 
 */
@Entity
@Table(name="EVALUACION", schema="public")
@NamedQueries({ 
	@NamedQuery(name="Evaluacion.findAll", query="SELECT e FROM Evaluacion e"),
	@NamedQuery(name="Evaluacion.findById", query="SELECT e FROM Evaluacion e WHERE e.id = :id"),
	@NamedQuery(name="Evaluacion.findByFecha", query="SELECT e FROM Evaluacion e WHERE e.fecha = :fecha")
})
public class Evaluacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="EVALUACION_ID_GENERATOR", sequenceName="SEQ_EVALUACION", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EVALUACION_ID_GENERATOR")
	private long id;

	private Timestamp fecha;

	//bi-directional many-to-one association to Alumno
	@ManyToOne
	@JoinColumn(name="id_alumno")
	private Alumno alumno;

	//bi-directional many-to-one association to Puntuacion
	@OneToMany(mappedBy="evaluacion", fetch= FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Puntuacion> puntuacions = new ArrayList<Puntuacion>();

	public Evaluacion() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public Alumno getAlumno() {
		return this.alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public List<Puntuacion> getPuntuacions() {
		return this.puntuacions;
	}

	public void setPuntuacions(List<Puntuacion> puntuacions) {
		this.puntuacions = puntuacions;
	}

	public Puntuacion addPuntuacion(Puntuacion puntuacion) {
		getPuntuacions().add(puntuacion);
		puntuacion.setEvaluacion(this);

		return puntuacion;
	}

	public Puntuacion removePuntuacion(Puntuacion puntuacion) {
		getPuntuacions().remove(puntuacion);
		puntuacion.setEvaluacion(null);

		return puntuacion;
	}
	
	@Override
	public boolean equals(Object eva) {
		if(eva instanceof Evaluacion && this.id == ((Evaluacion) eva).getId()) {
			return true;
		}else {
			return false;
		}
		
	}

}