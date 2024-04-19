package it.avbo.dilaxia.api.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;

public class ProjectLogger {
	 // Definizione del logger usando SLF4J
    private static final Logger logger = LoggerFactory.getLogger(ProjectLogger.class);
    private static boolean isAlreadySetUp = false;
    
    public static void setupLogger() {
        if(isAlreadySetUp)
            return;
        
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        // Imposta il percorso del file di configurazione di Logback
        System.setProperty("logback.configurationFile", "path/to/logback.xml");

        // Ottieni il contesto del logger
        context.reset();

        // Imposta il livello di logging desiderato
        context.getLogger(Logger.ROOT_LOGGER_NAME).setLevel(Level.INFO);

        // Aggiungi un appender di file di log
        RollingFileAppender appender = new RollingFileAppender();
        appender.setContext(context);
        appender.setFile("logs/mylog.log");

        // Configura una politica di rotazione basata sul tempo (ogni giorno)
        TimeBasedRollingPolicy rollingPolicy = new TimeBasedRollingPolicy();
        rollingPolicy.setContext(context);
        rollingPolicy.setParent(appender);
        rollingPolicy.setFileNamePattern("logs/mylog-%d{yyyy-MM-dd}.log");
        rollingPolicy.setMaxHistory(30); // Conserva i log degli ultimi 30 giorni
        rollingPolicy.start();

        appender.setRollingPolicy(rollingPolicy);
        appender.start();

        // Aggiungi l'appender al logger radice
        context.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(appender);
        isAlreadySetUp = true;
    }

    public static void logInfo(String message) {
        logger.info("{}", message);
    }

    public static void logError(String message, Throwable throwable) {
        logger.error("{}", message, throwable);
    }
}
