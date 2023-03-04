package microservice.examenes.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import microservice.commons.examenes.model.entity.Asignatura;
import microservice.commons.examenes.model.entity.Examen;
import microservice.commons.services.CommonService;
import microservice.examenes.models.repository.IAsignaturaRepository;
import microservice.examenes.models.repository.IExamenRepository;

@Service
public class ExamenServiceImpl extends CommonService<Examen,IExamenRepository> implements IExamenService {

	@Override
	@Transactional(readOnly = true)
	public List<Examen> findByNombre(String texto) {
		return repository.findByNombre(texto);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Asignatura> findAllAsignaturas() {
		return asignaturaRepositoty.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Long> findExamenesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntaIds) {
		// TODO Auto-generated method stub
		return repository.findExamenesIdsConRespuestasByPreguntaIds(preguntaIds);
	}
	
	@Autowired
	private IAsignaturaRepository asignaturaRepositoty;

	
}
