package it.avbo.dilaxia.api.services;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.Configurator;
import ch.qos.logback.classic.tyler.TylerConfiguratorBase;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;


public class LoggerConfigurator extends TylerConfiguratorBase implements Configurator {

    @Override
    public ExecutionStatus configure(LoggerContext loggerContext) {
        setContext(loggerContext);

        RollingFileAppender appender = new RollingFileAppender();
        appender.setContext(context);
        appender.setFile("${USER_HOME}/logs/dilaxia-api.log");

        // Configura una politica di rotazione basata sul tempo (ogni giorno)
        TimeBasedRollingPolicy rollingPolicy = new TimeBasedRollingPolicy();
        rollingPolicy.setContext(context);
        rollingPolicy.setParent(appender);
        rollingPolicy.setFileNamePattern("${USER_HOME}/logs/dilaxia-api-%d{yyyy-MM-dd}.log");
        rollingPolicy.setMaxHistory(30); // Conserva i log degli ultimi 30 giorni
        rollingPolicy.start();

        appender.setRollingPolicy(rollingPolicy);
        appender.start();

        Logger rootLogger = setupLogger("ROOT", "debug", null);
        rootLogger.addAppender(appender);

        return ExecutionStatus.DO_NOT_INVOKE_NEXT_IF_ANY;
    }
}
