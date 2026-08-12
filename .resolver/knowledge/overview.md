---
name: overview
description: What teamboard-java is and why it exists — read before touching pom.xml or src/
type: knowledge
scope: global
updated: 2026-08-12 (IONE-959)
captured_sha: 9269d355434fc3fbaf21048abae6a01cab004721
sources:
  - README.md
  - pom.xml
  - src/main/java/com/example/App.java
---

teamboard-java is a Resolver test fixture, not a product codebase — see README.md
for the full journey (`j90`) rationale. It is the Java/Maven counterpart to a
JS/pnpm `teamboard` fixture used to prove the dependency-fix resolve path for
transitive vulnerabilities.

Two source files only:
- `src/main/java/com/example/App.java` — a `final class App` with a private
  constructor, `toYaml(Map<String,Object>)` and `main(String[])`. It genuinely
  calls `jackson-databind`'s `ObjectMapper`/`YAMLMapper` at runtime so the
  vulnerable transitive artifact is real on the compile+runtime path, not
  prunable dead code.
- `src/test/java/com/example/AppTest.java` — one JUnit 5 test,
  `serializesBoardToYaml`, asserting the YAML output contains `"teamboard"`.

`pom.xml` declares exactly one runtime dependency,
`com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.2`, which pulls
in `com.fasterxml.jackson.core:jackson-databind:2.13.2` transitively — the
version vulnerable to GHSA-57j2-w4cx-62h2 / CVE-2022-42003 (fixed in
2.13.4.2). `jackson-databind` is never declared directly in `<dependencies>`.

**Fixture invariant (do not break):** `main` must stay on the vulnerable
transitive `jackson-databind:2.13.2`. The correct fix a dependency-fix resolve
should produce is a `<dependencyManagement>` pin of `jackson-databind` to a
patched version, applied only on a PR branch — never a direct edit to
`<dependencies>`, and never merged to `main`. See [[dependency-fix-conventions]].
