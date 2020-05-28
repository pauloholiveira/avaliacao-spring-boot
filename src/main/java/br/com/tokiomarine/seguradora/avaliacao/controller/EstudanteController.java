package br.com.tokiomarine.seguradora.avaliacao.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.tokiomarine.seguradora.avaliacao.entidade.Estudante;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteIDInvalidoEX;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteJaCadastradoEX;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteNaoEncontradoEX;
import br.com.tokiomarine.seguradora.avaliacao.service.EstudandeService;

@Controller
@RequestMapping("/estudantes/")
public class EstudanteController {

	public static final String ATRIBUTO_LISTA_ESTUDANTES_NAME="estudantes";
	public static final String ATRIBUTO_ESTUDANTE_NAME="estudante";
	
	public static final String PAGE_INDEX="index";
	public static final String PAGE_CADASTRAR_ESTUDANTE="cadastrar-estudante";
	public static final String PAGE_ATUALIZAR_ESTUDANTE="atualizar-estudante";
	
	@Autowired
	private EstudandeService service;
	
	@GetMapping("criar")
	public String iniciarCastrado(Estudante estudante) {
		return PAGE_CADASTRAR_ESTUDANTE;
	}

	@GetMapping("listar")
	public String listarEstudantes(Model model) {
		model.addAttribute(ATRIBUTO_LISTA_ESTUDANTES_NAME, service.buscarEstudantes());
		return PAGE_INDEX;
	}

	@PostMapping("add")
	public String adicionarEstudante(@Valid Estudante estudante, BindingResult result, Model model) throws EstudanteJaCadastradoEX {
		if (result.hasErrors()) {
			return PAGE_CADASTRAR_ESTUDANTE;
		}

		service.cadastrarEstudante(estudante);

		return "redirect:listar";
	}

	@GetMapping("editar/{id}")
	public String exibirEdicaoEstudante(@PathVariable("id") Long id, Model model) throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX {
		Estudante estudante = service.buscarEstudante(id);
		model.addAttribute(ATRIBUTO_ESTUDANTE_NAME, estudante);
		return PAGE_ATUALIZAR_ESTUDANTE;
	}

	@PostMapping("atualizar/{id}")
	public String atualizarEstudante(@PathVariable("id") Long id, @Valid Estudante estudante, BindingResult result, Model model) throws EstudanteNaoEncontradoEX {
		if (result.hasErrors()) {
			estudante.setId(id);
			return PAGE_ATUALIZAR_ESTUDANTE;
		}

		service.atualizarEstudante(estudante);

		model.addAttribute(ATRIBUTO_LISTA_ESTUDANTES_NAME, service.buscarEstudantes());
		return PAGE_INDEX;
	}

	@GetMapping("apagar/{id}")
	public String apagarEstudante(@PathVariable("id") Long id, Model model) throws EstudanteIDInvalidoEX, EstudanteNaoEncontradoEX {
		
		service.removerEstudante(id);
		
		model.addAttribute(ATRIBUTO_LISTA_ESTUDANTES_NAME, service.buscarEstudantes());
		
		return PAGE_INDEX;
	}
}
