package microservice.respuestas.models.services;

import microservice.respuestas.models.entity.Respuesta;

public interface IRespuestaService {

	public Iterable<Respuesta>saveAll(Iterable<Respuesta> respuestas); 
	
	public Iterable<Respuesta>findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);
	
	public Iterable<Long>findExamenesIdsConRespuestasByAlumno(Long alumnoId);
	
	//OBTIENE RESPUESTAS POR EL ID DEL ALUMNO.
	public Iterable<Respuesta>findByAlumnoId(Long alumnoId);
}
