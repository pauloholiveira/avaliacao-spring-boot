package br.com.tokiomarine.seguradora.avaliacao.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.avaliacao.entidade.Estudante;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteIDInvalidoEX;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteJaCadastradoEX;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteNaoEncontradoEX;
import br.com.tokiomarine.seguradora.avaliacao.repository.EstudanteRepository;

@Service
public class EstudanteServiceImpl implements EstudandeService {

	@Autowired
	private EstudanteRepository repository;

	@Override
	public Estudante cadastrarEstudante(@Valid Estudante estudante) throws EstudanteJaCadastradoEX {
		List<Estudante> jaExisteEmailMatricula = repository.findByEmailOrMatricula(estudante.getEmail(), estudante.getMatricula());
		
		if(jaExisteEmailMatricula != null && !jaExisteEmailMatricula.isEmpty()) {
			throw new EstudanteJaCadastradoEX("Estudante já está cadastrado.");
		}
		
		return repository.save(estudante);
	}

	@Override
	public Estudante atualizarEstudante(@Valid Estudante estudante) throws EstudanteNaoEncontradoEX	 {
		if(!repository.findById(estudante.getId()).isPresent()) {
			throw new EstudanteNaoEncontradoEX("Estudando não encontrado");
		}
		
		return repository.save(estudante);
	}

	@Override
	public List<Estudante> buscarEstudantes() {
		return (List<Estudante>)repository.findAll();
	}

	@Override
	public Estudante buscarEstudante(Long id) throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX {
		if(id == null || id <=0) 	throw new EstudanteIDInvalidoEX("Identificador inválido: " + id);
		
		Optional<Estudante> encontrado = repository.findById(id);
		if(!encontrado.isPresent()) {
			throw new EstudanteNaoEncontradoEX("Estudante ID: " + id + " não Encontrado.");
		}
		return encontrado.get();
	}

	@Override
	public void removerEstudante(Long id) throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX {
		if(id == null || id <=0) 	throw new IllegalArgumentException("Identificador inválido: " + id);
		
		buscarEstudante(id);
		
		repository.deleteById(id);
	}

}
