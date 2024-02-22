package br.com.bradesco.kit.bff.logging;

import org.slf4j.Logger;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
public class DadosLogTecnico {

    private static final String INFO = "INFO";
    private static final String ERROR = "ERROR";
    String mensagemLog = "";
    String level;


    public DadosLogTecnico() {
        this.level = INFO;
    }

    public void BufferLogTecnico(String mensagemLog) {
        mensagemLog += "\n" + mensagemLog;
    }

    public void setLevelInfo() {
        this.level = INFO;
    }

    public void setLevelError() {
        this.level = ERROR;
    }

    public void disparaEventoLog(Logger logger) {
        if (ERROR.equals(level)) {
            logger.error(mensagemLog);
        } else {
            logger.info(mensagemLog);
        }
    }
}
