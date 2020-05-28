package br.com.tokiomarine.seguradora.avaliacao.exception;

import br.com.tokiomarine.seguradora.avaliacao.error.ErrorCodes;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EstudanteNaoEncontradoEX extends EstudanteEX{

	
    /**
	 * 
	 */
	private static final long serialVersionUID = -4332810619011076462L;
	
	public static final ErrorCodes ERROR_CODE = ErrorCodes.ESTUDANTE_NAO_ENCONTRADO;

	public EstudanteNaoEncontradoEX(String message) {
		super(message);
	}
}
