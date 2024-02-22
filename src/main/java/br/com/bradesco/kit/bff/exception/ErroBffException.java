package br.com.bradesco.kit.bff.exception;


import br.com.bradesco.kit.bff.model.ErroBffResponse;

public class ErroBffException extends Exception {

    private static final long serialVersionUID = 8378176063435669342L;
    private final String mensagem;
    private final ErroBffResponse erroBffResponse;

    public ErroBffException(String mensagem, ErroBffResponse erroBffResponse) {
        super(mensagem);
        this.mensagem = mensagem;
        this.erroBffResponse = erroBffResponse;
    }
}
