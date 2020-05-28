package br.com.tokiomarine.seguradora.avaliacao.error;

import org.springframework.http.HttpStatus;

public enum ErrorCodes {
    ESTUDANTE_NAO_ENCONTRADO(HttpStatus.NOT_FOUND, "CEX-001"),
    ESTUDANTE_JA_CADASTRADO(HttpStatus.CONFLICT, "CEX-002"),
    ESTUDANTE_ID_INVALIDO(HttpStatus.BAD_REQUEST, "CEX-003");

    private final HttpStatus httpCode;
    private final String code;

    ErrorCodes(HttpStatus codigoHttp, String codigo) {
        this.httpCode = codigoHttp;
        this.code = codigo;
    }

    public HttpStatus getHttpCode() {
        return httpCode;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "ERROR_CODES{" +
                "httpCode='" + httpCode + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

}
