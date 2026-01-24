package com.kitsuneindustries.deathwatch.data;

import org.hibernate.jpa.HibernatePersistenceConfiguration;
import org.slf4j.Logger;

import com.kitsuneindustries.deathwatch.Config;
import com.mojang.logging.LogUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class PersistenceHelper {

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String UNIT_NAME = "com.kitsuneindustries.deathwatch";

    // private static SessionFactory sessionFactory;
    private static EntityManagerFactory emFactory;

    public static void setup() {
        try {
            HibernatePersistenceConfiguration config = new HibernatePersistenceConfiguration(UNIT_NAME)
                .managedClass(Player.class)
                .managedClass(PlayerDeath.class)
                .jdbcUrl(Config.JDBC_URL.get())
                .jdbcCredentials(Config.JDBC_USER.get(), Config.JDBC_PASS.get());

            // PersistenceHelper.sessionFactory = config.buildSessionFactory();
            emFactory = config.createEntityManagerFactory();

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
}
