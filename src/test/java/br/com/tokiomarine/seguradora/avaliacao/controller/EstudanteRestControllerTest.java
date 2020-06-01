package br.com.tokiomarine.seguradora.avaliacao.controller;


import br.com.tokiomarine.seguradora.avaliacao.controller.dto.EstudanteDTO;
import br.com.tokiomarine.seguradora.avaliacao.entidade.Estudante;
import br.com.tokiomarine.seguradora.avaliacao.service.EstudandeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EstudanteRestController.class)
public class EstudanteRestControllerTest {
	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private EstudandeService estudantesService;
	
	@Test
	public void whenGetEstudantesthenReturnHALJsonArray()
	  throws Exception {
	     
		List<Estudante> estudantesList = Arrays.asList(
				new Estudante(1L,"ESTUDANTE 1", "estudante1@gmail.com", "11999999999", "123456", "Curso 1"),
				new Estudante(2L,"ESTUDANTE 2", "estudante2@gmail.com", "11999999999", "123457", "Curso 1"),
				new Estudante(3L,"ESTUDANTE 3", "estudante3@gmail.com", "11999999999", "123458", "Curso 3"),
				new Estudante(4L,"ESTUDANTE 4", "estudante4@gmail.com", "11999999999", "123459", "Curso 4")
				);
		List<EstudanteDTO> estudantesDTOList = EstudanteDTO.convert(estudantesList);
		
	    when(estudantesService.buscarEstudantes()).thenReturn(estudantesList);
	    
	    mvc.perform(get("/api/estudantes")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$._embedded").exists())
	      .andExpect(jsonPath("$._embedded.estudanteDTOList").exists())
	      .andExpect(jsonPath("$._embedded.estudanteDTOList").isArray())
	      .andExpect(jsonPath("$._embedded.estudanteDTOList", hasSize(4)))
	      .andExpect(jsonPath("$._links.self.href", containsString("/api/estudantes"))).andReturn()
	      ;


	}
}
