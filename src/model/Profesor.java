package model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the profesor database table.
 * 
 */
@Entity
@Table(name="PROFESOR", schema="public")
@NamedQueries({ 
	@NamedQuery(name="Profesor.findAll", query="SELECT p FROM Profesor p"),
	@NamedQuery(name="Profesor.findByNIF", query="SELECT p FROM Profesor p WHERE p.nif = :nif"),
	@NamedQuery(name="Profesor.findById", query="SELECT p FROM Profesor p WHERE p.id = :id")
})
public class Profesor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PROFESOR_ID_GENERATOR", sequenceName="SEQ_PROFESOR", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PROFESOR_ID_GENERATOR")
	private long id;

	private String apellido1;

	private String apellido2;

	private String contrasena;

	private String nif;

	private String nombre;

	private String notas;

	private Boolean permisos;

	//bi-directional many-to-many association to Aula
	@ManyToMany(mappedBy="profesors", fetch= FetchType.EAGER)
	private List<Aula> aulas;

	public Profesor() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getApellido1() {
		return this.apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return this.apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getContrasena() {
		return this.contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
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

	public Boolean getPermisos() {
		return this.permisos;
	}

	public void setPermisos(Boolean permisos) {
		this.permisos = permisos;
	}

	public List<Aula> getAulas() {
		return this.aulas;
	}

	public void setAulas(List<Aula> aulas) {
		this.aulas = aulas;
	}
	
	@Override
	public boolean equals(Object tea) {
		if(tea instanceof Profesor && this.id == ((Profesor) tea).getId()) {
			return true;
		}else {
			return false;
		}
	}

}