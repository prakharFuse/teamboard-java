---
name: dependency-fix
description: Required pom.xml mechanism and exact pin syntax for remediating the transitive jackson-databind CVE
type: convention
scope: global
updated: 2026-08-12 (IONE-959)
captured_sha: 9269d355434fc3fbaf21048abae6a01cab004721
sources:
  - pom.xml
  - README.md
---

See `../../README.md` and the header comment in `../../pom.xml` for why the
fix must be a `<dependencyManagement>` pin (never a `<dependencies>` edit)
and why `main` must stay on the vulnerable transitive version.

**Exact pin (gap — the prose docs describe the mechanism but not the literal
XML):** add this as a top-level sibling of `<dependencies>` in `pom.xml`:

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.13.4.2</version>
    </dependency>
  </dependencies>
</dependencyManagement>
```

- Do not add `jackson-databind` under `<dependencies>` — that edits the
  declared dependency set instead of forcing the transitive resolution.
- Do not touch `App.java` or `AppTest.java` for this fix; it is `pom.xml`-only.
