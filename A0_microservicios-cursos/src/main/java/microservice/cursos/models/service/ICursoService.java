package microservice.cursos.models.service;

import java.util.List;

import microservice.alumnos.commons.models.entity.Alumno;
import microservice.commons.services.ICommonService;
import microservice.cursos.models.entity.Curso;

public interface ICursoService extends ICommonService<Curso>{

	//METODO PERSONALIZADO
	public Curso findCursoByAlumnoId(Long id);
	
	public Iterable<Long>obtenerExamenesConRespuestasPorAlumno(Long alumnoId);
	
	public Iterable<Alumno>obtenerAlumnosPorCurso(List<Long>ids);
	
	public void eliminarCursoAlumnoPorId(Long id);
	
}
