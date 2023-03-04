package microservice.usuarios.models.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import microservice.alumnos.commons.models.entity.Alumno;
import microservice.commons.services.CommonService;
import microservice.usuarios.feign.IFeignClientCursos;
import microservice.usuarios.models.repository.IAlumnoRepository;

@Service
public class AlumnoService extends CommonService<Alumno, IAlumnoRepository> implements IAlumnoService{

	//METODO PERSONALIZADO PARA BUSCAR ALUMNOS.
	@Override
	@Transactional(readOnly = true)
	public List<Alumno> findByNombreOrApellido(String texto) {
		return repository.findByNombreOrApellido(texto);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findAllById(Iterable<Long> ids) {
		return repository.findAllById(ids);
	}

	//METODO QUE CONSUME API DE MS-CURSOS PARA ELIMINAR REGISTRO DE ALUMNO EN LA TABLA CURSOALUMNOS.
	//NO VA EL TRANSACTIONAL, YA QUE ES UN MÉTODO QUE ESTÁ CONSUMIENTO UN REST DEL OTRO MICROSERVICIO.
	@Override
	public ResponseEntity<?> eliminarCursoAlumnoPorId(Long id) {
		// TODO Auto-generated method stub
		feignClientCursos.eliminarCursoAlumnoPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	
	//SE SOBREESCRIBE EL MÉTODO PARA ELIMINAR TAMBN EN LA TABLA CURSOALUMNOS LA RELACIÓN DEL ALUMNO CON CURSO.
	@Override
	@Transactional																// EL TRANSACTIONAL ES IMPORTANTE PQ ENVUELVE A TODO EL MÉTODO. SI EL deleteById Y EL eliminarCursoAlumnoPorId, LOGRAN CONCLUIR, SE ELIMINAN LOS DATOS. PERO SI UNO DE LOS DOS NO CONSIGUE ELIMINAR, NINGUNO DE LOS DOS MÉTODOS ELIMINARÁ. 
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		super.deleteById(id);
		this.eliminarCursoAlumnoPorId(id);
	}




	@Autowired
	private IFeignClientCursos feignClientCursos;

	
	
}
