package br.com.tokiomarine.seguradora.avaliacao.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tokiomarine.seguradora.avaliacao.entidade.Estudante;
import br.com.tokiomarine.seguradora.avaliacao.repository.EstudanteRepository;

// TODO Efetue a implementação dos métodos da classe service
@Service
public class EstudanteServiceImpl implements EstudandeService {

	@Autowired
	EstudanteRepository repository;

	@Override
	public Estudante cadastrarEstudante(@Valid Estudante estudante) {
		return repository.save(estudante);
	}

	@Override
	public Estudante atualizarEstudante(@Valid Estudante estudante) {
		return repository.save(estudante);
	}

	@Override
	public List<Estudante> buscarEstudantes() {
		return (List<Estudante>)repository.findAll();
	}

	@Override
	public Estudante buscarEstudante(Long id) {
		if(id == null || id <=0) 	throw new IllegalArgumentException("Identificador inválido:" + id);
		
		Optional<Estudante> encontrado = repository.findById(id);
		if(!encontrado.isPresent()) {
			throw new IllegalArgumentException("Identificador inválido:" + id);
		}
		return encontrado.get();
	}

	@Override
	public void removerEstudante(Long id) {
		if(id == null || id <=0) 	throw new IllegalArgumentException("Identificador inválido:" + id);
		
		repository.deleteById(id);
	}

}
