package br.com.jhage.dispag.cadastrarcredor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jhage.dispag.core.modelo.Credor;

/***
 * 
 * @author Alexsander Melo
 * @since 05/02/2021
 *
 */

@Repository
public interface CredorRepository extends JpaRepository<Credor, Long>{

	
	
	@Query("select c " +
			   "from Credor c "+
			   "where c.descricao like :descricao" )
	public Credor loadCredorByDescricao(@Param("descricao") String descricao);
	
}
