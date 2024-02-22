package br.com.bradesco.kit.bff.webclient;

import br.com.bradesco.kit.bff.dto.SampleClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "${feign.funcionalidadeExemplo.name}", url = "${feign.funcionalidadeExemplo.url}")
public interface ExemploInterface {

    @RequestMapping(value = "${feign.funcionalidadeExemplo.consultaSucesso.path}")
    SampleClientDTO getClient();

    @RequestMapping(value = "${feign.funcionalidadeExemplo.consultaErroInfra.path}")
    SampleClientDTO getClientReturnInfraErr();

    @RequestMapping(value = "${feign.funcionalidadeExemplo.consultaErroBusiness.path}")
    SampleClientDTO getClientReturnBusinessExc();
}