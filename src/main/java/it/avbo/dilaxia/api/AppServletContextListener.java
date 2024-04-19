package it.avbo.dilaxia.api;

import it.avbo.dilaxia.api.database.DBWrapper;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppServletContextListener implements ServletContextListener {
    private static Logger logger = LoggerFactory.getLogger(AppServletContextListener.class);
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        DBWrapper.setupDatabase();
        logger.info("Database initialized");
    }
}
