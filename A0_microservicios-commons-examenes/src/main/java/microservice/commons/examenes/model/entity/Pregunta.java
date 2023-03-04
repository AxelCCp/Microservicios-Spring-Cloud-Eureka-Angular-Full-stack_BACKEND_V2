package microservice.commons.examenes.model.entity;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="preguntas")
public class Pregunta implements Serializable{

	private static final long serialVersionUID = -1179961449241927706L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Examen getExamen() {
		return examen;
	}

	public void setExamen(Examen examen) {
		this.examen = examen;
	}

	
	//SE USA EL EQUALS AL ELIMINAR UNA PREGUNTA, PARA COMPARAR LA PREGUNTA QUE SE QUIERE ELIMINAR CON EL LISTADO DE PREGUNTAS QUE TIENE EL EXAMEN.
	@Override
	public boolean equals(Object obj) {
		//SE COMPARA ESTA INSTANCIA CON EL OBJ Q LLEGA POR PARÁMETRO. CON LA CLASE HACE EL MAPEO EN LA BBDD Y SI LO ENCUENTRA, LO ELIMINA.
		if(this == obj) {
			return true;
		}
				
		//SI OBJ NO ES INSTANCIA DE Pregunta, RETORNA FALSE
		if(!(obj instanceof Pregunta)) {
			return false;
		}
				
		//SI OBJ ES INSTANCIA DE Pregunta,
		Pregunta a = (Pregunta) obj;
				
		//SI EL ID != DE NULL Y SI ES IGUAL AL ID DE "a".    SI SE CUMPLE RETORNARÁ TRUE Y SI NO RETORNARÁ FALSE.   POR CADA OBJ DE LA LISTA A IR A BUSCAR PARA PODER ELIMINAR. Y CUANDO LO ENCUENTRE, VA A ELIMINAR.
		return this.id != null && this.id.equals(a.id);
	}
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String texto;
	
	@JsonIgnoreProperties(value="preguntas")// 33 : PARA QUE NO SE PRODUZCA UN LOOP INFINITO AL CREAR EL JSON DE EXAMEN - PREGUNTA. SE SUPRIME EL ATRIBUTO preguntas.
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="examen_id")			//33 : LA CLASE PREGUNTA ES LA DUEÑA DE LA RELACIÓN. YA QUE TIENE EL JOIN COLUMN CON LA FORANEA EXAMEN_ID.
	private Examen examen;
	
}
