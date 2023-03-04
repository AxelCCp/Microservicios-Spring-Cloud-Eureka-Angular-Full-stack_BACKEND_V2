package microservice.usuarios.models.repository;

import org.springframework.data.repository.CrudRepository;

import microservice.usuarios.models.entity.Alumno;

public interface IAlumnoRepository extends CrudRepository <Alumno, Long> {

}
