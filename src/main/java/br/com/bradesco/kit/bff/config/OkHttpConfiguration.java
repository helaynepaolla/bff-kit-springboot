package br.com.bradesco.kit.bff.config;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Realiza configuração do OkHttpClient para ser reusado
 */
@Configuration
public class OkHttpConfiguration implements Supplier<ClientHttpRequestFactory> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OkHttpConfiguration.class);

    @Value("${okhttp-configuracao-geral.connect-timeout-millis}")
    private long connectTimeout;
    @Value("${okhttp-configuracao-geral.read-timeout-millis}")
    private long readTimeout;
    @Value("${okhttp-configuracao-geral.write-timeout-millis}")
    private long writeTimeout;
    @Value("${okhttp-configuracao-geral.retryOnConnectionFailure}")
    private boolean retryOnConnectionFailure;

    /**
     * The maximum number of idle connections for each address
     * okhttp - maxIdleConnections - default 5
     *
     * @see okhttp3.internal.connection.RealConnectionPool
     */
    @Value("${okhttp-connection-pool.max-idle-connections}")
    private int maxIdleConnections;

    /**
     * okhttp - keepAliveTime - default 60seg
     */
    @Value("${okhttp-connection-pool.keep-alive-duration-segundos}")
    private int keepAliveDurationSegundos;

    /**
     * Criando um OkHttpClient para ser utilizado comumente
     * <p>
     * ok-http-client - connectTimeout - default - 10 seg
     * ok-http-client - readTimeout - default - 10 seg
     * ok-http-client - writeTimeout - default - 10 seg
     * ok-http-client - retryOnConnectionFailure - default - true
     *
     * @return ClientHttpRequestFactory objeto factory de HTTPClient
     * @see <a href="https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/">...</a>
     * @see <a href="https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/-builder/">...</a>
     */
    @Override
    public ClientHttpRequestFactory get() {
        return new OkHttp3ClientHttpRequestFactory(okHttpClient());
    }

    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        ConnectionPool connectionPool = connectionPool();
        builder.connectionPool(connectionPool)
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS);
        builder.retryOnConnectionFailure(retryOnConnectionFailure);
        return builder.build();
    }

    /**
     * Configurando o Pool de Conexões
     *
     * @return ConnectionPool bean ConnectionPool do OkHttp
     * @see <a href="https://square.github.io/okhttp/3.x/okhttp/okhttp3/ConnectionPool.html">...</a>
     * @see <a href="https://square.github.io/okhttp/4.x/okhttp/okhttp3/-connection-pool/">...</a>
     */
    private ConnectionPool connectionPool() {
        return new ConnectionPool(maxIdleConnections,
                keepAliveDurationSegundos, TimeUnit.MILLISECONDS);
    }
}
