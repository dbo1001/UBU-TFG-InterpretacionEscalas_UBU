package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the evaluacion database table.
 * 
 */
@Entity
@Table(name="EVALUACION", schema="public")
@NamedQuery(name="Evaluacion.findAll", query="SELECT e FROM Evaluacion e")
public class Evaluacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="EVALUACION_ID_GENERATOR", sequenceName="SEQ_EVALUACION")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="EVALUACION_ID_GENERATOR")
	private long id;

	private Timestamp fecha;

	//bi-directional many-to-one association to Alumno
	@ManyToOne
	@JoinColumn(name="id_alumno")
	private Alumno alumno;

	//bi-directional many-to-one association to Puntuacion
	@OneToMany(mappedBy="evaluacion")
	private List<Puntuacion> puntuacions;

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

}