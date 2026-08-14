---
name: overview
description: What teamboard-java is (a dependency-fix test fixture, not a product) and why main stays vulnerable on purpose
type: knowledge
scope: global
updated: 2026-08-14 (IONE-959)
captured_sha: 9269d355434fc3fbaf21048abae6a01cab004721
sources:
  - README.md
  - pom.xml
  - src/main/java/com/example/App.java
---

`teamboard-java` is a minimal Java/Maven fixture used by resolver-core's
dependency-fix journey (`j90`), not an application under active development.
See ../../README.md for the full fixture description and provisioning steps.

The entire source is two files: `src/main/java/com/example/App.java` (a
`main`/`toYaml` pair that serializes a `Map` to YAML via
`com.fasterxml.jackson.dataformat.yaml.YAMLMapper`) and
`src/test/java/com/example/AppTest.java` (one JUnit 5 test asserting the
output contains `"teamboard"`).

**Load-bearing fact (not just documented, verified in `pom.xml`):**
`pom.xml` declares only `jackson-dataformat-yaml:2.13.2`. That artifact
transitively pulls `jackson-databind:2.13.2`, which is vulnerable
(GHSA-57j2-w4cx-62h2 / CVE-2022-42003; fixed in 2.13.4.2).
`jackson-databind` is never declared directly — `App.java` imports
`com.fasterxml.jackson.databind.ObjectMapper` specifically so the transitive
artifact sits on the real compile/runtime path instead of being tree-shakeable
dead weight.

Do not "fix" this by editing the `<dependencies>` block on `main` — see
[[dependency-fix-invariant]] for why, and what the correct remediation shape
actually is.
