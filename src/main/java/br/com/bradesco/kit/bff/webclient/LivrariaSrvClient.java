package br.com.bradesco.kit.bff.webclient;

import br.com.bradesco.kit.bff.dto.LivrariaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${feign.livrariaService.name}", url = "${feign.livrariaService.url}")
public interface LivrariaSrvClient {

    @GetMapping(value = "${feign.livrariaService.get.path}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    List<LivrariaDTO> retornaColecaoSRV();

    @GetMapping(value = "${feign.livrariaService.getbyId.path}?id={id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    LivrariaDTO retornaLivroSRV(@PathVariable("id") Integer idLivro);

    @PostMapping(value = "${feign.livrariaService.create.path}?id={id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    LivrariaDTO cadastrarNovoLivroSRV(@RequestBody LivrariaDTO novoLivro);

    @DeleteMapping(value = "${feign.livrariaService.delete.path}?id={id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    LivrariaDTO deletarLivroSRV(@PathVariable("id") Integer idLivro);

}
