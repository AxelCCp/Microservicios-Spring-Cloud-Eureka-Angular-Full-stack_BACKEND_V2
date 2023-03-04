package microservice.usuarios.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import microservice.usuarios.models.entity.Alumno;
import microservice.usuarios.models.service.IAlumnoService;

@RestController
public class AlumnoController {

	@GetMapping
	public ResponseEntity<?>listar(){
		return ResponseEntity.ok().body(alumnoService.findAll());
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?>ver(@PathVariable Long id){
		Optional<Alumno> o = alumnoService.porId(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(o.get());
	}
	
	
	@PostMapping
	public ResponseEntity<?>crear(@RequestBody Alumno alumno){
		Alumno alumnodb = alumnoService.save(alumno);
		return ResponseEntity.status(HttpStatus.CREATED).body(alumnodb);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?>editar(@RequestBody Alumno alumno, @PathVariable Long id){
		Optional<Alumno> o = alumnoService.porId(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Alumno alumnodb = o.get();
		alumnodb.setNombre(alumno.getNombre());
		alumnodb.setApellido(alumno.getApellido());
		alumnodb.setEmail(alumno.getEmail());
		return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.save(alumnodb));
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?>eliminar(@PathVariable Long id){
		alumnoService.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}
	
	
	@Autowired
	private IAlumnoService alumnoService;	
}
