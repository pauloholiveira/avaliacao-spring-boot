package br.com.tokiomarine.seguradora.avaliacao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.tokiomarine.seguradora.avaliacao.entidade.Estudante;

@Controller
public class IndexController {

	@GetMapping("/")
	public String index(Estudante estudante) {
		return "redirect:estudantes/listar";
	}
}
