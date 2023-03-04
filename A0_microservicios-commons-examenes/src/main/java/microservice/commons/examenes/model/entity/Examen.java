package microservice.commons.examenes.model.entity;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="examenes")
public class Examen implements Serializable {
	
	private static final long serialVersionUID = -1368620360641536608L;

	
	public Examen() {
		this.preguntas = new ArrayList<>();
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

	public List<Pregunta> getPreguntas() {
		return preguntas;
	}
	
	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}
	
	public Boolean getRespondido() {
		return Respondido;
	}

	public void setRespondido(Boolean respondido) {
		Respondido = respondido;
	}

	//MODIFICACIÓN SETTER:
	//HAY Q MODIFICAR EL SET PARA INCLUIR LA RELACIÓN INVERSA. PARA Q POR CADA PREGUNTA ASIGNE EL EXAMEN.
	//CUANDO UNO CREA UN EXAMEN CON SUS PREGUNTAS CON EL POST, SE ASIGNA "EXAMEN -> A -> PREGUNTAS"    Y FALTA ASIGNAR	 "LA PREGUNTA -> AL -> EXAMEN"      ....SIN ESTO QUEDARÍA LA FOREIGN KEY DEL EXAMEN_ID EN NULL.
	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas.clear();	// CLASE 33 MIN 12.
		preguntas.forEach(p -> {
			this.addPregunta(p);
		});
	}


	//METODO PARA AGREGAR PREGUNTAS AL EXAMEN.
	public void addPregunta(Pregunta pregunta) {
		this.preguntas.add(pregunta);
		//SE ASIGNA EL EXAMEN A LA PREGUNTA.
		pregunta.setExamen(this);	
	}
	
	//METODO PARA AGREGAR PREGUNTAS AL EXAMEN.
	public void removePregunta(Pregunta pregunta) {
		this.preguntas.remove(pregunta);
		//SE QUITA EL EXAMEN A LA PREGUNTA CON UN NULL.
		pregunta.setExamen(null);	
	}

	//38
	@Override
	public boolean equals(Object obj) {
		//SE COMPARA ESTA INSTANCIA CON EL OBJ Q LLEGA POR PARÁMETRO. CON LA CLASE HACE EL MAPEO EN LA BBDD Y SI LO ENCUENTRA, LO ELIMINA.
		if(this == obj) {
			return true;
		}
				
		//SI OBJ NO ES INSTANCIA DE Examen, RETORNA FALSE
		if(!(obj instanceof Examen)) {
			return false;
		}
				
		//SI OBJ ES INSTANCIA DE Examen,
		Examen a = (Examen) obj;
			
		//SI EL ID != DE NULL Y SI ES IGUAL AL ID DE "a".    SI SE CUMPLE RETORNARÁ TRUE Y SI NO RETORNARÁ FALSE.   POR CADA OBJ DE LA LISTA A IR A BUSCAR PARA PODER ELIMINAR. Y CUANDO LO ENCUENTRE, VA A ELIMINAR.
		return this.id != null && this.id.equals(a.id);
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Size(min=4, max=30)
	private String nombre;
	
	@Temporal(TemporalType.TIMESTAMP)	//FECHA COMPLETA.
	@Column(name="create_at")
	private Date createAt;
	
	@JsonIgnoreProperties(value="examen", allowSetters = true)// 33 : PARA QUE NO SE PRODUZCA UN LOOP INFINITO AL CREAR EL JSON DE EXAMEN - PREGUNTA. SE SUPRIME EL ATRIBUTO EXAMEN.    	//SE PERMITEN LOS SETTERS YA Q PUEDE DAR ALGUN ERROR. 
	@OneToMany(mappedBy="examen", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)	//33 : CUANDO SE ELIMINA UN EXAMEN SE TIENEN Q ELIMINAR SUS PREGUNTAS. CUANDO SE CREA UN EXAMEN TAMBN SE CREAN SUS PREGUNTAS. LA IDEA ES Q SE CREE TODO JUNTO, EL EXAMEN Y LAS PREGUNTAS, POR ESO "ALL".   	 // orphanRemoval = true) : CADA VEZ Q SE ELIMINA UNA PREGUNTA DE LA LISTA, LA PREGUNTA ELIMINADA TIENE Q ASIGNAR LA REFERENCIA DEL EXAMEN EN NULL.   //TAMBN, CUALQUIER PREGUNTA QUE NO ESTÉ ASIGNADA A UN EXAMEN, LA VA A ELIMINAR.  //mappedBy="examen" : PARA ESTABLECER LA RELACIÓN BI DIRECCIONAL.
	private List<Pregunta>preguntas;
	
	//@NotNull
	@ManyToOne(fetch = FetchType.LAZY)//MUCHOS EXÁMENES PUEDEN ESTAR ASOCIADOS A UNA SOLA ASIGNATURA.			//LA RELACIÓN ES UNIDIRECCIONAL, SE QUIERE SABER DE QUÉ ASIGNATURA ES UN EXAMEN, PERO NO INTERESA SABER LOS EXAMENES DE UNA ASIGNATURA.
	private Asignatura asignatura;
	
	@Transient//ATRIBUTO Q NO SE MAPEA A LA TABLA.
	private Boolean Respondido;
}
