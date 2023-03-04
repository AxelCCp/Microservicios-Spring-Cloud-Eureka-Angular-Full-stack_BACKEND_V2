package microservice.usuarios.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="cursos-server")
public interface IFeignClientCursos {
	
	@DeleteMapping("/eliminar-alumno-curso/{id}")
	public void eliminarCursoAlumnoPorId(@PathVariable Long id);

}
