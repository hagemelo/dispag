package br.com.jhage.dispag.novoorcamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jhage.dispag.core.modelo.Usuario;

/***
 * 
 * @author Alexsander Melo
 * @since 16/05/2021
 *
 */


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	@Query("select u " +
			   "from Usuario u "+
			   "where u.login like :valor or u.nome like :valor ")
	public Usuario loadUsuarioBy(@Param("valor") String valor);
}
