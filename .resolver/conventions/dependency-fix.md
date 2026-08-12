---
name: dependency-fix-conventions
description: How to remediate the jackson-databind vulnerability in this fixture — read before editing pom.xml
type: convention
scope: global
updated: 2026-08-12 (IONE-959)
captured_sha: 9269d355434fc3fbaf21048abae6a01cab004721
sources:
  - README.md
  - pom.xml
---

This repo exists to validate one specific remediation shape, so the "correct"
fix here is narrower than a typical Java project:

- Add a `<dependencyManagement>` block pinning
  `com.fasterxml.jackson.core:jackson-databind` to a patched version
  (>= 2.13.4.2 for GHSA-57j2-w4cx-62h2). Do this in `pom.xml` only.
- Do NOT add `jackson-databind` as a direct entry under `<dependencies>` —
  the fixture's premise is that it stays transitive and the pin is the only
  lever Maven gives you to override a transitive version.
- Do NOT change the `jackson-dataformat-yaml` version, and do NOT edit
  `src/main/java/com/example/App.java` or
  `src/test/java/com/example/AppTest.java` — the test is meant to stay green
  across the fix untouched.
- Any such fix belongs on a PR branch; `main` intentionally stays on the
  vulnerable transitive version (see [[overview]]).

There's no other coding-standard surface in this repo (one class, one test) —
this page only exists because the dependency-fix shape is non-obvious and
easy to get wrong by editing `<dependencies>` directly.
