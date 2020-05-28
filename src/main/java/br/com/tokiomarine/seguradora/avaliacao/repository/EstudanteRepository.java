package br.com.tokiomarine.seguradora.avaliacao.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.avaliacao.entidade.Estudante;

@Repository
public interface EstudanteRepository extends CrudRepository<Estudante, Long>  {

	List<Estudante> findByNome(String nome);

}
