package microservice.usuarios.models.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import microservice.usuarios.models.entity.Alumno;
import microservice.usuarios.models.repository.IAlumnoRepository;

@Service
public class AlumnoService  implements IAlumnoService{

	@Override
	@Transactional(readOnly=true)
	public Iterable<Alumno> findAll() {
		// TODO Auto-generated method stub
		return alumnoRepo.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<Alumno> porId(Long Id) {
		// TODO Auto-generated method stub
		return alumnoRepo.findById(Id);
	}

	@Override
	@Transactional
	public Alumno save(Alumno alumno) {
		// TODO Auto-generated method stub
		return alumnoRepo.save(alumno);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		alumnoRepo.deleteById(id);
	}

	
	@Autowired
	private IAlumnoRepository alumnoRepo;
	
}
