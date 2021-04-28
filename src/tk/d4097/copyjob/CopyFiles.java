package tk.d4097.copyjob;

public class CopyFiles {
  private final String from;
  private final String to;
  private String[] files = new String[0];
  private final boolean forward;

  public CopyFiles(String from, String to, String[] files, boolean forward) {
    this.from = from;
    this.to = to;
    if (files != null) {
      this.files = files;
    }
    this.forward = forward;
    System.out.println("CopyFiles constructor:\nfrom: " + from
        + "\n  to: " + to + "\nforward: " + forward + ".");
  }

  public void go() {
    for (String fileName: files) {
      String origPath = fileName;
      String copyPath = fileName.replaceAll(from, to);
      String src = null;
      String tgt = null;
      if (forward) {
        src = origPath;
        tgt = copyPath;
      } else {
        src = copyPath;
        tgt = origPath;
      }
      copyFile(src, tgt);
    }
  }

  void copyFile(String src, String tgt) {
    System.out.println(src + " -> " + tgt);
  }
}
