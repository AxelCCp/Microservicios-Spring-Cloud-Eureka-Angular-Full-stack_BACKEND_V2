package microservice.cursos.models.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import microservice.cursos.models.entity.Curso;

//46 : SE CAMBIA CRUDREPOSITORY POR PAGING AND SORTING PARA PAGINACION.
public interface ICursoRepository extends PagingAndSortingRepository<Curso,Long>{

	//31
	//METODO PERSONALIZADO QUE DEVUELVE EL CURSO DE UN ALUMNO SEGÚN EL ID DEL ALUMNO.
	//FETCH : ES PARA POBLAR EL CURSO CON LA LISTA DE ALUMNOS.
	//@Query("select c from Curso c  join fetch c.alumnos a where a.id=?1")
	//public Curso findCursoByAlumnoId(Long id);
	
	
	// 74 : SE MODIFICA CONSULTA PERSONALIZADA YA Q DABA UN ERROR EN LA PARTIDA, PQ EN EL ENTITY CURSO, SE PUSO EL ATRIBUTO ALUMNOS CON @TRANSIENT.. POR LO TANTO SE USA AHORA cursoAlumnos
	@Query("select c from Curso c join fetch c.cursoAlumnos a where a.alumnoId=?1")
	public Curso findCursoByAlumnoId(Long id);
	
	
	
	//81 : MÉTODO PERSONALIZADO : SI EN MS-USUARIOS SE ELIMINA UN ALUMNO (POSTGRES), TAMBN HAY QUE ELIMINAR EN LA TABLA CURSOALUMNOS EL ID DEL ALUMNO.
	//ESTA SE USA CUANDO EL MÉTODO PERSONALIZADO ES POST-PUT Ó DELETE.
	@Modifying
	@Query("delete from CursoAlumno ca where ca.alumnoId=?1")
	public void eliminarCursoAlumnoPorId(Long id);
	
	
}
