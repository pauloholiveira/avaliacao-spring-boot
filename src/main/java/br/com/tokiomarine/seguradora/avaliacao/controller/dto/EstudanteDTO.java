package br.com.tokiomarine.seguradora.avaliacao.controller.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import br.com.tokiomarine.seguradora.avaliacao.entidade.Estudante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@NoArgsConstructor @AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public final class EstudanteDTO  extends ResourceSupport{
	
	private Long ID;
	private String nome;
	private String email;// (com validação para não nulo e mensagem: Email é obrigatório)
	private String telefone;//telefone
	private String matricula;// (com validação para não nulo e mensagem: Matrícula é obrigatória)
	private String curso;

	public EstudanteDTO(final Estudante e) {
		if(e != null) {
			this.ID = e.getId();
			this.nome = e.getNome();
			this.email = e.getEmail();
			this.telefone = e.getTelefone();
			this.matricula = e.getMatricula();
			this.curso = e.getCurso();
		}
	}

	public static List<EstudanteDTO> convert(Collection<Estudante> estudantes) {
		List<EstudanteDTO> estudantesDTO = new ArrayList<>();
		
		if(!estudantes.isEmpty()) {
			for(Estudante estudante : estudantes) {
				EstudanteDTO estudanteDTO = new EstudanteDTO(estudante);
				estudantesDTO.add(estudanteDTO);
			}
		}
		
		return estudantesDTO;
	}
	
	public Estudante toEstudante() {
		Estudante estudante = new Estudante();
		
		estudante.setId(this.ID);
		estudante.setNome(this.nome);
		estudante.setEmail(this.email);
		estudante.setTelefone(this.telefone);
		estudante.setMatricula(this.matricula);
		estudante.setCurso(this.curso);
		
		return estudante;
	}
}