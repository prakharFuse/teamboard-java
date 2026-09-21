package com.teamboard.worker;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/** Boots the scheduled jobs. */
public final class WorkerApplication {

    private static final Logger LOG = Logger.getLogger(WorkerApplication.class.getName());

    private WorkerApplication() {
    }

    public static void main(String[] args) throws Exception {
        Properties config = new Properties();
        try (var in = WorkerApplication.class.getResourceAsStream("/application.properties")) {
            config.load(in);
        }

        String jdbcUrl = config.getProperty("teamboard.jdbcUrl", "jdbc:sqlite:../data/team.db");
        Path dropDirectory = Paths.get(config.getProperty("teamboard.export.dropDirectory", "./out/hr"));
        long intervalHours = Long.parseLong(config.getProperty("teamboard.export.intervalHours", "24"));

        ExportBatchWorker exportWorker = new ExportBatchWorker(
            new MemberRepository(jdbcUrl), new MemberCsvWriter(), dropDirectory);

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                exportWorker.runBatch();
            } catch (Exception e) {
                LOG.severe("Export batch failed: " + e.getMessage());
            }
        }, 0, intervalHours, TimeUnit.HOURS);

        LOG.info("TeamBoard worker started; export runs every " + intervalHours + "h");
    }
}
