package br.com.tokiomarine.seguradora.avaliacao.controller.dto;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.com.tokiomarine.seguradora.avaliacao.entidade.Estudante;

public class EstudanteDTOTest{
	
	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Test
	public void constructorTest() {
		//Cenario
		Estudante estudante = null;
		
		//Ação
		EstudanteDTO estudanteDTO = new EstudanteDTO(estudante);
		
		//Verificao
		error.checkThat(estudanteDTO.getNome(), is(""));
		error.checkThat(estudanteDTO.getEmail(), is(""));
		error.checkThat(estudanteDTO.getTelefone(), nullValue());
		error.checkThat(estudanteDTO.getMatricula(), is(""));
		error.checkThat(estudanteDTO.getCurso(), nullValue());
		error.checkThat(estudanteDTO.getId(), nullValue());
	}

	@Test
	public void convertTest() {
		//cenario
		List<Estudante> estudantes = asList(
				new Estudante(1L,"Estudante 1","estudante1@sistema.com.br", "1199999999", "12340","Curso 1"),
				new Estudante(1L,"Estudante 2","estudante2@sistema.com.br", "1199999999", "12341","Curso 2"),
				new Estudante(1L,"Estudante 3","estudante3@sistema.com.br", "1199999999", "12342","Curso 3")
				);
		
		//Ação
		List<EstudanteDTO> estudantesDTO = EstudanteDTO.convert(estudantes);
		
		//Verificacao
		error.checkThat(estudantesDTO.size(), is(3));
		error.checkThat(estudantesDTO, notNullValue());		
	}

	@Test
	public void convertTest_ListVazia() {
		//cenario
		List<Estudante> estudantes = Collections.emptyList();
		
		//Ação
		List<EstudanteDTO> estudantesDTO = EstudanteDTO.convert(estudantes);
		
		//Verificacao
		error.checkThat(estudantesDTO.size(), is(0));
		error.checkThat(estudantesDTO, notNullValue());		
	}
	
	@Test
	public void toEstudanteTest() {
		//Cenario
		EstudanteDTO estudanteDTO = new EstudanteDTO(1L,"Estudante 1","estudante1@sistema.com.br", "1199999999", "12340","Curso 1");
		
		//Ação
		Estudante estudante = estudanteDTO.toEstudante();
		
		//Verificacao
		error.checkThat(estudante.getNome(), equalTo(estudanteDTO.getNome()));
		error.checkThat(estudante.getEmail(), equalTo(estudanteDTO.getEmail()));
		error.checkThat(estudante.getTelefone(), equalTo(estudanteDTO.getTelefone()));
		error.checkThat(estudante.getMatricula(), equalTo(estudanteDTO.getMatricula()));
		error.checkThat(estudante.getCurso(), equalTo(estudanteDTO.getCurso()));
		error.checkThat(estudante.getId(), equalTo(estudanteDTO.getIdEstudante()));
	}
}