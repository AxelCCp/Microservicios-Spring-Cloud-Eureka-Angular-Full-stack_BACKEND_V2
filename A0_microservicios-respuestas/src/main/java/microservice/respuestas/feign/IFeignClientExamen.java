package microservice.respuestas.feign;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import microservice.commons.examenes.model.entity.Examen;

@FeignClient(name="examenes-server")
public interface IFeignClientExamen {

	@GetMapping("/{id}")
	public Examen obtenerExamenPorId(@PathVariable Long id);
	
	//98
	@GetMapping("/respondidos-por-preguntas")																	
	public List<Long> obtenerExamenesIdsPorPreguntasIdRespondidas(@RequestParam List<Long>preguntaIds);
	
}
