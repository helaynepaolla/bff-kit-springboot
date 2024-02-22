package br.com.bradesco.kit.bff.controller;

import br.com.bradesco.kit.bff.logging.ExceptionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import br.com.bradesco.kit.bff.service.LivrariaService;
import br.com.bradesco.kit.bff.dto.LivrariaDTO;
import br.com.bradesco.kit.bff.exception.InfrastructureException;
import br.com.bradesco.kit.bff.handler.ResponseHandler;
import br.com.bradesco.kit.bff.logging.DadosLogTecnico;

@Api
@RestController
@RequestMapping("/bff-kit-springboot/v1")
public class LivrariaBFFController {

    //Objeto especifico para realizar log de auditoria
    private static final Logger LOGGER = LoggerFactory.getLogger(LivrariaBFFController.class);

    @Autowired
    private LivrariaService srvCanalLivraria;

    @ApiOperation(value = "Consultar colecao de livros do SRV", notes = "Requisita o microsserviço para retorno de todos os livros cadastradas na base de dados.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok")})
    @GetMapping(value = "/lista-colecao-livros", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LivrariaDTO>> ctrlRetornoColecaoSRV() throws Exception {

        List<LivrariaDTO> responseSrvLivraria = null;
        DadosLogTecnico logTecnico = new DadosLogTecnico();

        logTecnico.BufferLogTecnico("Enviando requisição ao SRV.");

        try {
            responseSrvLivraria = srvCanalLivraria.retornaColecaoLivros();
            logTecnico.BufferLogTecnico("Consulta de colecao de livros teve sucesso!");

        } catch (InfrastructureException ex) {

            // Início log técnico
            // Implementação de logging por request com a escrita do log no finally
            // Maiores detalhes em: https://confluence.bradesco.com.br:8443/pages/viewpage.action?pageId=151881724
            logTecnico.setLevelError();
            logTecnico.BufferLogTecnico("Response code: " + ex.getErrCode() +
                    " Durante a requisição ao micro servico. Mensagem: " + ex.getErrMsg());
            logTecnico.BufferLogTecnico(ExceptionUtils.exceptionStacktraceToString(ex));
            // Fim log técnico
            return ResponseEntity.internalServerError().body(responseSrvLivraria);

        } finally {
            // Print log técnico
            logTecnico.disparaEventoLog(LOGGER);
        }
        return ResponseEntity.ok().body(responseSrvLivraria);
    }

    @ApiOperation(value = "Consultar livro por Id do SRV", notes = "Requisita o micro serviço para retorno de um livro especifico por ID.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok")})
    @GetMapping(value = "/detalha-livro", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LivrariaDTO> ctrlRetornaLivroSRV(@RequestParam(name = "IDLivro") Integer IDLivro) throws Exception {

        LivrariaDTO responseSrvLivraria = null;
        DadosLogTecnico logTecnico = new DadosLogTecnico();

        logTecnico.BufferLogTecnico("Enviando requisição ao SRV.");

        try {
            responseSrvLivraria = srvCanalLivraria.retornaLivro(IDLivro);
            logTecnico.BufferLogTecnico("Consulta de livro teve sucesso!");

        } catch (InfrastructureException ex) {
            // Início log técnico
            logTecnico.setLevelError();
            logTecnico.BufferLogTecnico("Response code: " + ex.getErrCode() +
                " Durante a requisição ao micro servico. Mensagem: " + ex.getErrMsg());
            logTecnico.BufferLogTecnico(ExceptionUtils.exceptionStacktraceToString(ex));
            // Fim log técnico
            return ResponseEntity.internalServerError().body(responseSrvLivraria);

        } finally {
            // Print log técnico
            logTecnico.disparaEventoLog(LOGGER);
        }
        return ResponseEntity.ok().body(responseSrvLivraria);
    }

    @ApiOperation(value = "Cadastrar novo Livro", notes = "Envia ao micro serviço a requisição para criar um novo livro na base de dados")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok")})
    @PostMapping(value = "/cadastrar-novo-livro", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> ctrlCadastrarNovoLivro(@Valid @RequestBody LivrariaDTO docNovoLivro) throws Exception {

        LivrariaDTO responseSrvLivraria = null;
        DadosLogTecnico logTecnico = new DadosLogTecnico();
        String responseMsg = "";

        logTecnico.BufferLogTecnico("Enviando requisição ao SRV.");

        try {
            responseSrvLivraria = srvCanalLivraria.cadastrarNovoLivro(docNovoLivro);
            responseMsg = "Novo livro criado com sucesso com o ID " + docNovoLivro.getIdLivro();
            logTecnico.BufferLogTecnico(responseMsg);

        } catch (InfrastructureException ex) {
            // Início log técnico
            logTecnico.setLevelError();
            logTecnico.BufferLogTecnico("Response code: " + ex.getErrCode() +
                " Durante a requisição ao micro servico. Mensagem: " + ex.getErrMsg());
            logTecnico.BufferLogTecnico(ExceptionUtils.exceptionStacktraceToString(ex));
            // Fim log técnico
            responseMsg = ex.getErrMsg();
            return ResponseEntity.internalServerError().body(responseMsg);

        } finally {
            // Print log técnico
            logTecnico.disparaEventoLog(LOGGER);
        }
        return ResponseHandler.generateResponse(responseMsg, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Requisição de remoção de um livro", notes = "Requisita o micro serviço para deleção de um livro.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Ok")})
    @DeleteMapping(value = "/deletar-livro", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> ctrlDeletarLivro(@RequestParam(name = "IDLivro") Integer IDLivro) throws Exception {

        LivrariaDTO responseSrvLivraria = null;
        DadosLogTecnico logTecnico = new DadosLogTecnico();
        String responseMsg = "";

        logTecnico.BufferLogTecnico("Enviando requisição ao SRV.");

        try {
            responseSrvLivraria = srvCanalLivraria.deletarLivro(IDLivro);
            responseMsg = "Livro com o ID " + IDLivro + " deletado com sucesso!";
            logTecnico.BufferLogTecnico(responseMsg);

        } catch (InfrastructureException ex) {
            // Início log técnico
            logTecnico.setLevelError();
            logTecnico.BufferLogTecnico("Response code: " + ex.getErrCode() +
                " Durante a requisição ao micro servico. Mensagem: " + ex.getErrMsg());
            logTecnico.BufferLogTecnico(ExceptionUtils.exceptionStacktraceToString(ex));
            // Fim log técnico
            responseMsg = ex.getErrMsg();
            return ResponseEntity.internalServerError().body(responseMsg);

        } finally {
            // Print log técnico
            logTecnico.disparaEventoLog(LOGGER);
        }
        return ResponseHandler.generateResponse(responseMsg, HttpStatus.ACCEPTED);
    }
}
