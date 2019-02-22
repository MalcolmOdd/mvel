package org.mvel2;

/**
 * Interface tagging unknown values. This can be implemented to provide guidance for figuring out the value.
 * @author Luc Thuot
 */
public interface Unknown {
  public static final Unknown UNKNOWN = new Unknown() {
    @Override
    public String toString() {
      return "UNKNOWN";
    }
  };
}
