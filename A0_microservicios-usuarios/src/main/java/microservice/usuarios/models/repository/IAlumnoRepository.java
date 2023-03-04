package microservice.usuarios.models.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import microservice.alumnos.commons.models.entity.Alumno;

//public interface IAlumnoRepository extends CrudRepository <Alumno, Long> {
//46 : SE CAMBIA PARA PODER USAR PAGINACIÓN.

public interface IAlumnoRepository extends PagingAndSortingRepository<Alumno, Long> {
	
	
	
	//VERSION 1
	//ESTE MÉTODO ES PARA BUSCAR ALUMNOS. CONSULTA PERSONALIZADA CON HQL.
	//"SELECCIONA DE ALUMNO, DONDE EL NOMBRE ES COMO O EL APELLIDO ES COMO" ...  RECIBE EL NOMBRE O EL APELLIDO POR PARAMETRO
	//@Query("select a from Alumno a where a.nombre like %?1% or a.apellido like %?1%")
	//public List<Alumno>findByNombreOrApellido(String texto); //SI SE LE PONE OTRO NOMBRE AL MÉTODO IGUAL FUNCIONA, YA QUE MANDA PRIMERO EL @QUERY.
	
	//VERSION 2
	//AHORA QUE SE ESTÁ USANDO POSTGRES, POSTGRES DISTINGUE ENTRE MINÚSCULAS Y MAYÚSCULAS, ENTONCES, O SE PASA TODO A MAYÚSULAS O TODO A MINÚSCULAS, AL BUSCAR POR NOMBRE.
	//LA FUNCIÓN UPPER NO SOPORTA ESTO "%?1%" POR LO TANTO HAY QUE CONCATENAR.
	@Query("select a from Alumno a where upper(a.nombre) like upper(concat('%', ?1, '%')) or upper(a.apellido) like upper(concat('%', ?1, '%'))")
	public List<Alumno>findByNombreOrApellido(String texto); 
	

}
