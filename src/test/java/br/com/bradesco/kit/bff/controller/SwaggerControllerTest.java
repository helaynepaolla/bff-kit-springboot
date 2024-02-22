package br.com.bradesco.kit.bff.controller;

import br.com.bradesco.kit.bff.config.SwaggerConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@DisplayName("Teste geral de Swagger com base no Profile")
@Import(SwaggerConfig.class)
class SwaggerControllerTest {

    @SpringBootTest(properties = {"spring.main.allow-bean-definition-overriding=true", "spring.redis.port=6370",
            "spring.redis.host=localhost"})
    @ActiveProfiles("LOCAL")
    @DisplayName("Teste Swagger com Profile LOCAL")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class SwaggerTestComProfileLocal {

        @Autowired
        private WebApplicationContext context;

        private MockMvc mockMvc;

        @BeforeEach
        public void setUp() {
            this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        }

        @Test
        void requisitaEndpointSwaggerUI_ViaGet_deveRetornarHTTP200()
                throws Exception {
            this.mockMvc.perform(MockMvcRequestBuilders.get("/swagger-ui/index.html"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        }

        @Test
        void requisitaEndpointAPIDocs_ViaGet_deveRetornarHTTP200()
                throws Exception {
            this.mockMvc.perform(MockMvcRequestBuilders.get("/api-docs"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        }
    }

    @SpringBootTest(properties = {"spring.main.allow-bean-definition-overriding=true", "spring.redis.port=6370",
            "spring.redis.host=localhost"})
    @ActiveProfiles("DEV")
    @DisplayName("Teste Swagger com Profile DEV")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class SwaggerTestComProfileDev {

        @Autowired
        private WebApplicationContext context;

        private MockMvc mockMvc;

        @BeforeEach
        public void setUp() {
            this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        }

        @Test
        void requisitaEndpointSwaggerUI_ViaGet_deveRetornarHTTP200()
                throws Exception {
            this.mockMvc.perform(MockMvcRequestBuilders.get("/swagger-ui/index.html"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        }

        @Test
        void requisitaEndpointAPIDocs_ViaGet_deveRetornarHTTP200()
                throws Exception {
            this.mockMvc.perform(MockMvcRequestBuilders.get("/api-docs"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        }
    }

    @SpringBootTest(properties = {"spring.main.allow-bean-definition-overriding=true", "spring.redis.port=6370",
            "spring.redis.host=localhost"})
    @ActiveProfiles("HOM")
    @DisplayName("Teste Swagger com Profile HOM")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class SwaggerTestComProfileHom {

        @Autowired
        private WebApplicationContext context;

        private MockMvc mockMvc;

        @BeforeEach
        public void setUp() {
            this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        }

        @Test
        void requisitaEndpointSwaggerUI_ViaGet_deveRetornarHTTP200()
                throws Exception {
            this.mockMvc.perform(MockMvcRequestBuilders.get("/swagger-ui/index.html"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        }

        @Test
        void requisitaEndpointAPIDocs_ViaGet_deveRetornarHTTP200()
                throws Exception {
            this.mockMvc.perform(MockMvcRequestBuilders.get("/api-docs"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        }
    }

    @SpringBootTest(properties = {"spring.main.allow-bean-definition-overriding=true", "spring.redis.port=6370",
            "spring.redis.host=localhost"})
    @ActiveProfiles("PRD")
    @DisplayName("Teste Swagger com Profile PRD")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class SwaggerTestComProfilePRD {

        @Autowired
        private WebApplicationContext context;

        private MockMvc mockMvc;

        @BeforeEach
        public void setUp() {
            this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        }

        @Test
        void requisitaEndpointSwaggerUI_ViaGet_deveRetornarHTTP404()
                throws Exception {
            this.mockMvc.perform(MockMvcRequestBuilders.get("/swagger-ui/index.html"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andDo(print());
        }

        @Test
        void requisitaEndpointAPIDocs_ViaGet_deveRetornarHTTP404()
                throws Exception {
            this.mockMvc.perform(MockMvcRequestBuilders.get("/api-docs"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andDo(print());
        }
    }
}