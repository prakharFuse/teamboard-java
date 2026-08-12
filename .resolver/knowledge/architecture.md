---
name: architecture
description: Dependency graph showing how the vulnerable jackson-databind version reaches the app transitively
type: knowledge
scope: global
updated: 2026-08-12 (IONE-959)
captured_sha: 9269d355434fc3fbaf21048abae6a01cab004721
sources:
  - pom.xml
  - src/main/java/com/example/App.java
---

```mermaid
graph TD
  App["App.java<br/>com.example.App"] -->|"new YAMLMapper() / ObjectMapper#writeValueAsString"| YAML["jackson-dataformat-yaml 2.13.2<br/>declared in pom.xml"]
  YAML -->|"transitive dependency"| DB["jackson-databind 2.13.2<br/>NOT declared directly — vulnerable<br/>GHSA-57j2-w4cx-62h2 / CVE-2022-42003"]
```

`pom.xml` declares only `jackson-dataformat-yaml:2.13.2` (plus a test-scoped
`junit-jupiter`); it pulls `jackson-databind:2.13.2` in transitively.
`App.java` imports `com.fasterxml.jackson.databind.ObjectMapper` directly and
instantiates `YAMLMapper` (an `ObjectMapper` subclass), so the vulnerable
class is on the real compile + runtime path, not just present in the
dependency tree unused.
