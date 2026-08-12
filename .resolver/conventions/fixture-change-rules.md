---
name: fixture-change-rules
description: What a resolve/PR is allowed to touch in this fixture, and what must never change on main
type: convention
scope: global
updated: 2026-08-12 (IONE-959)
captured_sha: 9269d355434fc3fbaf21048abae6a01cab004721
sources:
  - pom.xml
  - src/test/java/com/example/AppTest.java
  - README.md
---

- The correct remediation for GHSA-57j2-w4cx-62h2 is a `<dependencyManagement>` entry pinning `com.fasterxml.jackson.core:jackson-databind` to `>= 2.13.4.2`, added inside `<project>` in `pom.xml` — not an edit to the existing `<dependencies>` block (see the fixture-invariant comment at pom.xml:17-28, and ../../README.md). That pin belongs on a resolve PR branch only.
- `src/test/java/com/example/AppTest.java` is deliberately trivial (`assertTrue(yaml.contains("teamboard"))`) and is annotated in-code as a test that "the dependency fix must not need to touch" — a dependency-version fix should leave this file untouched and green under `mvn test`.
- `src/main/java/com/example/App.java` exists only to keep `jackson-databind` reachable on the compile/runtime path; don't remove the `ObjectMapper`/`YAMLMapper` usage as part of a dependency fix, since that would make the vulnerable artifact prunable and defeat the fixture's purpose.
- Build tooling versions (`maven.compiler.release=17`, `maven-compiler-plugin:3.13.0`, `maven-surefire-plugin:3.2.5`, `junit.version=5.10.2` in pom.xml properties) are fixed for this fixture; there's no CI workflow in the repo, so `mvn test` locally is the only verification signal.
