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
      throw new YmlParseException("Var type can be set only once.");
    } else {
      this.type = type;
      typeSet = true;
    }
  }

  public void appendToVal(String s) {
    valBuffer.append(s);
  }

  public void build() throws YmlParseException {
    ;
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
}
