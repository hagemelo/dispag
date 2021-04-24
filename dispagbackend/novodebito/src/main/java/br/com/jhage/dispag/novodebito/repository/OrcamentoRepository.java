package br.com.jhage.dispag.novodebito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jhage.dispag.core.constante.Mes;
import br.com.jhage.dispag.core.modelo.Orcamento;


/***
 * 
 * @author Alexsander Melo
 * @since 11/04/2021
 *
 */


@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long>{

	
	@Query("select o " +
			   "from Orcamento o "+
			   "where o.ano = :ano and o.mes like :mes "
			   + "and o.estado = br.com.jhage.dispag.core.constante.Estado.APROVADO" )
	public Orcamento loadOrcamentoBy(@Param("ano") int ano, @Param("mes") Mes mes);
	
}
