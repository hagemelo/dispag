package br.com.jhage.dispag.debito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jhage.dispag.debito.modelo.Debito;

/***
 * 
 * @author Alexsander Melo
 * @since 05/02/2021
 *
 */

@Repository
public interface DebitoRepository extends JpaRepository<Debito, Long>{
	
	
}
