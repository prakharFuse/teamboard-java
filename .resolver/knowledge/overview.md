---
name: overview
description: What teamboard-java is, its structure, and where the authoritative fixture story lives
type: knowledge
scope: global
updated: 2026-08-14 (IONE-959)
captured_sha: 9269d355434fc3fbaf21048abae6a01cab004721
sources:
  - pom.xml
  - src/main/java/com/example/App.java
  - src/test/java/com/example/AppTest.java
  - README.md
---

This is a deliberately tiny single-module Maven project (4 tracked files total:
`pom.xml`, `README.md`, `App.java`, `AppTest.java`). It exists as a **fixture repo**
for an external test journey, not as a product codebase — see ../../README.md for
the full story of why it exists and what invariant it must preserve (the
transitive-dependency CVE fixture and the `<dependencyManagement>` remediation
path).

## Structure

- `src/main/java/com/example/App.java` — a `final` utility class (private
  constructor, no instance state) with two static methods: `toYaml(Map)` and
  `main(String[])`. It genuinely calls into `jackson-databind` (via
  `YAMLMapper`) so that dependency is real on the compile/runtime path.
- `src/test/java/com/example/AppTest.java` — one JUnit 5 test asserting the
  YAML output contains the expected string via `assertTrue(...contains(...))`.
- No other source files, no config files, no CI workflow, and no
  `CLAUDE.md`/`AGENTS.md`/`.cursor` rules exist in this repo.

## Derived facts not stated in README/pom

- **Maven coordinates don't match the Java package**: `groupId` is
  `com.appfire.resolver`, `artifactId` is `teamboard-java`, but all source
  lives under Java package `com.example`. This is consistent with the repo
  being a generic fixture rather than a real "teamboard" product.
- **No Maven wrapper** (`mvnw`/`mvnw.cmd`) is committed — building requires a
  system-installed Maven.
- **No CI workflow** exists (`.github/workflows` is absent) — `mvn test`
  must be run manually to verify changes.

See [[architecture]] for the dependency graph and [[dependency-fix-invariant]]
for the rules around the CVE fixture, and [[build-and-test]] for how to
actually build/run/test this project.
