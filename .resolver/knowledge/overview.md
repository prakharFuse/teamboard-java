---
name: overview
description: What teamboard-java is and why it exists — read before touching any file in this repo
type: knowledge
scope: global
updated: 2026-08-12 (IONE-959)
captured_sha: 9269d355434fc3fbaf21048abae6a01cab004721
sources:
  - README.md
  - pom.xml
  - src/main/java/com/example/App.java
  - src/test/java/com/example/AppTest.java
---

teamboard-java is not a product — it is a resolve-journey **fixture** (j90, resolver-core spec 015) that exercises the dependency-fix flow for a transitively-pulled vulnerable Maven dependency. See ../../README.md for the full journey/provisioning story; don't repeat it here.

The whole app is two files:
- `src/main/java/com/example/App.java` — a `App.toYaml(Map)` helper (via `YAMLMapper`) and a `main` that prints a sample board as YAML. Exists solely so `jackson-databind` is a real compile/runtime dependency, not prunable.
- `src/test/java/com/example/AppTest.java` — one JUnit 5 test (`serializesBoardToYaml`) asserting the YAML output contains `"teamboard"`.

**The fixture invariant** (already stated in ../../README.md and in the `pom.xml` header comment, so treat both as authoritative): `main` must stay on the vulnerable transitive `jackson-databind:2.13.2` (GHSA-57j2-w4cx-62h2 / CVE-2022-42003). The real fix — a `<dependencyManagement>` pin to a patched version — belongs only on a resolve PR branch, never merged to `main`. Any task that edits `pom.xml` `<dependencies>` directly, or merges a pin to `main`, is working against the fixture's purpose.
