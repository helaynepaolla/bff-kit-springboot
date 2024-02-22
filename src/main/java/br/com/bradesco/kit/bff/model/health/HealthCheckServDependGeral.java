package br.com.bradesco.kit.bff.model.health;

import java.util.List;

/**
 * Healtch Check customizado especifico para identificar a saude dos servicos dependentes
 */
public class HealthCheckServDependGeral {

    private final List<HealthCheckServDepend> healthCheckServDependList;
    private final boolean todosServicosUP;

    public HealthCheckServDependGeral(List<HealthCheckServDepend> healthCheckServDependList, boolean todosServicosUP) {
        this.healthCheckServDependList = healthCheckServDependList;
        this.todosServicosUP = todosServicosUP;
    }

    public List<HealthCheckServDepend> getHealthCheckServDependList() {
        return healthCheckServDependList;
    }

    public boolean isTodosServicosUP() {
        return todosServicosUP;
    }
}
