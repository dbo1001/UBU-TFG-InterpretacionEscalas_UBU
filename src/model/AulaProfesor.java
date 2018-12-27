package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the aula_profesor database table.
 * 
 */
@Entity
@Table(name="aula_profesor", schema="public")
@NamedQuery(name="AulaProfesor.findAll", query="SELECT a FROM AulaProfesor a")
public class AulaProfesor implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AulaProfesorPK id;

	public AulaProfesor() {
	}

	public AulaProfesorPK getId() {
		return this.id;
	}

	public void setId(AulaProfesorPK id) {
		this.id = id;
	}

}