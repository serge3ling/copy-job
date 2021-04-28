package tk.d4097.copyjob;

import java.util.ArrayList;
import java.util.List;

public class CopyErrors {
  private final List<CopyError> errors = new ArrayList<>();

  public void addError(CopyError error) {
    errors.add(error);
  }

  public List<CopyError> getErrors() {
    return errors;
  }

  public int size() {
    return errors.size();
  }

  public CopyError get(int i) {
    return errors.get(i);
  }
}
