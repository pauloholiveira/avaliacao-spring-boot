package br.com.tokiomarine.seguradora.avaliacao.exception;

import br.com.tokiomarine.seguradora.avaliacao.error.ErrorCodes;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EstudanteJaCadastradoEX extends EstudanteEX{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1393751945216547846L;
	
	
	public static final ErrorCodes ERROR_CODE = ErrorCodes.ESTUDANTE_JA_CADASTRADO;
	
	public EstudanteJaCadastradoEX(String message) {
		super(message);
	}
}
