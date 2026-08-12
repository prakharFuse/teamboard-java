---
name: architecture
description: Dependency graph and build shape of teamboard-java — read before adding/changing dependencies
type: knowledge
scope: global
updated: 2026-08-12 (IONE-959)
captured_sha: 9269d355434fc3fbaf21048abae6a01cab004721
sources:
  - pom.xml
  - src/main/java/com/example/App.java
  - src/test/java/com/example/AppTest.java
---

There is no multi-service architecture here — just one Maven module. The
graph below is the dependency + call shape that matters for the fixture.

```mermaid
flowchart TD
  AppTest["AppTest.serializesBoardToYaml (test)"] --> App
  App["App.toYaml / App.main"] --> YAMLMapper["jackson-dataformat-yaml:2.13.2 (declared)"]
  YAMLMapper --> Databind["jackson-databind:2.13.2 (transitive, CVE-2022-42003)"]
  JUnit["junit-jupiter:5.10.2 (test scope)"] -.-> AppTest
```

Build: `maven-compiler-plugin:3.13.0` (release 17) +
`maven-surefire-plugin:3.2.5` run the JUnit 5 (`junit.version=5.10.2`) tests.
No other plugins, no lockfile — Maven resolves `jackson-databind`'s version
purely from `jackson-dataformat-yaml`'s own POM, which is why a
`<dependencyManagement>` pin (not a `<dependencies>` edit) is the only way to
override it. See [[overview]] and [[dependency-fix-conventions]].
