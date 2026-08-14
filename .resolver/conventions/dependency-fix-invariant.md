---
name: dependency-fix-invariant
description: How the vulnerable jackson-databind transitive dependency must and must not be remediated in this fixture
type: convention
scope: global
updated: 2026-08-14 (IONE-959)
captured_sha: 9269d355434fc3fbaf21048abae6a01cab004721
sources:
  - pom.xml
  - README.md
---

This repo's whole purpose is to exercise resolver-core's dependency-fix
journey (`j90`), and the pom.xml FIXTURE INVARIANT comment (lines 17-28) plus
../../README.md make the rule explicit — this page exists only to flag it as
a hard constraint, not to restate the prose:

- `main` must keep `jackson-dataformat-yaml:2.13.2` as the only declared
  dependency, so it keeps transitively pulling the vulnerable
  `jackson-databind:2.13.2` (CVE-2022-42003).
- The correct fix is a `<dependencyManagement>` pin of `jackson-databind` to a
  patched version (2.13.4.2+) — Maven's mechanism for forcing a transitive
  version — added only on a PR branch, never merged to `main`.
- Do NOT remediate by editing the `<dependencies>` block, by declaring
  `jackson-databind` directly, or by bumping `jackson-dataformat-yaml`. Any of
  those would make the vulnerable transitive edge disappear and defeat the
  fixture.
- Do NOT touch `AppTest.java` as part of a dependency fix — its docstring
  ("the dependency fix must not need to touch it") is itself part of the
  fixture contract: a correct fix is a pure `pom.xml` change.

If you are asked to "fix" or "remediate" this vulnerability, the expected
diff is exactly one `<dependencyManagement>` block added to `pom.xml` on a
non-main branch — nothing in `src/`.
