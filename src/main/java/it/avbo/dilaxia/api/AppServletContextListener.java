package it.avbo.dilaxia.api;

import it.avbo.dilaxia.api.database.DBWrapper;
import it.avbo.dilaxia.api.services.ProjectLogger;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class AppServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        DBWrapper.setupDatabase();
        ProjectLogger.setupLogger();
    }
}
