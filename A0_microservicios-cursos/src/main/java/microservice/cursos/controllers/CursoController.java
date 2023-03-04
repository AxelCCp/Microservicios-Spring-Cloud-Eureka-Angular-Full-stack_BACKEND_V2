package microservice.cursos.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import microservice.alumnos.commons.models.entity.Alumno;
import microservice.commons.controllers.CommonController;
import microservice.commons.examenes.model.entity.Examen;
import microservice.cursos.models.entity.Curso;
import microservice.cursos.models.entity.CursoAlumno;
import microservice.cursos.models.service.ICursoService;

@RestController
public class CursoController extends CommonController<Curso,ICursoService>{
	
	
	//75 : SE COPIA Y PEGA EL METODO DESDE EL COMMON CONTROLLER Y AQUÍ SE SOBRE ESCRIBE. MANUALMENTE SE LE PONE EL @OVERRRIDE
	@Override
	@GetMapping
	public ResponseEntity<?>listar(){
		//APARTIR DEL FINDALL(), HAY QYE CONVERTIR LOS DATOS CON UN MAP(), PARA PASAR LOS ALUMNOS POR CADA CURSOALUMNO DEL CURSO, CREAR UNA ISNTANCIA DE ALUMNO Y SE LO PASAMOS CON EL ID AL CURSO, POR CADA UNO.
		//                  (                              ) : ESTOS PARENTESIS ADICIONALES SON PQ FINDALL() DEVUELVE UN ITERABLE . Y EL API STREAM NO FUNCIONA CON ITERABLE , PERO SI CON LIST.
		//ESTO SE HACE PARA EL FRONT, PARA TRABAJAR CON OBJS ALUMNO EN VEZ DE OBJS CURSOALUMNO
		List<Curso>cursos = ((List<Curso>)service.findAll()).stream().map(c -> {
			c.getCursoAlumnos().forEach(ca -> {
				Alumno alumno = new Alumno();
				//ESTO ENTREGARA EN EL JSON CON TODOS LOS CURSOS Y SOLO EL ID DEL ALUMNO, MIENTRAS QUE LOS OTROS ATRIBUTOS DEL OBJ APARECERÁN NULL. ESTO ES PQ NO ES NECESARIO OBTENER EN EL FRONT LA LISTA CON TODO EL DETALLE, EN LA PÁGINA INICIAL DONDE APARECEN TODOS LOS CURSOS, SI SE MOSTRARÁ TODA LA INFO EN PÁGINA DE DETALLE DEL CURSO. SI SE PONEN TODOS LOS DATOS DEL ALUMNO, Y SI CADA CURSO TIENE 20 ALUMNOS, EL MICROSERVICIO CURSO HARÁ 20 PETICIONES FEIGN POR CADA CURSO AL MS-USUARIOS, Y ESTO TIENE SU COSTO EN USO DE RECURSOS.
				alumno.setId(ca.getAlumnoId());
				c.addAlumnos(alumno);
			});
			return c;
		}).collect(Collectors.toList());
		return ResponseEntity.ok().body(cursos);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?>editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Curso>o = this.service.porId(id);
		if(!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursodb = o.get();
		cursodb.setNombre(curso.getNombre());
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursodb));
	}
	
	
	//DOS METODOS DEL TIPO  PUT QUE SON PARA MODIFICAR EL CURSO. PARA ASIGNAR ALUMNOS Y ELIMINAR ALUMNOS.
	
	//RECIBE POR EL CUERPO DEL REQUEST, UN ARREGLO DE ALUMNOS.
	@PutMapping("/{id}/asignar-alumnos")
	public ResponseEntity<?>asignarAlumno(@RequestBody List<Alumno>alumnos, @PathVariable Long id){
		Optional<Curso>o = this.service.porId(id);
		if(!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursodb = o.get();
		alumnos.forEach(a -> {
			CursoAlumno cursoAlumno = new CursoAlumno();
			cursoAlumno.setAlumnoId(a.getId());
			cursoAlumno.setCurso(cursodb);
			cursodb.addCursoAlumno(cursoAlumno);
		});
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursodb));
	}
	
	@PutMapping("/{id}/eliminar-alumno")
	public ResponseEntity<?>eliminarAlumnos(@RequestBody Alumno alumno, @PathVariable Long id){
		Optional<Curso>o = this.service.porId(id);
		if(!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursodb = o.get();
		CursoAlumno cursoAlumno = new CursoAlumno();
		cursoAlumno.setAlumnoId(alumno.getId());
		cursodb.removeCursoAlumno(cursoAlumno);
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursodb));
	}
	
	
	//DOS METODOS DEL TIPO  PUT QUE SON PARA MODIFICAR EL CURSO SEGÚN LOS EXAMENES Q TIENE. PARA ASIGNAR EXAMENES Y ELIMINAR EXAMENES AL CURSO.
	
	@PutMapping("/{id}/asignar-examenes")
	public ResponseEntity<?>asignarExamenes(@RequestBody List<Examen>examenes, @PathVariable Long id){
		Optional<Curso>o = this.service.porId(id);
		if(!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursodb = o.get();
		examenes.forEach(a -> {
			cursodb.addExamen(a);
		});
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursodb));
	}
	
	@PutMapping("/{id}/eliminar-examen")
	public ResponseEntity<?>eliminarExamen(@RequestBody Examen examen, @PathVariable Long id){
		Optional<Curso>o = this.service.porId(id);
		if(!o.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso cursodb = o.get();
		cursodb.removeExamen(examen);
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(cursodb));
	}
	
	
	
	//ENCUENTRA UN CURSO SEGÚN EL ID DEL ALUMNO.
	@GetMapping("/alumno/{id}")
	public ResponseEntity<?>buscarPorAlumnoId(@PathVariable long id){
		Curso curso = service.findCursoByAlumnoId(id);
		
		
		if(curso != null) {
			//OBTIENE LOS EXAMENES RESPONDIDOS POR EL ALUMNO
			List<Long>examenesIds = (List<Long>)service.obtenerExamenesConRespuestasPorAlumno(id);
			
			//95 --- SE ENVUELVE EL CODIGO EN UN IF
			if(examenesIds != null && examenesIds.size() > 0) {
				
		
				//SE OBTIENEN TODOS LOS EXAMENES Y SE SETTEAN EN TRUE LOS EXAMENES RESPONDIDOS
				List<Examen>examenes = curso.getExamenes().stream().map(examen -> {
					if(examenesIds.contains(examen.getId())) {
						examen.setRespondido(true);
					}
					return examen;
				}).collect(Collectors.toList());
				//ACTUALIZA LA LISTA
				curso.setExamenes(examenes);
			
			}
			//95
		}
		return ResponseEntity.ok(curso);
	}
	
	//SE COPIO Y PEGO ESTE MÉTODO DESDE EL COMMON CONTROLLER Y AQUÍ SE SOBREESCRIBE.
	@Override
	@GetMapping("/{id}")
	public ResponseEntity<?>ver(@PathVariable Long id){
		Optional<Curso> o = service.porId(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Curso curso = o.get();
		if(curso.getCursoAlumnos().isEmpty() == false) {
			List<Long> ids = curso.getCursoAlumnos().stream().map(ca -> {
				return ca.getAlumnoId();
			}).collect(Collectors.toList());
			List<Alumno>alumno = (List<Alumno>) service.obtenerAlumnosPorCurso(ids);
			curso.setAlumnos(alumno);	//LA IDEA DE ESTO ES OBTENER UN LISTADO DE LOS ALUMNOS POR CURSO, CON TODA LA INFORMACION DE CADA ALUMNOS.
		}
		return ResponseEntity.ok().body(curso);
	}
	
	
	//SE COPIO Y PEGO ESTE MÉTODO DESDE EL COMMON CONTROLLER Y AQUÍ SE SOBREESCRIBE.
	@Override
	@GetMapping("/pagina")
	public ResponseEntity<?>listar(Pageable pageable){
		//PAGE YA VIENE INTEGRADO CON EL API STREAM DE JAVA8
		Page<Curso> cursos = service.findAll(pageable).map(curso -> {
			curso.getCursoAlumnos().forEach(ca -> {
				Alumno alumno = new Alumno();
				alumno.setId(ca.getAlumnoId());
				curso.addAlumnos(alumno);
			});
			return curso;
		});
		return ResponseEntity.ok().body(cursos);
	}
	
	
	//81 : MÉTODO PERSONALIZADO : SI EN MS-USUARIOS SE ELIMINA UN ALUMNO (POSTGRES), TAMBN HAY QUE ELIMINAR EN LA TABLA CURSOALUMNOS EL ID DEL ALUMNO.
	@DeleteMapping("/eliminar-alumno-curso/{id}")
	public ResponseEntity<?>eliminarCursoAlumnoPorId(@PathVariable Long id){
		service.eliminarCursoAlumnoPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	
		
	
}
