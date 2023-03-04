package microservice.examenes.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import microservice.commons.examenes.model.entity.Examen;

//public interface IExamenRepository extends CrudRepository<Examen,Long>{

//46 : SE CAMBIA PARA USAR PAGINACIÓN.
public interface IExamenRepository extends PagingAndSortingRepository<Examen, Long>{
	
	//METODO PERSONALIZADO PARA BUSCAR UN EXAMEN POR EL NOMBRE DEL EXAMEN. ESTO ES PARA BUSCAR UN EXAMEN Y LUEGO ASIGNARSELO A UN CURSO.
	//"SELECCIONA DE EXAMEN DONDE EL NOMBRE ES COMO XXXX".		//EL DOBLE %% ES PARA Q BUSQUE LO QUE SE ESTÁ ESCRIBIENDO, A LO LARGO DE TODA LA CADENA, IZQ Y DER.
	@Query("select e from Examen e where e.nombre like %?1%")
	public List<Examen>findByNombre(String texto);
	
	//97 : ES UNA CONSULTA JPA A "PREGUNTA" --> SE HACE UN JOIN AL ATRIBUTO EXAMEN PARA OBTENER EL "EXAMEN ID". OBTENIENDO EL EXAMEN ID, ESTOS SE AGRUPAN PARA Q NO SE REPITA EL ID POR CADA PREGUNTA.
	//		LO IMPORTANTE ES EL WHERE, QUIERE DECIR : "DONDE LA PREGUNTAID SE ENCUENTRE DENTRO DE LA COLECCION QUE SE RECIBE POR PARAMETRO" 
	@Query("select e.id from Pregunta p join p.examen e where p.id in ?1 group by e.id")
	public Iterable<Long>findExamenesIdsConRespuestasByPreguntaIds(Iterable<Long>preguntaIds);
}
