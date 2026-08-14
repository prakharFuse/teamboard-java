---
name: architecture
description: Real shape of the fixture — single Maven module, one runtime dependency edge to a transitively-vulnerable artifact
type: knowledge
scope: global
updated: 2026-08-14 (IONE-959)
captured_sha: 9269d355434fc3fbaf21048abae6a01cab004721
sources:
  - pom.xml
  - src/main/java/com/example/App.java
  - src/test/java/com/example/AppTest.java
---

Single-module Maven build, no services, no network, no persistence. The only
non-trivial edge is the transitive dependency pull shown below.

```mermaid
flowchart TD
  App["App.main / App.toYaml\nsrc/main/java/com/example/App.java"]
  AppTest["AppTest.serializesBoardToYaml\nsrc/test/java/com/example/AppTest.java"]
  YAMLMapper["jackson-dataformat-yaml:2.13.2\n(declared in pom.xml)"]
  Databind["jackson-databind:2.13.2\n(TRANSITIVE, vulnerable — GHSA-57j2-w4cx-62h2)"]

  AppTest -->|calls| App
  App -->|"new YAMLMapper()"| YAMLMapper
  App -->|"imports ObjectMapper"| Databind
  YAMLMapper -->|pulls in| Databind
```

`App` imports `ObjectMapper` from `jackson-databind` directly even though the
POM never declares that artifact — that import is what keeps the vulnerable
transitive dependency on the real compile/runtime path (see
[[dependency-fix-invariant]]).
