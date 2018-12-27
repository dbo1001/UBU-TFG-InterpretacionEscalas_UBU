package model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the aula database table.
 * 
 */
@Entity
@Table(name = "AULA", schema = "public")
@NamedQuery(name = "Aula.findAll", query = "SELECT a FROM Aula a")
public class Aula implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private int capacidad;

	private String nombre;

	private String notas;

	// bi-directional many-to-one association to Alumno
	@OneToMany(mappedBy = "aula")
	private List<Alumno> alumnos;

	// bi-directional many-to-many association to Profesor
	@ManyToMany
	@JoinTable(name = "aula_profesor", joinColumns = { @JoinColumn(name = "ID_AULA") }, inverseJoinColumns = {
			@JoinColumn(name = "ID_PROFESOR") })
	private List<Profesor> profesors;

	public Aula() {
		this.alumnos = new ArrayList<Alumno>();
		this.profesors = new ArrayList<Profesor>();
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCapacidad() {
		return this.capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNotas() {
		return this.notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public List<Alumno> getAlumnos() {
		return this.alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public Alumno addAlumno(Alumno alumno) {
		getAlumnos().add(alumno);
		alumno.setAula(this);

		return alumno;
	}

	public Alumno removeAlumno(Alumno alumno) {
		getAlumnos().remove(alumno);
		alumno.setAula(null);

		return alumno;
	}

	public List<Profesor> getProfesors() {
		return this.profesors;
	}

	public void setProfesors(List<Profesor> profesors) {
		this.profesors = profesors;
	}

}