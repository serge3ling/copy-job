package tk.d4097.copyjob.ymlparse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Parse {
  enum State {
    FIND_VAR,
    FIND_TYPE,
    READ_VAR
  }

  private String fileName = "job.yml";
  private State state = State.FIND_VAR;
  private Var vr;
  private final Map<String, Var> map = new HashMap<>();

  public Parse(String fileName) {
    this();
    this.fileName = fileName;
  }

  public Parse() {
  }

  public void readFile() throws YmlParseException {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String line = "";
      while (line != null) {
        String trimmed = line.trim();
        if (!(trimmed.length() == 0) && !(trimmed.startsWith("#"))) {
          parseLine(trimmed);
        }
        line = reader.readLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  void parseLine(String line) throws YmlParseException {
    switch (state) {
      case FIND_VAR:
        findVar(line);
        break;
      case FIND_TYPE:
        System.out.println("FIND_TYPE");
        findType(line);
        break;
      case READ_VAR:
        System.out.println("READ_VAR");
        break;
      default:
    }
  }

  void findVar(String line) throws YmlParseException {
    int hashIndex = line.indexOf(" #");
    int colonIndex = line.indexOf(": ");
    if ((hashIndex >= 0) && (hashIndex < colonIndex)) {
      throw new YmlParseException("Meaningless statement before # sign: \"{0}\".", line);
    }
    if (colonIndex < 1) {
      throw new YmlParseException("Meaningless statement: \"{0}\".", line);
    }

    String noComment = (hashIndex > 0) ? line.substring(hashIndex + 2) : line;

    String name = noComment.substring(0, colonIndex).trim();
    String pattern = "^[a-z_][a-z_0-9\\-]+$";
    if (!name.matches(pattern)) {
      throw new YmlParseException("Wrong var name: \"{0}\".", line);
    }

    String tail = noComment.substring(colonIndex + 2).trim();
    vr = new Var(name);
    map.put(name, vr);
    if (tail.length() > 0) {
      if (tail.charAt(0) == '[') {
        parseBracketArray(tail);
      } else {
        vr.setType(VarType.TXT);
        vr.appendToBuilder(tail);
        vr.build();
      }
      System.out.println(vr);
    } else {
      state = State.FIND_VAR;
    }
  }

  void findType(String line) throws YmlParseException {
    boolean found = false;
    if (line.charAt(0) == '[') {
      found = true;
      parseBracketArray(line);
    }
    if (line.charAt(0) == '-') {
      found = true;
      vr.setType(VarType.HYPHEN_ARR);
      state = State.READ_VAR;
      int hashIndex = line.indexOf(" #");
      String noComment = ((hashIndex > 0) ? line.substring(hashIndex + 2) : line).trim();
      vr.appendToBuilder(noComment + "\n");
    }
    if (!found) {
      throw new YmlParseException("Unrecognized type of var \"{0}\".", vr.getName());
    }
  }

  void parseBracketArray(String tail) throws YmlParseException {
    vr.setType(VarType.BRACKET_ARR);
    int closing = tail.indexOf(']');

    if (closing > 0) {
      if (closing == (tail.length() - 1)) {
        vr.appendToBuilder(tail.substring(1, tail.length() - 1).trim());
        vr.build();
      } else {
        throw new YmlParseException("After ']' closing array there cannot be any text: \"{0}\".", tail);
      }
    } else {
      state = State.READ_VAR;
      vr.appendToBuilder(tail.substring(1).trim());
    }
  }
}
