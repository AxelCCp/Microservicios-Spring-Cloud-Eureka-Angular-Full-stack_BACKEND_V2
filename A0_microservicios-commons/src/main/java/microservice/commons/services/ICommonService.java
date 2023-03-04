package microservice.commons.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface ICommonService <E>{

	
	public Iterable<E>findAll();
	public Optional<E>porId(Long Id);
	public E save(E entity);
	public void deleteById(Long id);
	
	public Page<E>findAll(Pageable pageable);
	
	
	
}
