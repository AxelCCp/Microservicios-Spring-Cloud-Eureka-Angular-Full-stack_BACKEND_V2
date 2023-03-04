package microservice.cursos.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import microservice.alumnos.commons.models.entity.Alumno;
import microservice.commons.examenes.model.entity.Examen;

@Entity
@Table(name="cursos")
public class Curso implements Serializable {

	public Curso() {
		this.alumnos = new ArrayList<>();
		this.examenes = new ArrayList<>();
		this.cursoAlumnos = new ArrayList<>();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}
	
	public List<Alumno> getAlumnos() {
		return alumnos;
	}
	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}
	
	//PARA AGREGAR ALUMNOS A LA LISTA
	public void addAlumnos(Alumno alumno) {
		this.alumnos.add(alumno);
	}
	
	//PARA ELIMINAR ALUMNOS A LA LISTA
	public void removeAlumno(Alumno alumno) {
		this.alumnos.remove(alumno);
	}
	
	public List<Examen> getExamenes() {
		return examenes;
	}

	public void setExamenes(List<Examen> examenes) {
		this.examenes = examenes;
	}
	
	//PARA AGREGAR EXAMENES AL CURSO.
	public void addExamen(Examen examen) {
		this.examenes.add(examen);
	}
	
	//PARA QUITAR EXAMENES DEL CURSO  
	public void removeExamen(Examen examen) {
		this.examenes.remove(examen);
	}
	
	public List<CursoAlumno> getCursoAlumnos() {
		return cursoAlumnos;
	}

	public void setCursoAlumnos(List<CursoAlumno> cursoAlumnos) {
		this.cursoAlumnos = cursoAlumnos;
	}
	
	//PARA AGREGAR CURSOALUMNO
	public void addCursoAlumno(CursoAlumno cursoAlumno) {
		this.cursoAlumnos.add(cursoAlumno);
	}
	
	//PARA ELIMINAR CURSOALUMNO
	public void removeCursoAlumno(CursoAlumno cursoAlumno) {
		this.cursoAlumnos.remove(cursoAlumno);
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String nombre;
	@Column(name="create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	//@OneToMany(fetch = FetchType.LAZY)  //RELACIÓN DE UN CURSO A VARIOS ALUMNOS.	  ------>						// 73 : SE QUITA YA QUE YA NO EXISTE LA RELACION CON INTEGRIDAD REFERENCIAL CON LA TABLA ALUMNOS. ESTO PQ YA NO EXISTE ALUMNOS EN MYSQL.
	@Transient																										// 73 : SE DEJA COMO CAMPO FUERA DE LA TABLA YA Q IGUAL SE VA A NECESITAR DESPUÉS EL LISTADO DE ALUMNOS CON SUS DATOS. 
	private List<Alumno>alumnos;
	
	@ManyToMany																										//UN CURSO PUEDE TENER VARIOS EXAMENES Y A LA VEZ UN EXAMEN PUEDE ESTAR EN DIFERENTES CURSOS.
	private List<Examen>examenes;
	
	@JsonIgnoreProperties(value= {"curso"}, allowSetters = true)
	@OneToMany(fetch = FetchType.LAZY, mappedBy="curso", cascade= CascadeType.ALL, orphanRemoval = true)			//	73 : RELACION 1 CURSO A MUCHOS CURSOALUMNOS. // mapped : SE ESTABLECE LA RELACION BIDIRECCIONAL Y SE APUNTA AL TRIBUTO CURSO DE LA CONTRAPARTE. // CASCADE ALL : SI SE ELIMINA UN CURSO, ELIMNA TAMBN LA RELACIÓN DE CURSO CON ALUMNOS.
	private List<CursoAlumno>cursoAlumnos;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3415825813094397347L;
}
