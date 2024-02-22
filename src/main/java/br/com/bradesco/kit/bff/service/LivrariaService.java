package br.com.bradesco.kit.bff.service;

import br.com.bradesco.kit.bff.dto.LivrariaDTO;
import br.com.bradesco.kit.bff.exception.InfrastructureException;
import br.com.bradesco.kit.bff.webclient.LivrariaSrvClient;
import feign.FeignException;
import feign.RetryableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivrariaService {

    @Autowired
    private LivrariaSrvClient livrariaSRVInterface;

    public List<LivrariaDTO> retornaColecaoLivros() throws Exception {
        List<LivrariaDTO> colecaoLivros = null;

        try {
            colecaoLivros = livrariaSRVInterface.retornaColecaoSRV();
        } catch (RetryableException | FeignException.GatewayTimeout | FeignException.BadGateway
                 | FeignException.ServiceUnavailable | FeignException.InternalServerError ex) {
            throw new InfrastructureException(ex.status(), ex.getMessage(), ex);
        }
        return colecaoLivros;
    }

    public LivrariaDTO retornaLivro(Integer IdLivro) throws Exception {
        LivrariaDTO objetoLivro = null;

        // Get do livro via Feign dentro de um try/catch para thrown da possível exceção vindo do Feign client
        try {
            //Chamada ao webclient do Feign
            objetoLivro = livrariaSRVInterface.retornaLivroSRV(IdLivro);
        } catch (RetryableException | FeignException.GatewayTimeout | FeignException.BadGateway
                 | FeignException.ServiceUnavailable ex) {
            throw new InfrastructureException(ex.status(), ex.getMessage(), ex);
        }
        return objetoLivro;
    }

    public LivrariaDTO cadastrarNovoLivro(LivrariaDTO docNovoLivro) throws Exception {
        LivrariaDTO novoLivro = null;

        // Request para criacao de livro via Feign dentro de um try/catch para thrown da possível exceção vindo do Feign client
        try {
            //Chamada ao webclient do Feign
            novoLivro = livrariaSRVInterface.cadastrarNovoLivroSRV(docNovoLivro);
        } catch (RetryableException | FeignException.GatewayTimeout | FeignException.BadGateway
                 | FeignException.ServiceUnavailable ex) {
            throw new InfrastructureException(ex.status(), ex.getMessage(), ex);
        }
        return novoLivro;
    }

    public LivrariaDTO deletarLivro(Integer idLivro) throws Exception {
        LivrariaDTO objetoLivro = null;

        // Request para remoção de um livro via Feign dentro de um try/catch para thrown da possível exceção vindo do Feign client
        try {
            //Chamada ao webclient do Feign
            objetoLivro = livrariaSRVInterface.deletarLivroSRV(idLivro);
        } catch (RetryableException | FeignException.GatewayTimeout | FeignException.BadGateway
                 | FeignException.ServiceUnavailable ex) {
            throw new InfrastructureException(ex.status(), ex.getMessage(), ex);
        }
        return objetoLivro;
    }

}
