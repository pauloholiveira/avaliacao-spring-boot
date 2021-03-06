package br.com.tokiomarine.seguradora.avaliacao.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import br.com.tokiomarine.seguradora.avaliacao.error.ErrorCodes;
import br.com.tokiomarine.seguradora.avaliacao.error.EstudantesError;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteIDInvalidoEX;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteJaCadastradoEX;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteNaoEncontradoEX;
import br.com.tokiomarine.seguradora.avaliacao.service.EstudandeService;

@RestController
@RequestMapping("/api/estudantes")
@ExposesResourceFor(EstudanteDTO.class)
public class EstudanteRestController {

	@Autowired 
	private EstudandeService estudanteService;
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resources<EstudanteDTO>> listarEstudantes() throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX {
		List<EstudanteDTO> estudantesDTOs = EstudanteDTO.convert(estudanteService.buscarEstudantes());
		
		for (final EstudanteDTO estudanteDTO : estudantesDTOs) {
			Link selfLink = linkTo(methodOn(EstudanteRestController.class).obterEstudantePorID(estudanteDTO.getIdEstudante())).withSelfRel();
			estudanteDTO.add(selfLink);
		}

		Link link = linkTo(EstudanteRestController.class).withSelfRel();
				
		Resources<EstudanteDTO> respModel = new Resources<>(estudantesDTOs, link);
		return new ResponseEntity<>(respModel, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resource<EstudanteDTO>> obterEstudantePorID(@PathVariable(required = true) Long id) throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX {
		Estudante encontrado = estudanteService.buscarEstudante(id);
		EstudanteDTO estudanteDTO = new EstudanteDTO(encontrado);
		
		Link selfLink = linkTo(methodOn(EstudanteRestController.class).obterEstudantePorID(id)).withSelfRel();
		Resource<EstudanteDTO> entModel = new Resource<>(estudanteDTO, selfLink);
		
		return new ResponseEntity<> (entModel, HttpStatus.OK);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Resource<EstudanteDTO>> inserirEstudante(@Valid @RequestBody EstudanteDTO estudanteDTO) throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX, EstudanteJaCadastradoEX {
		Estudante inserido = estudanteService.cadastrarEstudante(estudanteDTO.toEstudante());
		
		Link selfLink = linkTo(methodOn(EstudanteRestController.class).obterEstudantePorID(inserido.getId())).withSelfRel();
		Resource<EstudanteDTO> entModel = new Resource<>(new EstudanteDTO(inserido), selfLink);
		
		return new ResponseEntity<> (entModel, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Resource<EstudanteDTO>> atualizarEstudante(@PathVariable(required = true) Long id, @Valid @RequestBody EstudanteDTO estudanteDTO) throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX {
		if(!id.equals(estudanteDTO.getIdEstudante())) estudanteDTO.setIdEstudante(id);
		
		Estudante atualizado = estudanteService.atualizarEstudante(estudanteDTO.toEstudante());
		
		Link selfLink = linkTo(methodOn(EstudanteRestController.class).obterEstudantePorID(atualizado.getId())).withSelfRel();
		Resource<EstudanteDTO> entModel = new Resource<>(new EstudanteDTO(atualizado), selfLink);
		
		return new ResponseEntity<> (entModel, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerEstudante(@PathVariable(required = true) Long id) throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX {
		
		estudanteService.removerEstudante(id);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<EstudantesError> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		EstudantesError estudantesError = new EstudantesError(String.valueOf(ErrorCodes.ESTUDANTE_ID_INVALIDO.getHttpCode()),
				ErrorCodes.ESTUDANTE_ID_INVALIDO.getCode(),
				errors.toString());
		
		return new ResponseEntity<>(estudantesError, ErrorCodes.ESTUDANTE_ID_INVALIDO.getHttpCode());
	}
}
