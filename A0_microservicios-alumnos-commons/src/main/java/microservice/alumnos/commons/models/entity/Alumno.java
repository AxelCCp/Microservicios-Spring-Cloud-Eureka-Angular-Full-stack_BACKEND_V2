package microservice.alumnos.commons.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="alumnos")
public class Alumno implements Serializable{

	private static final long serialVersionUID = -6729343235819139099L;
	
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	//SE SOBREESCRIBE METODO
	@Override
	public boolean equals(Object obj) {
		//SE COMPARA ESTA INSTANCIA CON EL OBJ Q LLEGA POR PARÁMETRO. CON LA CLASE HACE EL MAPEO EN LA BBDD Y SI LO ENCUENTRA, LO ELIMINA.
		if(this == obj) {
			return true;
		}
		
		//SI OBJ NO ES INSTANCIA DE ALUMNO, RETORNA FALSE
		if(!(obj instanceof Alumno)) {
			return false;
		}
		
		//SI OBJ ES INSTANCIA DE ALUMNO,
		Alumno a = (Alumno) obj;
		
		//SI EL ID != DE NULL Y SI ES IGUAL AL ID DE "a".    SI SE CUMPLE RETORNARÁ TRUE Y SI NO RETORNARÁ FALSE.   POR CADA OBJ DE LA LISTA A IR A BUSCAR PARA PODER ELIMINAR. Y CUANDO LO ENCUENTRE, VA A ELIMINAR.
		return this.id != null && this.id.equals(a.id);
	}
	
	//ESTE MÉTODO ES PARA QUE RETORNE UN CODIGO DE IDENTIFICACION  DE LA FOTO. // hashCode() GENERA UN IDENTIFICADOR ÚNICO Y SE HEREDA DE LA CLASE OBJECT. CADA OBJ TIENE UN IDENTIFICADOR UNICO Y CON HASHCODE SE COMPARAN EN EL MÉTODO EQUALS().
	public Integer getFotoHashCode() {
		return (this.foto != null) ? this.foto.hashCode() : null;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String nombre;
	@NotEmpty
	private String apellido;
	@NotEmpty
	@Email
	private String email;
	@Column(name="create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
															//EN FOTO SE GUARDARÁ UN CONTENIDO BINARIO. 
	@Lob													//LOB PERMITE QUE UNA PROPIEDAD O CAMPO PERSISTENTE, DEBE CONSERVARSE COMO UN OBJ GRANDE PARA UN TIPO DE OBJ GRANDE, Y Q ES COMPATIBLE CON LA BBDD. 
	@JsonIgnore												//COMO EL CONTENIDO EN BINARIO ES MUY GRANDE Y NO TIENE IMPORTANCIA EN EL JSON, SE IGNORA ESTE ATRIBUTO. SE TUVO Q AGREGAR ESTA DEPENDENCIA spring-boot-starter-json DIRECTAMENTE PQ NO TIENE LA DEPENDENCIA DE SPRING WEB. 
	private byte[] foto;
	
	
}
