package microservice.usuarios.models.service;

import java.util.Optional;

import microservice.usuarios.models.entity.Alumno;

public interface IAlumnoService {

	
	public Iterable<Alumno>findAll();
	public Optional<Alumno>porId(Long Id);
	public Alumno save(Alumno alumno);
	public void deleteById(Long id);
	
}
