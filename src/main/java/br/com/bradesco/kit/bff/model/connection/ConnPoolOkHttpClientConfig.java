package br.com.bradesco.kit.bff.model.connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "okhttp-connection-pool")
public class ConnPoolOkHttpClientConfig {

    /**
     * The maximum number of idle connections for each address
     * okhttp - maxIdleConnections - default 5
     *
     * @see okhttp3.internal.connection.RealConnectionPool
     */
    @Value("${okhttp-connection-pool.max-idle-connections}")
    private Integer maxIdleConnections;

    /**
     * okhttp - keepAliveTime - default 60seg
     */
    @Value("${okhttp-connection-pool.keep-alive-duration-segundos}")
    private Integer keepAliveDurationSegundos;

    public Integer getMaxIdleConnections() {
        return maxIdleConnections;
    }

    public Integer getKeepAliveDurationSegundos() {
        return keepAliveDurationSegundos;
    }
}
