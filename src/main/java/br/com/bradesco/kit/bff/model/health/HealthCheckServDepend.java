package br.com.bradesco.kit.bff.model.health;

import org.springframework.boot.actuate.health.Health;

/**
 * Healtch Check customizado especifico para identificar a saude dos servicos dependentes
 */
public class HealthCheckServDepend {

    private final String url;

    private final Health health;

    public HealthCheckServDepend(String url, Health health) {
        this.url = url;
        this.health = health;
    }

    public String getUrl() {
        return url;
    }

    public Health getHealth() {
        return health;
    }
}
