package br.com.bradesco.kit.bff.handler;

import br.com.bradesco.kit.bff.exception.BusinessException;
import br.com.bradesco.kit.bff.exception.InfrastructureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static br.com.bradesco.kit.bff.model.ErrorEnum.ERRO_422;
import static br.com.bradesco.kit.bff.model.ErrorEnum.ERRO_500;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GlobalExceptionandlerTest.RestProcessingExceptionThrowingController.class)
@ActiveProfiles("test")
class GlobalExceptionandlerTest {

    private MockMvc mockMvc;


    /**
     * Called before each test.
     */
    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(RestProcessingExceptionThrowingController.class)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void testRequisitaEndpointQualquer_simulaExcecaoBusinessException_deveRetornarErroSrvResponseComHTTP500() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tests/businessException"))
                .andExpect(result -> result.getResponse().getContentAsString().contains(ERRO_422.getMensagem()))
                .andExpect(status().isUnprocessableEntity()).andDo(print());
    }

    @Test
    void testRequisitaEndpointQualquer_simulaExcecaoInfraException_deveRetornarErroSrvResponseComHTTP422() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tests/infraException"))
                .andExpect(result -> result.getResponse().getContentAsString().contains(ERRO_500.getMensagem()))
                .andExpect(status().isInternalServerError()).andDo(print());
    }

    @Test
    void testRequisitaEndpointQualquer_simulaExcecaoGenerica_deveRetornarErroSrvResponseComHTTP500() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tests/exception"))
                .andExpect(result -> result.getResponse().getContentAsString().contains(ERRO_500.getMensagem()))
                .andExpect(status().isInternalServerError()).andDo(print());
    }

    @Controller
    @RequestMapping("/tests")
    public static class RestProcessingExceptionThrowingController {
        @GetMapping(value = "/infraException")
        public @ResponseBody String getInfrastructureException() {
            throw new InfrastructureException("", "");
        }

        @GetMapping(value = "/businessException")
        public @ResponseBody String getBusinessException() {
            throw new BusinessException("", "");
        }

        @GetMapping(value = "/exception")
        public @ResponseBody String getException() throws Exception {
            throw new Exception("");
        }
    }

}
