package microservice.commons.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import microservice.commons.services.ICommonService;
import javax.validation.Valid;



//SE QUITA REST CONTROLLER YA Q VA A SER UNA CLASE GENERICA QUE SE VA A HEREDAR.
//<E,S> : E DE ENTITY Y S DE SERVICE. S DE SERVICE HEREDA DE COMMONSERVICE y COMMONSSERVICE A SU VEZ TIENE LA <E> Q REPRESENTA AL ENTITY. 

public class CommonController<E,S extends ICommonService<E>> {

	@GetMapping
	public ResponseEntity<?>listar(){
		return ResponseEntity.ok().body(service.findAll());
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?>ver(@PathVariable Long id){
		Optional<E> o = service.porId(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(o.get());
	}
	
	
	@PostMapping
	public ResponseEntity<?>crear(@Valid @RequestBody E entity, BindingResult result){
		if(result.hasErrors()) {
			return this.validar(result);
		}
		E entitydb = service.save(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(entitydb);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?>eliminar(@PathVariable Long id){
		service.deleteById(id);
		return ResponseEntity.noContent().build();	
	}
	
	protected ResponseEntity<?>validar(BindingResult result){
		Map<String,Object>errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}
	
	@GetMapping("/pagina")
	public ResponseEntity<?>listar(Pageable pageable){
		return ResponseEntity.ok().body(service.findAll(pageable));
	}
	
	
	
	@Autowired
	protected S service;	//SE LE PONE LA S DE SERVICE.	//SE PONE PROTECTED PARA PODER REUTILIZAR EL SERVICE EN EL CONTROLADOR Q SE VAYA A IMPLEMENTAR.
}
