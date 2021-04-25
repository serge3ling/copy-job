package tk.d4097.copyjob.ymlparse;

import java.util.Objects;

public class Var {
  private String name;
  private boolean typeSet;
  private VarType type;
  private StringBuffer valBuffer = new StringBuffer();
  private Object val;

  public Var(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public VarType getType() {
    return type;
  }

  public void setType(VarType type) throws YmlParseException {
    if (typeSet) {
      throw new YmlParseException("Var type can be set only once. Var name: {0}.", name);
    } else {
      this.type = type;
      typeSet = true;
    }
  }

  public void appendToBuilder(String s) {
    valBuffer.append(s);
  }

  public void build() throws YmlParseException {
    switch (type) {
      case TXT:
        val = valBuffer.toString();
        break;
      case BRACKET_ARR:
        buildBracketArray();
        break;
      case HYPHEN_ARR:
        buildHyphenArray();
        break;
      default:
    }
  }

  void buildBracketArray() {
    String[] separated = valBuffer.toString().split(",");
    val = new String[separated.length];
    for (int i = 0; i < separated.length; i++) {
      ((String[]) val)[i] = separated[i].trim();
    }
  }

  void buildHyphenArray() {
    String[] separated = valBuffer.toString().split("\n");
    val = new String[separated.length];
    for (int i = 0; i < separated.length; i++) {
      ((String[]) val)[i] = separated[i].trim();
    }
  }

  public Object getVal() {
    return val;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Var var = (Var) o;
    return Objects.equals(name, var.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    String s = "";
    switch (type) {
      case TXT:
        s = val.toString();
        break;
      case BRACKET_ARR:
      case HYPHEN_ARR:
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < ((String[]) val).length; i++) {
          sb.append(((String[]) val)[i]).append(",\n");
        }
        sb.append("]");
        s = sb.toString();
        break;
      default:
    }
    return name + " (" + type + "): " + s;
  }
}
