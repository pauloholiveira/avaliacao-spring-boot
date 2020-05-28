package br.com.tokiomarine.seguradora.avaliacao.error;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstudantesError implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4403699134491868533L;


	private String httpCode;
	private String errorCode;
	private String message;

}
