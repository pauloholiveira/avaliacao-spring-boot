package br.com.tokiomarine.seguradora.avaliacao.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.tokiomarine.seguradora.avaliacao.entidade.Estudante;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteIDInvalidoEX;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteNaoEncontradoEX;
import br.com.tokiomarine.seguradora.avaliacao.repository.EstudanteRepository;

@RunWith(MockitoJUnitRunner.class)
public class EstudanteServiceTest {

	@Mock
	EstudandeService estudantesService;

	@Mock
	EstudanteRepository estudanteRepository;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAtualizarEstudante() throws EstudanteNaoEncontradoEX {
		//cenario
		Estudante estudante1 = new Estudante(1L,"ESTUDANTE 1", "estudante1@gmail.com", "11999999999", "123456", "Curso 1");
		Estudante estudanteAlterado = new Estudante(1L,"ESTUDANTE 1111", "estudante1@gmail.com", "11999999999", "123456", "Curso 1");
		when(estudantesService.atualizarEstudante(estudante1)).thenReturn(estudanteAlterado);

		//Acao
		Estudante resultado = estudantesService.atualizarEstudante(estudante1);

		//Checagem
		error.checkThat(resultado.getEmail(), is(estudanteAlterado.getEmail()));
		error.checkThat(resultado.getNome(), is(estudanteAlterado.getNome()));


	}

	@Test
	public void testAtualizarEstudante_ESTUDANTEINEXISTENTE() throws EstudanteNaoEncontradoEX {
		//cenario
		expectedException.expect(EstudanteNaoEncontradoEX.class);
		expectedException.expectMessage("Estudante não encontrado");

		Estudante estudante1 = new Estudante(10L,"ESTUDANTE 1", "estudante1@gmail.com", "11999999999", "123456", "Curso 1");
		when(estudantesService.atualizarEstudante(estudante1)).thenThrow(new EstudanteNaoEncontradoEX("Estudante não encontrado"));

		//Acao
		estudantesService.atualizarEstudante(estudante1);

	}
	
	@Test
	public void testBuscarEstudantes() {
		//Cenario
		List<Estudante> estudantesList = Arrays.asList(
				new Estudante(1L,"ESTUDANTE 1", "estudante1@gmail.com", "11999999999", "123456", "Curso 1"),
				new Estudante(2L,"ESTUDANTE 2", "estudante2@gmail.com", "11999999999", "123457", "Curso 1"),
				new Estudante(3L,"ESTUDANTE 3", "estudante3@gmail.com", "11999999999", "123458", "Curso 3"),
				new Estudante(4L,"ESTUDANTE 4", "estudante4@gmail.com", "11999999999", "123459", "Curso 4")
				);
		when(estudantesService.buscarEstudantes()).thenReturn(estudantesList);

		//Acao
		List<Estudante> retorno = estudantesService.buscarEstudantes();

		//Verificacao
		verify(estudantesService).buscarEstudantes();
		assertThat(retorno.size(), is(4));
	}

	@Test
	public void testBuscarEstudantes_ListaVazia() {
		//Cenario
		List<Estudante> estudantesList = Collections.emptyList();
		when(estudantesService.buscarEstudantes()).thenReturn(estudantesList);

		//Acao
		List<Estudante> retorno = estudantesService.buscarEstudantes();

		//Verificacao
		verify(estudantesService).buscarEstudantes();
		error.checkThat(retorno.size(), is(0));
		error.checkThat(retorno.isEmpty(), is(true));
	}

	@Test
	public void testBuscarEstudante() throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX {
		//Cenario
		Estudante estudante = new Estudante(1L,"ESTUDANTE 1", "estudante1@gmail.com", "11999999999", "123456", "Curso 1");

		when(estudantesService.buscarEstudante(1L)).thenReturn(estudante);

		//Acao
		Estudante retorno = estudantesService.buscarEstudante(1L);

		//Verificacao
		verify(estudantesService).buscarEstudante(1L);
		assertThat(retorno, notNullValue());
	}

	@Test
	public void testBuscarEstudante_ID_INVALIDO() throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX  {
		//Cenario
		Long idUsuario = -10L;

		expectedException.expect(EstudanteIDInvalidoEX.class);
		expectedException.expectMessage("Estudante não encontrado");

		when(estudantesService.buscarEstudante(idUsuario)).thenThrow(new EstudanteIDInvalidoEX("Estudante não encontrado"));

		//Acao
		estudantesService.buscarEstudante(idUsuario);
	}

	@Test
	public void testBuscarEstudante_USUARIOINEXISTENTE() throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX  {
		//Cenario
		Long idUsuario = 10L;

		expectedException.expect(EstudanteNaoEncontradoEX.class);
		expectedException.expectMessage("Estudante ID: " + idUsuario + " não Encontrado.");

		when(estudantesService.buscarEstudante(idUsuario)).thenThrow(new EstudanteNaoEncontradoEX("Estudante ID: " + idUsuario + " não Encontrado."));

		//Acao
		estudantesService.buscarEstudante(idUsuario);
	}

}
