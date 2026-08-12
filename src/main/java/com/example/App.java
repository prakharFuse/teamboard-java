package com.example;

import com.fasterxml.jackson.databind.ObjectMapper; // transitive via jackson-dataformat-yaml
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Trivial app that genuinely uses jackson-databind (the transitively-pulled,
 * vulnerable artifact) so the dependency is real on the compile + runtime path —
 * not a phantom that a build could tree-shake away.
 */
public final class App {
  private App() {}

  /** Serialize a small map to YAML via jackson (databind + dataformat-yaml). */
  public static String toYaml(Map<String, Object> data) throws Exception {
    ObjectMapper mapper = new YAMLMapper();
    return mapper.writeValueAsString(data);
  }

  public static void main(String[] args) throws Exception {
    Map<String, Object> board = new LinkedHashMap<>();
    board.put("board", "teamboard");
    board.put("ok", true);
    System.out.print(toYaml(board));
  }
}
