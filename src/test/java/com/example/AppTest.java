package com.example;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

/** A trivial green test so `mvn test` passes — the dependency fix must not need to touch it. */
class AppTest {
  @Test
  void serializesBoardToYaml() throws Exception {
    String yaml = App.toYaml(Map.of("board", "teamboard"));
    assertTrue(yaml.contains("teamboard"), "YAML output should contain the board name");
  }
}
