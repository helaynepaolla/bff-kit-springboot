package br.com.bradesco.kit.bff.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Para o caso de não desejar utilizar o FeignClient, utilize o RestTemplate com OkHttpClient para realizar
 * requisições HTTP.
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, OkHttpConfiguration okHttpConfiguration) {
        return builder.requestFactory(okHttpConfiguration).build();
    }
}