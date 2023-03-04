package microservice.respuestas.models.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import microservice.commons.examenes.model.entity.Examen;
import microservice.commons.examenes.model.entity.Pregunta;
import microservice.respuestas.feign.IFeignClientExamen;
import microservice.respuestas.models.entity.Respuesta;
import microservice.respuestas.models.repository.RespuestaRepository;

@Service
public class RespuestaServiceImpl implements IRespuestaService{

	@Override																													//@Transactional(readOnly = true) : 95 : MONGO DB CON SPRING DATA, NO ES TRANSACCIONAL, POR LO TANTO SE QUITA.
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
		return respuestaRepository.saveAll(respuestas);
	}
	
	@Override																													//@Transactional(readOnly = true) : 95 : MONGO DB CON SPRING DATA, NO ES TRANSACCIONAL, POR LO TANTO SE QUITA. 
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
		Examen examen = feignClientExamen.obtenerExamenPorId(examenId); 
		List<Pregunta>preguntas = examen.getPreguntas();
		List<Long>preguntaIds = preguntas.stream().map(p -> {
			return p.getId();
		}).collect(Collectors.toList());
		List<Respuesta>respuestas = (List<Respuesta>) respuestaRepository.findRespuestaByAlumnoByPreguntasIds(alumnoId, preguntaIds);
		respuestas = respuestas.stream().map(r -> {
			preguntas.forEach(p -> {
				if(p.getId() == r.getPreguntaId()) {
					r.setPregunta(p);
				}
			});
			return r;
		}).collect(Collectors.toList());
		return respuestas;
	}
	
	@Override																													//@Transactional(readOnly = true) : 95 : MONGO DB CON SPRING DATA, NO ES TRANSACCIONAL, POR LO TANTO SE QUITA.
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {
		List<Respuesta>respuestasAlumno = (List<Respuesta>) respuestaRepository.findByAlumnoId(alumnoId);
		List<Long>examenIds = Collections.emptyList();			//SE CREA UNA LISTA VACÍA.
		
		if(respuestasAlumno.size() > 0) {
			List<Long>preguntaIds = respuestasAlumno.stream().map(r -> r.getPreguntaId()).collect(Collectors.toList());
			examenIds = feignClientExamen.obtenerExamenesIdsPorPreguntasIdRespondidas(preguntaIds);
		}
		return examenIds;
	}
	

	@Override
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId) {
		// TODO Auto-generated method stub
		return respuestaRepository.findByAlumnoId(alumnoId);
	}


	
	
	@Autowired
	private RespuestaRepository respuestaRepository;

	@Autowired
	private IFeignClientExamen feignClientExamen;

	
}
