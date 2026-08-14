---
name: dependency-fix-invariant
description: Rules for touching pom.xml dependencies in this fixture repo — read before any dependency-related change
type: convention
scope: global
updated: 2026-08-14 (IONE-959)
captured_sha: 9269d355434fc3fbaf21048abae6a01cab004721
sources:
  - pom.xml
  - README.md
---

The CVE fixture story, the exact advisory, and the required remediation shape
are already fully documented in ../../README.md and in the comment block
above `<dependencies>` in ../../pom.xml — read those first, don't re-derive
them here.

## The one rule that matters for any agent working in this repo

- **Never add or edit `<dependencies>`** to address the `jackson-databind`
  vulnerability. It is intentionally undeclared (pulled in transitively via
  `jackson-dataformat-yaml:2.13.2`), and that is the fixture's premise.
- The only correct remediation is a `<dependencyManagement>` pin of
  `jackson-databind` to a patched version, added on a **PR branch**, per
  README.md — `main` must keep the vulnerable transitive resolution.
- This applies to *any* dependency-related change in this repo, not only the
  known CVE: if a task asks to bump or pin a dependency, prefer
  `<dependencyManagement>` over editing `<dependencies>` unless the task
  explicitly targets a directly-declared dependency
  (`jackson-dataformat-yaml` or `junit-jupiter`).

## Verifying the current transitive resolution

`jackson-databind`'s resolved version isn't visible from `pom.xml` alone
(it's transitive). To confirm what version is actually on the classpath
before/after a change:

```bash
mvn dependency:tree -Dincludes=com.fasterxml.jackson.core:jackson-databind
```
