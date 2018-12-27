package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the aula_profesor database table.
 * 
 */
@Embeddable
public class AulaProfesorPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_aula")
	private long idAula;

	@Column(name="id_profesor")
	private long idProfesor;

	public AulaProfesorPK() {
	}
	public long getIdAula() {
		return this.idAula;
	}
	public void setIdAula(long idAula) {
		this.idAula = idAula;
	}
	public long getIdProfesor() {
		return this.idProfesor;
	}
	public void setIdProfesor(long idProfesor) {
		this.idProfesor = idProfesor;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AulaProfesorPK)) {
			return false;
		}
		AulaProfesorPK castOther = (AulaProfesorPK)other;
		return 
			(this.idAula == castOther.idAula)
			&& (this.idProfesor == castOther.idProfesor);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idAula ^ (this.idAula >>> 32)));
		hash = hash * prime + ((int) (this.idProfesor ^ (this.idProfesor >>> 32)));
		
		return hash;
	}
}