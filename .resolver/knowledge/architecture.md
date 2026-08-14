---
name: architecture
description: Dependency/build graph of the single-module teamboard-java app
type: knowledge
scope: global
updated: 2026-08-14 (IONE-959)
captured_sha: 9269d355434fc3fbaf21048abae6a01cab004721
sources:
  - pom.xml
  - src/main/java/com/example/App.java
  - src/test/java/com/example/AppTest.java
---

Single Maven module, one production class, one test class. No services, no
network calls, no database.

```mermaid
flowchart TD
  AppTest["AppTest (src/test)\nserializesBoardToYaml()"] -->|calls| AppMain
  AppMain["App (src/main)\nmain / toYaml"] -->|uses| YAMLMapper["YAMLMapper\n(jackson-dataformat-yaml:2.13.2)"]
  YAMLMapper -->|extends| ObjectMapper["ObjectMapper\n(jackson-databind, TRANSITIVE)"]
  Pom["pom.xml declared deps"] -->|declares| YAMLMapper
  Pom -.->|pulls in, undeclared| ObjectMapper
```

- `App.toYaml` builds a `YAMLMapper` (which extends Jackson's `ObjectMapper`)
  and calls `writeValueAsString`, so both `jackson-dataformat-yaml` and the
  transitively-pulled `jackson-databind` are exercised on a real code path —
  not just declared in `pom.xml`.
- `pom.xml` declares only `jackson-dataformat-yaml:2.13.2` and (test-scope)
  `junit-jupiter`; `jackson-databind` is never declared directly, which is the
  whole point of the fixture — see [[dependency-fix-invariant]].
- `AppTest` is the only caller of `App` in the repo.
