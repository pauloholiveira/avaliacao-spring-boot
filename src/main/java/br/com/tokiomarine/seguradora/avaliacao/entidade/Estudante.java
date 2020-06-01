package br.com.tokiomarine.seguradora.avaliacao.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class Estudante {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;// (chave com auto incremento)
	
	@NotNull(message = "Nome é obrigatório")
	@NotBlank(message = "Nome é obrigatório")
	@NotEmpty(message = "Nome é obrigatório")
	private String nome;// (com validação para não nulo e mensagem: Nome é obrigatório)
	
	@NotNull(message = "Email é obrigatório")
	@NotBlank(message = "Email é obrigatório")
	@NotEmpty(message = "Email é obrigatório")
	private String email;// (com validação para não nulo e mensagem: Email é obrigatório)
	
	private String telefone;//telefone
	
	@NotNull(message = "Matrícula é obrigatória")
	@NotBlank(message = "Matrícula é obrigatória")
	@NotEmpty(message = "Matrícula é obrigatória")
	private String matricula;// (com validação para não nulo e mensagem: Matrícula é obrigatória)
	
	private String curso;
}
