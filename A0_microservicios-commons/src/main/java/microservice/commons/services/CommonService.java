package microservice.commons.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

//R : ES EL NOMBRE DE TIPO Y QUE HEREDA DE CrudRepository.   
// R extends CrudRepository<E,Long> : SE ESTÁ DICIENDO QUE EL SEGUNDO GENERICO ES DE CUALQUIER TIPO QUE EXTIENDA DE CrudRepository.
//SE QUITA EL @SERVICE, YA Q NO SE VA A INYECTAR, SINO HEREDAR.

//46 : SE CAMBIA CrudRepository POR PagingAndSortingRepository PARA USAR PAGINACIÓN.
public class CommonService<E, R extends PagingAndSortingRepository<E,Long>>  implements ICommonService<E>{

	@Override
	@Transactional(readOnly=true)
	public Iterable<E> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Optional<E> porId(Long Id) {
		// TODO Auto-generated method stub
		return repository.findById(Id);
	}

	@Override
	@Transactional
	public E save(E entity) {
		// TODO Auto-generated method stub
		return repository.save(entity);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<E> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findAll(pageable);
	}
	
	
	@Autowired
	protected R repository;			//PROTECTED PARA Q SE PUEDE USAR EN LAS CLASES HIJAS.


	
	
}
