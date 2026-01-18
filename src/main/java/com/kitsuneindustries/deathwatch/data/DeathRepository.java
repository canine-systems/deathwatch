package com.kitsuneindustries.deathwatch.data;

import java.util.List;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DeathRepository {
    public static final Logger LOGGER = LogUtils.getLogger();

    private EntityManagerFactory emFactory;
    private EntityManager em;

    public void setUp() throws Exception {
        /*
         * TODO Maybe change this over to use
         *
         * @see jakarta.persistence.PersistenceConfiguration
         */
        emFactory = Persistence.createEntityManagerFactory("DeathWatch");

        em = emFactory.createEntityManager();
    }

    public PlayerDeath create(PlayerDeath playerDeath) {
        LOGGER.info("Recording death: " + playerDeath.toString());
        em.getTransaction().begin();
        em.persist(playerDeath);
        em.getTransaction().commit();
        return playerDeath;
    }

    public List<PlayerDeath> findAll() {
        LOGGER.info("Finding all deaths");
        em.getTransaction().begin();
        List<PlayerDeath> result = em.createQuery("SELECT d FROM PlayerDeath d", PlayerDeath.class).getResultList();
        em.getTransaction().commit();
        return result;
    }

    public List<PlayerDeath> findByPlayerName(String playerName) {
        LOGGER.info("Finding all deaths for " + playerName);
        em.getTransaction().begin();
        List<PlayerDeath> result = em
            .createQuery("SELECT d FROM PlayerDeath d WHERE c.playerName LIKE :playerName", PlayerDeath.class)
            .setParameter("playerName", playerName)
            .getResultList();
        em.getTransaction().commit();
        return result;
    }
}
