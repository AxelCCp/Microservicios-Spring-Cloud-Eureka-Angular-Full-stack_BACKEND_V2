package microservice.commons.examenes.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="asignaturas")
public class Asignatura implements Serializable{

	private static final long serialVersionUID = 5380157043098378959L;

	public Asignatura() {
		this.hijos = new ArrayList<>();
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

	public Asignatura getPadre() {
		return padre;
	}

	public void setPadre(Asignatura padre) {
		this.padre = padre;
	}

	public List<Asignatura> getHijos() {
		return hijos;
	}

	public void setHijos(List<Asignatura> hijos) {
		this.hijos = hijos;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	
	@JsonIgnoreProperties(value = {"hijos"})													//EVITA EL LOOP INFINITO. OMMITE asignaturasHijos.
	@ManyToOne(fetch = FetchType.LAZY)																	//ESTE ES UN SELF JOIN EN SQL : UNA ASIGNATURA PUEDE TENER VARIAS ASIGNATURAS HIJAS.
	private Asignatura padre;
	
	@JsonIgnoreProperties(value = {"padre"}, allowSetters = true)								//EVITA EL LOOP INFINITO. EN LAS ASIGNATURAS HIJAS SE OMITE LA ASIGNATURA PADRE, PERO SE PERMITEN LOS SETTER. 
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "padre", cascade = CascadeType.ALL)			//ES MAPEADO POR SU RELACIÓN INVERSA QUE ES asignaturaPadre.
	private List<Asignatura>hijos;
}
