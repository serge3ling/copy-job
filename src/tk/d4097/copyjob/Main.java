package tk.d4097.copyjob;

import tk.d4097.copyjob.ymlparse.Parse;
import tk.d4097.copyjob.ymlparse.YmlParseException;

public class Main {
  public static void main(String[] args) throws YmlParseException {
    Parse yml = new Parse("job-example.yml");
    yml.readFile();
  }
}
