package microservice.cursos.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import microservice.alumnos.commons.models.entity.Alumno;



@Entity
@Table(name="cursos_alumnos")
public class CursoAlumno implements Serializable{

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAlumnoId() {
		return alumnoId;
	}

	public void setAlumnoId(Long alumnoId) {
		this.alumnoId = alumnoId;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Override																		// 73 : SE USA PARA ELIMINAR REGISTRO CON EL METODO REMOVE() QU ESTÁ EN LA CLASE ENTITY CURSO.
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof CursoAlumno)) {
			return false;
		}
		CursoAlumno ca = (CursoAlumno) obj;															
		return this.alumnoId != null && this.alumnoId.equals(ca.alumnoId);			// 73 : SI SE CUMPLE RETORNARÁ TRUE Y SI NO RETORNARÁ FALSE.   POR CADA OBJ DE LA LISTA A IR A BUSCAR PARA PODER ELIMINAR. Y CUANDO LO ENCUENTRE, VA A ELIMINAR.
	}
	
	
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	Long id;
	
	@Column(name="alumno_id", unique=true)
	private Long alumnoId;

	@JsonIgnoreProperties(value= {"cursoAlumnos"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="curso_id")													// 73 : SE ESTABLECE EL NOMBRE DE LA FOREIGN KEY.
	private Curso curso;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3332772209210524776L;
}
