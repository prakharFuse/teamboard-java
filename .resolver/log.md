2026-08-14 · first-run · created .resolver

- Indexed the repo (4 tracked files: pom.xml, README.md, App.java, AppTest.java).
- README.md and the pom.xml comment already fully document the fixture's CVE/remediation story — knowledge pages point to them rather than duplicating.
- Verified from code (not previously documented): pom.xml configures only compiler+surefire plugins, so `mvn package` produces a jar with no Main-Class manifest and no bundled dependencies — captured in conventions/build-and-test.md.
- No CLAUDE.md/AGENTS.md/.cursor rules, no CI workflow, no Maven wrapper found in the repo.
