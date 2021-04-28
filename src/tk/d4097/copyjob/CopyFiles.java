package tk.d4097.copyjob;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CopyFiles {
  private final String from;
  private final String to;
  private String[] files = new String[0];
  private final boolean forward;
  private final CopyErrors errors = new CopyErrors();

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
      String copyPath = fileName.replaceAll(from, to);
      String src = null;
      String tgt = null;
      if (forward) {
        src = fileName;
        tgt = copyPath;
      } else {
        src = copyPath;
        tgt = fileName;
      }
      try {
        copyFile(src, tgt);
      } catch (IOException e) {
        errors.addError(new CopyError(src, tgt));
      }
    }
  }

  void copyFile(String src, String tgt) throws IOException {
    System.out.println(src + " -> " + tgt);
    Path sourceDirectory = Paths.get(src);
    Path targetDirectory = Paths.get(tgt);
    Files.copy(sourceDirectory, targetDirectory, StandardCopyOption.REPLACE_EXISTING);
  }

  public CopyErrors getErrors() {
    return errors;
  }
}
