package microservice.examenes.models.repository;

import org.springframework.data.repository.CrudRepository;

import microservice.commons.examenes.model.entity.Asignatura;

public interface IAsignaturaRepository extends CrudRepository<Asignatura, Long>{

}
