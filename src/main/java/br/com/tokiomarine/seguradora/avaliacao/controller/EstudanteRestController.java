package br.com.tokiomarine.seguradora.avaliacao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.tokiomarine.seguradora.avaliacao.controller.dto.EstudanteDTO;
import br.com.tokiomarine.seguradora.avaliacao.entidade.Estudante;
import br.com.tokiomarine.seguradora.avaliacao.service.EstudandeService;

// TODO não esquecer de usar as anotações para criação do restcontroller
@RestController
@RequestMapping("/api/estudantes")
public class EstudanteRestController {

	// TODO caso você não conheça THEMELEAF faça a implementação dos métodos em forma de RESTCONTROLLER (seguindo o padrão RESTFUL)
	@Autowired 
	private EstudandeService estudanteService;
	
	// TODO IMPLEMENTAR A LISTAGEM DE ESTUDANTES (GET)
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<EstudanteDTO>> listarEstudantes() {
		List<Estudante> estudantes = estudanteService.buscarEstudantes();
		
		return new ResponseEntity<> (EstudanteDTO.convert(estudantes), HttpStatus.OK);
	}
	
	// Obtençao de um estudante específico.
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<EstudanteDTO> obterEstudantePorID(@PathVariable(required = true) Long id) {
		Estudante encontrado = estudanteService.buscarEstudante(id);
		
		return new ResponseEntity<> (new EstudanteDTO(encontrado), HttpStatus.OK);
	}
	
	// TODO IMPLEMENTAR CADASTRO DE ESTUDANTES (POST)
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<EstudanteDTO> inserirEstudante(@RequestBody EstudanteDTO estudanteDTO) {
		Estudante inserido = estudanteService.cadastrarEstudante(estudanteDTO.toEstudante());
		
		return new ResponseEntity<> (new EstudanteDTO(inserido), HttpStatus.CREATED);
	}
	
	// TODO IMPLEMENTAR ATUALIZACAO DE ESTUDANTES (PUT)
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<EstudanteDTO> atualizarEstudante(@PathVariable(required = true) Long id, @RequestBody EstudanteDTO estudanteDTO) {
		if(id != estudanteDTO.getId()) estudanteDTO.setId(id);
		
		Estudante atualizado = estudanteService.atualizarEstudante(estudanteDTO.toEstudante());
		
		return new ResponseEntity<> (new EstudanteDTO(atualizado), HttpStatus.OK);
	}
	
	// TODO IMPLEMENTAR A EXCLUSÃO DE ESTUDANTES (DELETE)
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerEstudante(@PathVariable(required = true) Long id, @RequestBody EstudanteDTO estudanteDTO) {
		
		estudanteService.removerEstudante(id);
	}
}
