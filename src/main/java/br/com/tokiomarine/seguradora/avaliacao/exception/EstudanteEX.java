package br.com.tokiomarine.seguradora.avaliacao.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public class EstudanteEX extends Throwable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -474775775577269546L;

	public EstudanteEX(String cause, Throwable ex){
        super(cause, ex);
    }

    public EstudanteEX(String cause){
        super(cause);
    }
}
