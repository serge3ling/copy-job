package tk.d4097.copyjob;

public class CopyError {
  private final String src;
  private final String tgt;

  public CopyError(String src, String tgt) {
    this.src = src;
    this.tgt = tgt;
  }

  public String getSrc() {
    return src;
  }

  public String getTgt() {
    return tgt;
  }
}
