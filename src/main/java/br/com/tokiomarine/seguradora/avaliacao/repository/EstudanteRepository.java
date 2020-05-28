package br.com.tokiomarine.seguradora.avaliacao.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.tokiomarine.seguradora.avaliacao.entidade.Estudante;

@Repository
public interface EstudanteRepository extends CrudRepository<Estudante, Long>  {

	List<Estudante> findByNome(String nome);
	Estudante findByEmail(String email);
	Estudante findByMatricula(String matricula);
	
	Estudante findByNomeAndEmail(String nome,String email);
	Estudante findByNomeAndEmailAndMatricula(String nome, String email, String matricula);
	List<Estudante> findByEmailOrMatricula(String email, String matricula);
}
