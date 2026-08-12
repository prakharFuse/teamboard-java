# teamboard-java — journey fixture (j90 · resolver-core spec 015)

A tiny, fast-building **Java/Maven** app whose only declared dependency
(`jackson-dataformat-yaml:2.13.2`) pulls **`jackson-databind:2.13.2`
TRANSITIVELY** — a version vulnerable to **GHSA-57j2-w4cx-62h2**
(CVE-2022-42003, fixed 2.13.4.2).

It is the Java counterpart to the `teamboard` (JS/pnpm) fixture: the boot-env
dependency-fix resolve journey **j90** proves the JVM path ships a COMPLETE fix.
Because Maven has no lockfile and jackson-databind is transitive, the correct
deterministic remediation is a `pom.xml` `<dependencyManagement>` pin of
jackson-databind to a patched version — never an edit to `<dependencies>`.

**Fixture invariant:** keep `main` on the vulnerable transitive version — that
is the point. The dependency-fix resolve adds the `<dependencyManagement>` pin
on a PR branch; those PRs are the journey artifact, never merged to main.

## Provision it to GitHub

```bash
# creates <owner>/teamboard-java (owner = JOURNEY_DEPFIX_JAVA_REPO_NS or the
# GITHUB_TEST_USER_TOKEN owner) and seeds these files on `main`. Idempotent:
# leaves an existing repo as-is.
pnpm -C tests/journeys exec tsx scripts/provision-depfix-java-repo.ts
```

Then onboard it boot-env-ready (hosted: j90 self-provisions the Space + Jira
mapping + boot-env build via `ensureDepFixJavaHostedFixture`; local: onboard it
and build the boot-env once). Overridable via `JOURNEY_DEPFIX_JAVA_REPO_NS` /
`JOURNEY_DEPFIX_JAVA_REPO_NAME` / `JOURNEY_DEPFIX_JAVA_ADVISORY`.
