package microservice.respuestas.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import microservice.respuestas.models.entity.Respuesta;
import microservice.respuestas.models.services.IRespuestaService;

@RestController
public class RespuestaController {

	@PostMapping
	public ResponseEntity<?>crear(@RequestBody Iterable<Respuesta>respuestas){
		
		respuestas = ((List<Respuesta>)respuestas).stream().map(r -> {
			r.setAlumnoId(r.getAlumno().getId());
			r.setPreguntaId(r.getPregunta().getId());																//95 SE CREA UNA RELACION ENTRE RESPUESTA CON LA PREGUNTA, PQ AHORA CUENDO SE ENVÍA EL CONJUNTO DE RESPUESTAS DESDE EL JSON, ESTE AHORA VIENE CON EL ALUMNO Y EL OBJ PREGUNTA. ESTO SE HACE POR LA RELACIÓN DISTRIBUIDA.
			return r;
		}).collect(Collectors.toList());
		
		Iterable<Respuesta>respuestasDB = respuestaService.saveAll(respuestas);
		return ResponseEntity.status(HttpStatus.CREATED).body(respuestasDB);
	}
	
	
	@GetMapping("/alumno/{alumnoId}/examen/{examenId}")
	public ResponseEntity<?>obtenerRespuestasPorAlumnoPorExamen(@PathVariable Long alumnoId, @PathVariable Long examenId){
		Iterable<Respuesta>respuestas = respuestaService.findRespuestaByAlumnoByExamen(alumnoId, examenId);
		return ResponseEntity.ok(respuestas);
	}
	
	
	//OBTIENE EL ID DE LOS EXAMENES QUE FUERON RESPONDIDOS POR EL ALUMNO
	@GetMapping("/alumno/{alumnoId}/examenes-respondidos")
	public ResponseEntity<?>obtenerExamenesConRespuestasPorAlumno(@PathVariable Long alumnoId){
		Iterable<Long>examenesIds = respuestaService.findExamenesIdsConRespuestasByAlumno(alumnoId);
		return ResponseEntity.ok(examenesIds);
		
	}
	
	@Autowired
	private IRespuestaService respuestaService;
}
