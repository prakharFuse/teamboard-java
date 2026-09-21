package com.teamboard.worker;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

/**
 * Writes the nightly members CSV to the HR drop directory.
 *
 * Runs on the schedule in {@code application.properties}; there is no HTTP
 * entry point. The web app also exposes {@code GET /api/members/export}, which
 * builds the same file on demand for whoever clicks "Download CSV for HR".
 */
public class ExportBatchWorker {

    private static final Logger LOG = Logger.getLogger(ExportBatchWorker.class.getName());

    private final MemberRepository repository;
    private final MemberCsvWriter writer;
    private final Path dropDirectory;

    public ExportBatchWorker(MemberRepository repository, MemberCsvWriter writer, Path dropDirectory) {
        this.repository = repository;
        this.writer = writer;
        this.dropDirectory = dropDirectory;
    }

    /** One pass of the nightly export. Returns how many rows it wrote. */
    public int runBatch() throws SQLException, IOException {
        List<Member> members = repository.findAll();
        Path destination = dropDirectory.resolve("members-" + LocalDate.now() + ".csv");
        writer.writeTo(destination, members);
        LOG.info("Exported " + members.size() + " member(s) to " + destination);
        return members.size();
    }
}
