package br.com.jhage.dispag.pagardebito.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import br.com.jhage.dispag.core.modelo.Debitos;


/***
 * 
 * @author Alexsander Melo
 * @since 11/04/2021
 *
 */
@Repository
public class DebitosRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	private final int LIMIT; 
	
	
	public DebitosRepository(@Value("${set.max.results.limit}") Integer setMaxResultsLimit) {
		
		LIMIT = setMaxResultsLimit;
	}
	
	
	private static final String QUERYLOADDEBITOS = "select d " +
												   "from Debitos d "+
												   "where d.marcacao like :marcacao "+
												   "and d.status = br.com.jhage.dispag.core.constante.Status.AVENCER "+
												   "and d.estado = br.com.jhage.dispag.core.constante.Estado.PENDENTE "+
												   "and to_char(d.vencimento, 'dd/MM/yyyy') like :vencimento";

	public Debitos loadCredorByDescricao(String marcacao, String vencimento) {
				
		return (Debitos) entityManager.createQuery(QUERYLOADDEBITOS, Debitos.class)
				.setParameter("marcacao", marcacao)
				.setParameter("vencimento", vencimento)
				.setMaxResults(LIMIT).getSingleResult();
	}
	
	
	public void save(Debitos debitos) {
		
		entityManager.merge(debitos);
	}
}
