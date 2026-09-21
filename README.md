# TeamBoard Worker

Scheduled batch jobs for the TeamBoard team directory. Java 17, Maven, no web
server — it reads the directory's SQLite file and writes files out.

## Jobs

| Job | Entry point | Schedule |
| --- | --- | --- |
| Nightly HR export | `ExportBatchWorker.runBatch()` | `teamboard.export.intervalHours` (default 24h) |

The export writes `members-<date>.csv` into `teamboard.export.dropDirectory`
for the HR sync to collect.

## Running

```bash
mvn test          # unit tests
mvn compile exec:java -Dexec.mainClass=com.teamboard.worker.WorkerApplication
```

Point `teamboard.jdbcUrl` at the same `data/team.db` the TeamBoard web app
serves from. The worker only reads it.
