package br.com.tokiomarine.seguradora.avaliacao.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
//@Table(name = "customers")
@Data
public class Estudante {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;// (chave com auto incremento)
	
	@NotNull(message = "Nome é obrigatório")
	private String nome;// (com validação para não nulo e mensagem: Nome é obrigatório)
	
	@NotNull(message = "Email é obrigatório")
	private String email;// (com validação para não nulo e mensagem: Email é obrigatório)
	
	private String telefone;//telefone
	
	@NotNull(message = "Matrícula é obrigatória")
	private String matricula;// (com validação para não nulo e mensagem: Matrícula é obrigatória)
	
	private String curso;
}
