package com.wordgame.recordsstorage.configuration;

import org.flywaydb.core.Flyway;

import javax.sql.DataSource;

public class FlywayUtility {
    private final DataSource dataSource;

    public FlywayUtility(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setUp() {
        clean();
        migrate();
    }

    public void tearDown() {
        migrate();
    }

    private void clean() {
        Flyway.configure()
                .dataSource(dataSource)
                .load()
                .clean();
    }

    private void migrate() {
        Flyway.configure()
                .dataSource(dataSource)
                .load()
                .migrate();
    }
}
