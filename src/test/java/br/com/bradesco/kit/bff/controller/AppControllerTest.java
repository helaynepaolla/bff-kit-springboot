package br.com.bradesco.kit.bff.controller;

import br.com.bradesco.enge.logcloud.canal.SrvCanalLogger;
import br.com.bradesco.kit.bff.dto.SampleClientDTO;
import br.com.bradesco.kit.bff.handler.GlobalExceptionHandler;
import br.com.bradesco.kit.bff.webclient.ExemploInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
class AppControllerTest {

    @InjectMocks
    AppController appController;

    @Mock
    ExemploInterface srvInterface;

    @Mock
    SampleClientDTO sampleClientDTO;

    @Spy
    SrvCanalLogger SRV_CANAL_LOGGER;

    private MockMvc mockMvc;

    /**
     * Called before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(appController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void testRequisitaEndpointSampleClientReq_ViaGet_deveRetornarHTTP200() throws Exception {
        when(srvInterface.getClient()).thenReturn(sampleClientDTO);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/v1/sample-client-req"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

        //verify(SRV_CANAL_LOGGER, atLeastOnce()).log(any());
    }

}