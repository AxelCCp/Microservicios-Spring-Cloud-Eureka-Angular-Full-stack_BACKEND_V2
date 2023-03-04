package microservice.examenes.models.service;

import java.util.List;

import microservice.commons.examenes.model.entity.Asignatura;
import microservice.commons.examenes.model.entity.Examen;
import microservice.commons.services.ICommonService;

public interface IExamenService extends ICommonService<Examen>{

	//METODO PERSONALIZADO PARA BUSCAR UN EXAMEN POR EL NOMBRE DEL EXAMEN.
	public List<Examen>findByNombre(String texto);
	
	public Iterable<Asignatura>findAllAsignaturas();
	
	public Iterable<Long>findExamenesIdsConRespuestasByPreguntaIds(Iterable<Long>preguntaIds);
	
}
