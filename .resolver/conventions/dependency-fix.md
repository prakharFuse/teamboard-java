---
name: dependency-fix
description: Required pom.xml mechanism and exact pin syntax for remediating the transitive jackson-databind CVE
type: convention
scope: global
updated: 2026-08-12 (IONE-959)
captured_sha: 8f141d015aa88233413371fec313713f953ca32e
sources:
  - pom.xml
---

See `../../README.md` and the header comment in `../../pom.xml` for why the
fix must be a `<dependencyManagement>` pin (never a `<dependencies>` edit)
and why `main` must stay on the vulnerable transitive version.

**Exact pin (verified against the applied fix in `pom.xml`):** add this as a
top-level sibling of `<dependencies>` in `pom.xml`:

```xml
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.13.2.1</version>
    </dependency>
  </dependencies>
</dependencyManagement>
```

Note: the README/advisory describe the CVE as "fixed 2.13.4.2" upstream, but
the applied remediation pins to `2.13.2.1` — a backport fix on the 2.13.x
line that keeps the minor version aligned with the rest of the Jackson BOM
(`jackson-dataformat-yaml:2.13.2`) rather than jumping to 2.13.4.2.

- Do not add `jackson-databind` under `<dependencies>` — that edits the
  declared dependency set instead of forcing the transitive resolution.
- Do not touch `App.java` or `AppTest.java` for this fix; it is `pom.xml`-only.
