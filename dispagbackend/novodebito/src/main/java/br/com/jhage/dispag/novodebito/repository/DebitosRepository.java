package br.com.jhage.dispag.novodebito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.jhage.dispag.core.modelo.Debitos;

/***
 * 
 * @author Alexsander Melo
 * @since 05/02/2021
 *
 */

@Repository
public interface DebitosRepository extends JpaRepository<Debitos, Long>{
	
	
}
