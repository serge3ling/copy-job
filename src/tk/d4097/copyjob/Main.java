package tk.d4097.copyjob;

import tk.d4097.copyjob.ymlparse.Parse;
import tk.d4097.copyjob.ymlparse.YmlParseException;

public class Main {
  public static void main(String[] args) throws YmlParseException {
    Parse yml = new Parse("job-example.yml");
    yml.readFile();
    CopyFiles copyFiles = makeCopyFilesByParse(yml);
    copyFiles.go();
  }

  static CopyFiles makeCopyFilesByParse(Parse parse) {
    String parsedForward = parse.getTxt("forward");
    boolean forward = true;
    if (parsedForward != null) {
      String parsedForwardLowerCase = parsedForward.toLowerCase();
      if (parsedForwardLowerCase.equals("no") ||
          parsedForwardLowerCase.equals("false")) {
        forward = false;
      }
    }

    String parsedFrom = parse.getTxt("from");
    String parsedTo = parse.getTxt("to");
    String from = (parsedFrom == null) ? ("") : parsedFrom;
    String to = (parsedFrom == null) ? ("") : parsedTo;

    String[] parsedFiles = parse.getArr("files");
    String[] files = (parsedFiles == null) ? (new String[0]) : parsedFiles;

    return new CopyFiles(from, to, files, forward);
  }
}
