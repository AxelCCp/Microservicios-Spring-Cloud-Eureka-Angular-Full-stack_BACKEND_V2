package microservice.cursos.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import microservice.alumnos.commons.models.entity.Alumno;
import microservice.commons.services.CommonService;
import microservice.cursos.feign.IFeignClientAlumnos;
import microservice.cursos.feign.IFeignClientRespuestas;
import microservice.cursos.models.entity.Curso;
import microservice.cursos.models.repository.ICursoRepository;

@Service
public class CursoServiceImpl extends CommonService<Curso,ICursoRepository> implements ICursoService {

	
	//METODO PERSONALIZADO
	@Override
	@Transactional(readOnly=true)
	public Curso findCursoByAlumnoId(Long id) {
		// TODO Auto-generated method stub
		return repository.findCursoByAlumnoId(id);
	}

	@Override  //LOS METODOS QUE SE CONECTAN A OTRO MICROSERVICIO, NO LLEVAN EL TRANSACTUINAL.  NO SER Q INTERACTUEN CON LA BBDD.
	public Iterable<Long> obtenerExamenesConRespuestasPorAlumno(Long alumnoId) {
		// TODO Auto-generated method stub
		return feignClientRespuestas.obtenerExamenesConRespuestasPorAlumno(alumnoId);
	}

	@Override
	public Iterable<Alumno> obtenerAlumnosPorCurso(List<Long> ids) {
		// TODO Auto-generated method stub
		return feignClientAlumnos.obtenerAlumnosPorCurso(ids);
	}
	
	@Override
	@Transactional
	public void eliminarCursoAlumnoPorId(Long id) {
		// TODO Auto-generated method stub
		repository.eliminarCursoAlumnoPorId(id);
	}
	
	@Autowired
	private IFeignClientRespuestas feignClientRespuestas;
	
	@Autowired
	private IFeignClientAlumnos feignClientAlumnos;




}
