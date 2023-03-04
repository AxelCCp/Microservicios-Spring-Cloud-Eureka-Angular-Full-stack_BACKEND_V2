package microservice.cursos.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="respuestas-server")
public interface IFeignClientRespuestas {
	
	@GetMapping("/alumno/{alumnoId}/examenes-respondidos")
	public Iterable<Long>obtenerExamenesConRespuestasPorAlumno(@PathVariable Long alumnoId);

}
