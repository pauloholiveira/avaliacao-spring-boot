package br.com.tokiomarine.seguradora.avaliacao.service;

import java.util.List;

import javax.validation.Valid;

import br.com.tokiomarine.seguradora.avaliacao.entidade.Estudante;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteIDInvalidoEX;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteJaCadastradoEX;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteNaoEncontradoEX;

public interface EstudandeService {

	List<Estudante> buscarEstudantes();

	Estudante cadastrarEstudante(@Valid Estudante estudante) throws EstudanteJaCadastradoEX;

	Estudante buscarEstudante(Long id) throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX;

	Estudante atualizarEstudante(@Valid Estudante estudante) throws EstudanteNaoEncontradoEX;
	
	void removerEstudante(Long id) throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX;
}
