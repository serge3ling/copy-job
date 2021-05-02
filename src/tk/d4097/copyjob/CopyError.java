package tk.d4097.copyjob;

public class CopyError {
  private final String src;
  private final String tgt;
  private final String msg;

  public CopyError(String src, String tgt, String msg) {
    this.src = src;
    this.tgt = tgt;
    this.msg = msg;
  }

  public String getSrc() {
    return src;
  }

  public String getTgt() {
    return tgt;
  }

  public String getMsg() {
    return msg;
  }

  @Override
  public String toString() {
    return "[Fail] (" + msg + ") " + src + " -> " + src;
  }
}
