package br.com.tokiomarine.seguradora.avaliacao.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
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
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.tokiomarine.seguradora.avaliacao.entidade.Estudante;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteIDInvalidoEX;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteJaCadastradoEX;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteNaoEncontradoEX;
import br.com.tokiomarine.seguradora.avaliacao.repository.EstudanteRepository;

@RunWith(SpringRunner.class)
public class EstudanteServiceTest {

	@TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
  
        @Bean
        public EstudandeService estudantesService() {
            return new EstudanteServiceImpl();
        }
    }
	
	@Autowired
    private EstudandeService estudantesService;
	
	@MockBean
    private EstudanteRepository estudanteRepository;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	 
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCadastrarEstudante() throws EstudanteJaCadastradoEX {
		//Cenario
		Long id = 6L;
		Estudante estudante = new Estudante(null,"ESTUDANTE 1", "estudante1@gmail.com", "11999999999", "123456", "Curso 1");
		Estudante estudanteSeraGravado = new Estudante(id,"ESTUDANTE 1", "estudante1@gmail.com", "11999999999", "123456", "Curso 1");

		when(estudanteRepository.save(estudante)).thenReturn(estudanteSeraGravado);

		//Acao
		Estudante estudanteGravado = estudantesService.cadastrarEstudante(estudante);

		//Verificao
		verify(estudanteRepository).save(estudante);
		assertThat(estudanteGravado, is(estudanteSeraGravado));
	}

	@Test
	public void testCadastrarEstudante_JAEXISTE() throws EstudanteJaCadastradoEX {
		//Cenario
		Long id = 1L;
		Estudante estudante = new Estudante(null,"ESTUDANTE 1", "estudante1@gmail.com", "11999999999", "123456", "Curso 1");
		List<Estudante> estudantesJaExistentes = Collections.singletonList(new Estudante(id,"ESTUDANTE 1", "estudante1@gmail.com", "11999999999", "123456", "Curso 1"));

		when(estudanteRepository.findByEmailOrMatricula(estudante.getEmail(), estudante.getMatricula())).thenReturn(estudantesJaExistentes);
		expectedException.expect(EstudanteJaCadastradoEX.class);
		expectedException.expectMessage("Estudante já está cadastrado.");

		//Acao
		estudantesService.cadastrarEstudante(estudante);		

		//Verificao

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
		when(estudanteRepository.findAll()).thenReturn(estudantesList);

		//Acao
		List<Estudante> retorno = estudantesService.buscarEstudantes();

		//Verificacao
		verify(estudanteRepository).findAll();
		assertThat(retorno.size(), is(4));
	}

	@Test
	public void testBuscarEstudantes_ListaVazia() {
		//Cenario
		List<Estudante> estudantesList = Collections.emptyList();
		when(estudanteRepository.findAll()).thenReturn(estudantesList);

		//Acao
		List<Estudante> retorno = estudantesService.buscarEstudantes();

		//Verificacao
		verify(estudanteRepository).findAll();
		error.checkThat(retorno.size(), is(0));
		error.checkThat(retorno.isEmpty(), is(true));
	}


	@Test
	public void testBuscarEstudante() throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX {
		//Cenario
		Estudante estudante = new Estudante(1L,"ESTUDANTE 1", "estudante1@gmail.com", "11999999999", "123456", "Curso 1");

		when(estudanteRepository.findById(1L)).thenReturn(Optional.of(estudante));

		//Acao
		Estudante retorno = estudantesService.buscarEstudante(1L);

		//Verificacao
		verify(estudanteRepository).findById(1L);
		assertThat(retorno, notNullValue());
	}

	@Test
	public void testBuscarEstudante_ID_INVALIDO() throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX  {
		//Cenario
		Long idUsuario = -10L;

		expectedException.expect(EstudanteIDInvalidoEX.class);
		expectedException.expectMessage("Identificador inválido: " + idUsuario);

		//Acao
		estudantesService.buscarEstudante(idUsuario);
	}

	@Test
	public void testBuscarEstudante_ID_NULL() throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX  {
		//Cenario
		Long idUsuario = null;

		expectedException.expect(EstudanteIDInvalidoEX.class);
		expectedException.expectMessage("Identificador inválido: " + idUsuario);

		//Acao
		estudantesService.buscarEstudante(idUsuario);
	}

	@Test
	public void testBuscarEstudante_USUARIOINEXISTENTE() throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX  {
		//Cenario
		Long idUsuario = 10L;

		expectedException.expect(EstudanteNaoEncontradoEX.class);
		expectedException.expectMessage("Estudante ID: " + idUsuario + " não Encontrado.");

		when(estudanteRepository.findById(idUsuario)).thenReturn(Optional.empty());

		//Acao
		estudantesService.buscarEstudante(idUsuario);

		//Verificacao
		verify(estudanteRepository).findById(idUsuario);
	}


	@Test
	public void testAtualizarEstudante() throws EstudanteNaoEncontradoEX {
		//cenario
		Estudante estudante1 = new Estudante(1L,"ESTUDANTE 1", "estudante1@gmail.com", "11999999999", "123456", "Curso 1");
		Estudante estudanteAlterado = new Estudante(1L,"ESTUDANTE 1111", "estudante1@gmail.com", "11999999999", "123456", "Curso 1");
		when(estudanteRepository.findById(estudanteAlterado.getId())).thenReturn(Optional.of(estudante1));
		when(estudanteRepository.save(estudanteAlterado)).thenReturn(estudanteAlterado);


		//Acao
		Estudante resultado = estudantesService.atualizarEstudante(estudanteAlterado);

		//Checagem
		verify(estudanteRepository).findById(estudanteAlterado.getId());
		verify(estudanteRepository).save(estudanteAlterado);
		error.checkThat(resultado.getEmail(), is(estudanteAlterado.getEmail()));
		error.checkThat(resultado.getNome(), is(estudanteAlterado.getNome()));


	}

	@Test
	public void testAtualizarEstudante_ESTUDANTEINEXISTENTE() throws EstudanteNaoEncontradoEX {
		//cenario
		Estudante estudante1 = new Estudante(10L,"ESTUDANTE 1", "estudante1@gmail.com", "11999999999", "123456", "Curso 1");
		when(estudanteRepository.findById(estudante1.getId())).thenReturn(Optional.empty());
		expectedException.expect(EstudanteNaoEncontradoEX.class);
		expectedException.expectMessage("Estudante não encontrado");

		//Acao
		estudantesService.atualizarEstudante(estudante1);

		//Checagem
		verify(estudanteRepository).findById(estudante1.getId());
	}


	@Test
	public void testRemoverEstudante() throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX {
		//cenario
		Estudante estudante1 = new Estudante(1L,"ESTUDANTE 1", "estudante1@gmail.com", "11999999999", "123456", "Curso 1");
		when(estudanteRepository.findById(1L)).thenReturn(Optional.of(estudante1));

		//Acao
		estudantesService.removerEstudante(1L);

		//verificacao
		verify(estudanteRepository, times(1)).deleteById(1L);
		ArgumentCaptor<Long> argCapture = ArgumentCaptor.forClass(Long.class);
		verify(estudanteRepository).deleteById(argCapture.capture());
		Long id = argCapture.getValue();
		error.checkThat(id, is(1L));
	}

	@Test
	public void testremoverEstudante_ESTUDANTENAOEXISTE() throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX {
		//cenario
		Long id= 10L;

		when(estudanteRepository.findById(id)).thenReturn(Optional.empty());
		expectedException.expect(EstudanteNaoEncontradoEX.class);
		expectedException.expectMessage("Estudante ID: " + id + " não Encontrado.");

		//Acao
		estudantesService.removerEstudante(id);

		//verificacao
		verify(estudanteRepository, times(1)).findById(id);
	}

	@Test
	public void testremoverEstudante_IDNULL() throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX {
		//Cenario
		Long id = null;
		expectedException.expect(EstudanteIDInvalidoEX.class);
		expectedException.expectMessage("Identificador inválido: " + id);

		//Acao
		estudantesService.removerEstudante(id);
	}

	@Test
	public void testremoverEstudante_IDINVALIDO() throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX {
		//Cenario
		Long id = -10L;
		expectedException.expect(EstudanteIDInvalidoEX.class);
		expectedException.expectMessage("Identificador inválido: " + id);

		//Acao
		estudantesService.removerEstudante(id);
	}

}
