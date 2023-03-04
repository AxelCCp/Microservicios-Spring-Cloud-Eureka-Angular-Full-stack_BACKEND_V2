package microservice.usuarios.models.service;


import java.util.List;

import org.springframework.http.ResponseEntity;
import microservice.alumnos.commons.models.entity.Alumno;
import microservice.commons.services.ICommonService;

public interface IAlumnoService extends ICommonService<Alumno>{

	//METODO PERSONALIZADO PARA BUSCAR ALUMNOS.
	public List<Alumno>findByNombreOrApellido(String texto); 
	
	//ENCUENTRA UN ITERABLE DE ALUMNOS SEGÚN UNA LISTA DE ID'S
	Iterable<Alumno> findAllById(Iterable<Long> ids);
	
	//METODO QUE CONSUME API DE MS-CURSOS PARA ELIMINAR REGISTRO DE ALUMNO EN LA TABLA CURSOALUMNOS.
	public ResponseEntity<?>eliminarCursoAlumnoPorId(Long id);
	
}
