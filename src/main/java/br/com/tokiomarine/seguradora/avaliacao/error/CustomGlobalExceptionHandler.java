package br.com.tokiomarine.seguradora.avaliacao.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteIDInvalidoEX;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteJaCadastradoEX;
import br.com.tokiomarine.seguradora.avaliacao.exception.EstudanteNaoEncontradoEX;

@ControllerAdvice(basePackages = "br.com.tokiomarine.seguradora.avaliacao")
public final class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EstudanteNaoEncontradoEX.class)
	public ResponseEntity<EstudantesError> customHandleEstudanteNotFound(Exception ex, WebRequest request) {

		EstudantesError estudantesError = new EstudantesError(
				String.valueOf(EstudanteNaoEncontradoEX.ERROR_CODE.getHttpCode().value()),
				EstudanteNaoEncontradoEX.ERROR_CODE.getCode(),
				ex.getCause().getMessage());
		
		return new ResponseEntity<>(estudantesError, EstudanteNaoEncontradoEX.ERROR_CODE.getHttpCode());
	}
	
	@ExceptionHandler(EstudanteJaCadastradoEX.class)
	public ResponseEntity<EstudantesError> customHandleEstudanteJaCadastrado(Exception ex, WebRequest request) {

		EstudantesError estudantesError = new EstudantesError(
				String.valueOf(EstudanteJaCadastradoEX.ERROR_CODE.getHttpCode().value()),
				EstudanteJaCadastradoEX.ERROR_CODE.getCode(),
				ex.getCause().getMessage());
		
		return new ResponseEntity<>(estudantesError, EstudanteJaCadastradoEX.ERROR_CODE.getHttpCode());
	}
	
	@ExceptionHandler(EstudanteIDInvalidoEX.class)
	public ResponseEntity<EstudantesError> customHandleIDEstudanteInvalido(Exception ex, WebRequest request) {

		EstudantesError estudantesError = new EstudantesError(
				String.valueOf(EstudanteIDInvalidoEX.ERROR_CODE.getHttpCode().value()),
				EstudanteIDInvalidoEX.ERROR_CODE.getCode(),
				ex.getCause().getMessage());
		
		return new ResponseEntity<>(estudantesError, EstudanteIDInvalidoEX.ERROR_CODE.getHttpCode());
	}
}
