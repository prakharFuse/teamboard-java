---
name: architecture
description: Real shape of the (tiny) dependency graph — App, its two Jackson deps, and the one test
type: knowledge
scope: global
updated: 2026-08-12 (IONE-959)
captured_sha: 9269d355434fc3fbaf21048abae6a01cab004721
sources:
  - pom.xml
  - src/main/java/com/example/App.java
  - src/test/java/com/example/AppTest.java
---

```mermaid
flowchart LR
  AppTest["AppTest\n(src/test/.../AppTest.java)"] --> App
  App["App\n(src/main/.../App.java)"] --> YAML["jackson-dataformat-yaml:2.13.2\n(declared in pom.xml)"]
  YAML -->|transitive dep| Databind["jackson-databind:2.13.2\n(vulnerable, NOT declared directly)"]
  JUnit["junit-jupiter:5.10.2\n(test scope)"] -.-> AppTest
```

There is only one Maven module (`com.appfire.resolver:teamboard-java`), one production class (`App`), and one test class (`AppTest`). `App` calls `new YAMLMapper()` (from `jackson-dataformat-yaml`) whose `ObjectMapper` superclass is provided by `jackson-databind` — that's the only edge that matters for this fixture: it's what makes the vulnerable version reachable at compile and runtime instead of being an unused transitive artifact.
