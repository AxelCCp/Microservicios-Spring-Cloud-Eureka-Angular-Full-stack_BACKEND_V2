package microservice.respuestas.models.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import microservice.alumnos.commons.models.entity.Alumno;
import microservice.commons.examenes.model.entity.Pregunta;

@Document(collection="respuestas")																	//89 : @ENTITY Y @TABLE SE QUITAN PQ AHORA SE USA MONGO.
public class Respuesta {
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Alumno getAlumno() {
		return alumno;
	}
	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}
	public Pregunta getPregunta() {
		return pregunta;
	}
	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}
	public Long getAlumnoId() {
		return alumnoId;
	}
	public void setAlumnoId(Long alumnoId) {
		this.alumnoId = alumnoId;
	}
	public Long getPreguntaId() {
		return preguntaId;
	}
	public void setPreguntaId(Long preguntaId) {
		this.preguntaId = preguntaId;
	}


	@Id
	private String id;
	
	private String texto;
	
	//@ManyToOne(fetch=FetchType.LAZY)															//UN ALUMNO PUEDE TENER MUCHAS RESPUESTAS. MIENTRAS Q LAS RESPUESTAS SON DE UN SOLO ALUMNO EN PARTICULAR.
	//@Transient																				//89 : SE QUITA PQ AHORA SE USA MONGO	//84 : YA NO HAY UNA RELACIÓN REFERENCIAL ENTRE TABLAS. LA TABLA ALUMNOS YA NO EXISTE. POR ESO SE COMENTA EL MANY TO ONE.
	@Transient																					//89 : ESTE NUEVO TRANSIENT NO ES DE JAVAX.
	private Alumno alumno;
	
	//@OneToOne(fetch = FetchType.LAZY)															//89 : SE QUITA PQ AHORA SE USA MONGO	//LA RELACION ES DE 1 A 1 ENTRE PREGUNTA Y RESPUESTA.
	@Transient
	private Pregunta pregunta;
	
	//@Column(name="alumno_id")
	private Long alumnoId;																		//89 : SE QUITA PQ AHORA SE USA MONGO	//SE CREA CAMPO PARA ALMACENAR LA LLAVE DEL ALUMNO. EL ALUMNO_ID

	private Long preguntaId;
}
