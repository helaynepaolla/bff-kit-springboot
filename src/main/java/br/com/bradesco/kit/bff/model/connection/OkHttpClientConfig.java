package br.com.bradesco.kit.bff.model.connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "okhttp-configuracao-geral")
public class OkHttpClientConfig {

    @Value("${okhttp-configuracao-geral.connect-timeout-millis}")
    private Integer connectTimeoutMillis;
    @Value("${okhttp-configuracao-geral.read-timeout-millis}")
    private Integer readTimeoutMillis;
    @Value("${okhttp-configuracao-geral.write-timeout-millis}")
    private Integer writeTimeoutMillis;

    public Integer getConnectTimeoutMillis() {
        return connectTimeoutMillis;
    }

    public Integer getReadTimeoutMillis() {
        return readTimeoutMillis;
    }

    public Integer getWriteTimeoutMillis() {
        return writeTimeoutMillis;
    }
}
