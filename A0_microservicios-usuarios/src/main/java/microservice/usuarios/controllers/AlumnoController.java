package microservice.usuarios.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import microservice.alumnos.commons.models.entity.Alumno;
import microservice.commons.controllers.CommonController;
import microservice.usuarios.models.service.IAlumnoService;

//SERVICE VIENE DEL COMMON CONTROLLER.

@RestController
public class AlumnoController extends CommonController<Alumno,IAlumnoService>{

	
	//SIN FOTO
	@PutMapping("/{id}")
	public ResponseEntity<?>editar(@Valid @RequestBody Alumno alumno, BindingResult result, @PathVariable Long id){
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Alumno> o = service.porId(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Alumno alumnodb = o.get();
		alumnodb.setNombre(alumno.getNombre());
		alumnodb.setApellido(alumno.getApellido());
		alumnodb.setEmail(alumno.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnodb));
	}
	
	
	@GetMapping("/filtrar/{texto}")
	public ResponseEntity<?>filtrar(@PathVariable String texto) {
		return ResponseEntity.ok(service.findByNombreOrApellido(texto));
	}

	//CON FOTO 
	//NO SE USA @REQUESTBODY, PQ AQUÍ NO SE RECIBE UN JSON, SE RECIBE UN MULTIPARTFORMDATA, DONDE VIENE LA FOTO Y TODA LA WEA. Y LA CLASE MultipartFile MANEJA ESTOS DATOS
	@PostMapping("/crear-con-foto")
	public ResponseEntity<?> crearConFoto(@Valid Alumno alumno, BindingResult result, @RequestParam MultipartFile archivo) throws IOException {
		if(!archivo.isEmpty()) {
			alumno.setFoto(archivo.getBytes());
		}
		return super.crear(alumno, result);
	}
	
	//CON FOTO 
	//NO SE USA @REQUESTBODY, PQ AQUÍ NO SE RECIBE UN JSON, SE RECIBE UN MULTIPARTFORMDATA, DONDE VIENE LA FOTO Y TODA LA WEA. Y LA CLASE MultipartFile MANEJA ESTOS DATOS
	//localhost:8090/api/alumnos/crear-con-foto : EN POSTMAN YA NO SE USA EL RAW. SE USA ... BODY -> FORM-DATA
	@PutMapping("/editar-con-foto/{id}")
	public ResponseEntity<?>editarConFoto(@Valid Alumno alumno, BindingResult result, @PathVariable Long id, @RequestParam MultipartFile archivo) throws IOException{

		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Alumno> o = service.porId(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Alumno alumnodb = o.get();
		alumnodb.setNombre(alumno.getNombre());
		alumnodb.setApellido(alumno.getApellido());
		alumnodb.setEmail(alumno.getEmail());
		if(!archivo.isEmpty()) {
			alumnodb.setFoto(archivo.getBytes());
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnodb));
	}
	
	//METODO PARA VISUALIZAR LA IMAGEN
	@GetMapping("uploads/img/{id}")
	public ResponseEntity<?>verFoto(@PathVariable Long id){
		Optional<Alumno> o = service.porId(id);
		if(o.isEmpty() || o.get().getFoto() == null) {
			return ResponseEntity.notFound().build();
		}
		Resource imagen = new ByteArrayResource(o.get().getFoto());
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);	
	}
	
	//ESTE REST VA A SER CONSUMIDO POR MS-CURSOS-SERVER, MEDIANTE FEIGN
	@GetMapping("alumnos-por-curso")							
	public ResponseEntity<?>obtenerAlumnosPorCurso(@RequestParam List<Long>ids){							//76 : DEJAR CON LIST EL PARÁMETRO, YA QUE UN ITERABLE PRODUCE UN ERROR DE COMUNICACIÓN AL USAR FEIGN. ESTO PQ findAllById() RECIBE UN ITERABLE, PERO EL PROFE LO DEJA CON LIST MEJOR. 
		return ResponseEntity.ok(service.findAllById(ids));
	}
	
	
	
	
}
