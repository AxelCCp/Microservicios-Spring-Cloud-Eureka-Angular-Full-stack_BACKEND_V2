package microservice.cursos.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import microservice.alumnos.commons.models.entity.Alumno;

@FeignClient(name="usuarios-server")
public interface IFeignClientAlumnos {

	@GetMapping("alumnos-por-curso")							
	public Iterable<Alumno>obtenerAlumnosPorCurso(@RequestParam List<Long>ids);
	
}
