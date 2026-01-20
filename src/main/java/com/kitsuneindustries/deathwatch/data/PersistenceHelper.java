package com.kitsuneindustries.deathwatch.data;

import org.slf4j.Logger;

import com.kitsuneindustries.deathwatch.Config;
import com.mojang.logging.LogUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;

public class PersistenceHelper {

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String UNIT_NAME = "com.kitsuneindustries.deathwatch";

    // private static SessionFactory sessionFactory;
    private static EntityManagerFactory emFactory;

    public static void setup() {
        try {
            PersistenceConfiguration config = new PersistenceConfiguration(UNIT_NAME)
                .managedClass(PlayerDeath.class)
                .property(PersistenceConfiguration.JDBC_URL, Config.JDBC_URL.get())
                .property(PersistenceConfiguration.JDBC_USER, Config.JDBC_USER.get())
                .property(PersistenceConfiguration.JDBC_PASSWORD, Config.JDBC_URL.get());
            /*
             * Configuration config = new Configuration()
             * .addAnnotatedClass(PlayerDeath.class)
             * .setProperty(AvailableSettings.PERSISTENCE_UNIT_NAME,
             * "com.kitsuneindustries.deathwatch")
             * .setProperty(AvailableSettings.JAKARTA_JDBC_URL, Config.JDBC_URL.get())
             * .setProperty(AvailableSettings.JAKARTA_JDBC_USER, Config.JDBC_USER.get())
             * .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, Config.JDBC_USER.get())
             * .setProperty(AvailableSettings.POOL_SIZE, 2) .showSql( Config.SHOW_SQL.get(),
             * Config.FORMAT_SQL.get(), Config.HIGHLIGHT_SQL.get());
             */
            // PersistenceHelper.sessionFactory = config.buildSessionFactory();
            emFactory = config.createEntityManagerFactory();

            /*
             * PersistenceHelper.sessionFactory = new
             * HibernatePersistenceConfiguration("com.kitsuneindustries.deathwatch")
             * .jdbcUrl(Config.JDBC_URL.get()) .jdbcUsername(Config.JDBC_USER.get())
             * .jdbcPassword(Config.JDBC_PASS.get()) .jdbcPoolSize(2) .showSql(
             * Config.SHOW_SQL.get(), Config.FORMAT_SQL.get(), Config.HIGHLIGHT_SQL.get())
             * .createEntityManagerFactory();
             */
        } catch (Exception e) {
            LOGGER.error("Error setting up hibernate:", e);
        }
    }

    public static EntityManager getEntityManager() {
        if (emFactory == null) {
            LOGGER.error("Attempted to get session before PersistenceHelper was ready. This shouldn't happen!!");
        }
        setup();
        return emFactory.createEntityManager();
    }
    /*
     * public static Session getSession() throws HibernateException { if (emFactory
     * == null) { LOGGER.
     * error("Attempted to get session before PersistenceHelper was ready. This shouldn't happen!!"
     * ); } setup(); emFactory. if (sessionFactory == null) { setup(); }
     * 
     * return sessionFactory.openSession(); }
     */

}
