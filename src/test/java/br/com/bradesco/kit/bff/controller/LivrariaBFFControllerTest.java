package br.com.bradesco.kit.bff.controller;

import br.com.bradesco.kit.bff.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class LivrariaBFFControllerTest {

    @InjectMocks
    LivrariaBFFController livrariaBFFController;

    private MockMvc mockMvc;

    /**
     * Called before each test.
     */
    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(livrariaBFFController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    @Disabled()
    void testRequisitaEndpointListaColecaoLivros_ViaGet_deveRetornarHTTP200() {
        fail("Not yet implemented");
    }

    @Test
    @Disabled()
    void testRequisitaEndpointDetalhaLivro_ViaGet_deveRetornarHTTP200() {
        fail("Not yet implemented");
    }

    @Test
    @Disabled()
    void testRequisitaEndpointCadastrarNovoLivro_ViaPost_deveRetornarHTTP200() {
        fail("Not yet implemented");
    }

    @Test
    @Disabled()
    void testRequisitaEndpointDeletarLivro_ViaDelete_deveRetornarHTTP200() {
        fail("Not yet implemented");
    }

}