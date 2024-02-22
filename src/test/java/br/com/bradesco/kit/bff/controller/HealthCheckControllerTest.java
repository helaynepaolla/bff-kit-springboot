package br.com.bradesco.kit.bff.controller;

import br.com.bradesco.kit.bff.config.redis.TestRedisConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = TestRedisConfiguration.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
class HealthCheckControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    /**
     * Called before each test.
     */
    @BeforeEach
    public void setUp() {
        this.context.getBean(HealthEndpoint.class);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    void requisitaEndpointHealth_ViaGet_deveRetornar200ComServicoUP()
            throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/health"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andDo(print());
    }

    @Test
    void requisitaEndpointMetrics_ViaGet_deveRetornar404()
            throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/metrics"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void requisitaEndpointInfo_ViaGet_deveRetornar404()
            throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/info"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void requisitaEndpointEnv_ViaGet_deveRetornar404()
            throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/env"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void requisitaEndpointHeapDump_ViaGet_deveRetornar404()
            throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/heapdump"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void requisitaEndpointMappings_ViaGet_deveRetornar404()
            throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/mappings"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}