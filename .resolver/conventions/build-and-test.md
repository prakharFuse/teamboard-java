---
name: build-and-test
description: How to build, test, and run this project, plus a jar-packaging gotcha verified from pom.xml
type: convention
scope: global
updated: 2026-08-14 (IONE-959)
captured_sha: 9269d355434fc3fbaf21048abae6a01cab004721
sources:
  - pom.xml
---

## Commands

- `mvn test` — compiles and runs the single JUnit 5 test in `AppTest.java` via
  `maven-surefire-plugin:3.2.5`. This is the standard way to verify a change.
- `mvn compile` — compiles sources only.
- No Maven wrapper is committed, so a system `mvn` install is required.

## Gotcha: `mvn package` does not produce a runnable jar

`pom.xml`'s `<build><plugins>` only configures `maven-compiler-plugin` and
`maven-surefire-plugin` — there is no `maven-jar-plugin` manifest
configuration, no `maven-shade-plugin`, and no `maven-assembly-plugin`.
Consequences:

- The packaged jar has **no `Main-Class` manifest entry**, so
  `java -jar target/teamboard-java-1.0.0.jar` fails with
  `no main manifest attribute`.
- The packaged jar does **not** bundle `jackson-dataformat-yaml`/
  `jackson-databind`, so even `java -cp target/classes com.example.App` fails
  with `ClassNotFoundException` unless the dependency jars are also placed on
  the classpath (e.g. via `mvn dependency:build-classpath` or by running
  through Maven).
- To actually run `App.main`, use Maven's classpath rather than the packaged
  jar, e.g. `mvn compile exec:java -Dexec.mainClass=com.example.App` (this
  resolves `exec-maven-plugin` on the fly since it isn't declared in `pom.xml`).

This is not mentioned in README.md — it's a real limitation of the current
`pom.xml`, not a documented design choice. If a task ever needs App.java to be
directly runnable as a jar, adding a jar-plugin `Main-Class` manifest entry (or
a shade/assembly plugin to bundle dependencies) would be the fix — but note
[[dependency-fix-invariant]] before touching `pom.xml` on `main`.
