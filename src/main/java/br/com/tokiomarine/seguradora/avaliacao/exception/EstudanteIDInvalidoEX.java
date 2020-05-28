package br.com.tokiomarine.seguradora.avaliacao.exception;

import br.com.tokiomarine.seguradora.avaliacao.error.ErrorCodes;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EstudanteIDInvalidoEX extends EstudanteEX{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4743723654574891024L;
	
	public static final ErrorCodes ERROR_CODE = ErrorCodes.ESTUDANTE_ID_INVALIDO;
	
	public EstudanteIDInvalidoEX(String message) {
		super(message);
	}

}
