package microservice.examenes.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import microservice.commons.controllers.CommonController;
import microservice.commons.examenes.model.entity.Examen;
import microservice.commons.examenes.model.entity.Pregunta;
import microservice.examenes.models.service.IExamenService;

@RestController
public class ExamenController extends CommonController<Examen, IExamenService>{

	@PutMapping("/{id}")
	public ResponseEntity<?>editar(@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id ){
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Examen> o = service.porId(id);
		if(!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Examen examendb = o.get();
		examendb.setNombre(examen.getNombre());
		
		//VER QUE PREGUNTAS EXISTEN EN LA BBDD, PERO NO ESTÁN EN EL JSON QUE SE ESTÁ ENVIANDO.  ESTO ES PQ SEGURAMENTE ANTES AGREGARON Y BORRARON PREGUNTAS, Y HAT Q REFLEJAR ESE CAMBIO.
		//ESTO ES TAMBN PARA ELIMINAR LAS PREGUNTAS DE LA BBDD QUE NO ESTÁN EN EL JSON.
		
		//1.-CREAR ARRAY DE LAS PREGUNTAS ELIMINADAS. SE ITERAN LAS PREGUNTAS DE LA BBDD Y SE PREGUNTA SI ESTA PREGUNTA EXISTE EN EÑ JSON QUE SE RECIBE DEL @REQUESBODY. y SI NO EXISTE LA PREGUNTA EN EL JSON, SE ELIMINA DE LA BBDDD.
		List<Pregunta>eliminadas = new ArrayList<>();
		examendb.getPreguntas().forEach(pdb -> {
			//EL CONTAINS() USA EL EQUALS DE LA CLASE PREGUNTA.
			if(!examen.getPreguntas().contains(pdb)) {
				eliminadas.add(pdb);
			}
		});
		
		//2.-POR CADA PREGUNTA A ELIMINAR,  LA ELIMINAMOS.
		eliminadas.forEach(p -> {
			examendb.removePregunta(p);
		});
		
		//3.-UNA VEZ ELIMINADAS LAS PREGUNTAS DE LA BBDD Q NO ESTABAN EN EL JSON, AHORA HAY Q AGREGAR LAS NUEVAS PREGUNTAS Y MODIFICAR LAS EXISTENTES. Y LAS PREGUNTAS QUE NO SE TOCARON, QUEDAN TAL CUAL.
		examendb.setPreguntas(examen.getPreguntas());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examendb));
	}
	
	
	
	//METODO PERSONALIZADO PARA BUSCAR UN EXAMEN POR EL NOMBRE DEL EXAMEN.
	@GetMapping("/filtrar/{texto}")
	public ResponseEntity<?>filtrar(@PathVariable String texto){
		return ResponseEntity.ok(service.findByNombre(texto));
	}
	
	
	//DEVULVE LA LISTA DE ASIGNATURAS
	@GetMapping("/asignaturas")
	public ResponseEntity<?>listarAsignaturas(){
		return ResponseEntity.ok(service.findAllAsignaturas());
	}
	
	//97
	@GetMapping("/respondidos-por-preguntas")																	//CON REQUESTPARAM, MEJOR USAR LIST Q ITERABLE. ITERABLE DA ERROR CON REQUESTPARAM.
	public ResponseEntity<?>obtenerExamenesIdsPorPreguntasIdRespondidas(@RequestParam List<Long>preguntaIds){
		return ResponseEntity.ok().body(service.findExamenesIdsConRespuestasByPreguntaIds(preguntaIds));
	}
	
	
}
