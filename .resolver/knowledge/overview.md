---
name: overview
description: What teamboard-java is, how to build/test it, and where to look for the fixture invariant
type: knowledge
scope: global
updated: 2026-08-12 (IONE-959)
captured_sha: 9269d355434fc3fbaf21048abae6a01cab004721
sources:
  - pom.xml
  - src/main/java/com/example/App.java
  - src/test/java/com/example/AppTest.java
---

teamboard-java is a minimal Java/Maven fixture app. See `../../README.md` for
the full purpose (resolver-core journey j90) and the dependency-fix invariant
governing `pom.xml`.

**Build/test (not stated in README — verified from `pom.xml`):**
- `mvn compile` / `mvn test` / `mvn package` — standard Maven lifecycle, no
  custom build steps or profiles are defined.
- Target: Java 17 (`maven.compiler.release=17`).
- Tests: JUnit Jupiter 5.10.2 via `maven-surefire-plugin` 3.2.5.

**Structure** (the whole repo, 2 source files):
- `src/main/java/com/example/App.java` — single class `App` with
  `toYaml(Map<String, Object>)` and `main(String[])`.
- `src/test/java/com/example/AppTest.java` — single test class `AppTest`
  with one `@Test` (`serializesBoardToYaml`).

`App.toYaml` builds a `YAMLMapper` (a `jackson-databind` `ObjectMapper`
subclass from `jackson-dataformat-yaml`) and calls `writeValueAsString` on it,
so `jackson-databind` is exercised on both the compile and runtime paths —
see `../knowledge/architecture.md` for how that dependency reaches the app.

For why `jackson-databind:2.13.2` must stay on `main` and why any fix belongs
in `<dependencyManagement>` rather than `<dependencies>`, see `../../README.md`
and the header comment in `../../pom.xml`.
