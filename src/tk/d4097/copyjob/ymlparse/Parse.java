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
  private Map<String, String> map = new HashMap<>();

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
      throw new YmlParseException("Meaningless statement before # sign: \"" + line + "\".");
    }
    if (colonIndex < 1) {
      throw new YmlParseException("Meaningless statement: \"" + line + "\".");
    }

    String noComment = (hashIndex > 0) ? line.substring(hashIndex + 2) : line;

    String s = noComment.substring(0, colonIndex).trim();
    String pattern = "^[a-z_][a-z_0-9\\-]+$";
    if (s.matches(pattern)) {
      vr = new Var(s);
    } else {
      throw new YmlParseException("Wrong var name: \"" + line + "\".");
    }
  }
}
