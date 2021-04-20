package tk.d4097.copyjob;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class YmlParse {
  private String fileName = "job.yml";

  private BufferedReader reader;

  public YmlParse() {}
  
  public YmlParse(String fileName) {
    this.fileName = fileName;
  }
  
  public boolean parse() {
    boolean fine = true;
    readFile();
    return fine;
  }

  void readFile() {
    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
      String line = "";
      while (line != null) {
        String trimmed = line.trim();
        if (!(trimmed.length() == 0) && !(trimmed.startsWith("#"))) {
          ;
        }
      }
    } catch (IOException e) {}
  }
}
