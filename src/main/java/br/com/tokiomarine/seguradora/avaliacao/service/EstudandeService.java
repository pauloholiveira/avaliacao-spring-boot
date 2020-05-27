package br.com.tokiomarine.seguradora.avaliacao.service;

import java.util.List;

import javax.validation.Valid;

import br.com.tokiomarine.seguradora.avaliacao.entidade.Estudante;

public interface EstudandeService {

	List<Estudante> buscarEstudantes();

	Estudante cadastrarEstudante(@Valid Estudante estudante);

	Estudante buscarEstudante(Long id);

	Estudante atualizarEstudante(@Valid Estudante estudante);
	
	void removerEstudante(Long id);
}
