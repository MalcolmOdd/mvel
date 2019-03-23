package org.mvel2.tests.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.Unknown;
import org.mvel2.UnknownContainer;

public class UnknownTests extends TestCase {
  public static class UnknownValue implements Unknown {
    private String missing;
    public UnknownValue(String missing) {
      this.missing = missing;
    }
    public String getMissing() {
      return missing;
    }
  }
  public static class SetWithUnknown implements UnknownContainer {
    private Map<String, Boolean> values = new HashMap<String, Boolean>();
    public SetWithUnknown(String ... set) {
      for (String str : set) {
        this.values.put(str, Boolean.TRUE);
      }
    }
    public void setValue(String key, boolean value) {
      values.put(key, value);
    }
    public Object contains(Object key) {
      return values.containsKey(key) ? values.get(key) : new UnknownValue((String)key); 
    }
  }
  public void testAndCompiled() {
    Map<String, Object> vars = new HashMap<String, Object>();
    vars.put("a", Unknown.UNKNOWN);
    vars.put("b", Boolean.FALSE);
    Serializable s = MVEL.compileExpression("a && b");
    assertEquals(Boolean.FALSE, MVEL.executeExpression(s, vars));
    vars.put("b", Boolean.TRUE);
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(s, vars));
    vars.put("a", Boolean.TRUE);
    assertSame(Boolean.TRUE, MVEL.executeExpression(s, vars));
    vars.put("b", Unknown.UNKNOWN);
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(s, vars));
    vars.put("a", Boolean.FALSE);
    assertEquals(Boolean.FALSE, MVEL.executeExpression(s, vars));
  }
  public void testOrCompiled() {
    Map<String, Object> vars = new HashMap<String, Object>();
    vars.put("a", Unknown.UNKNOWN);
    vars.put("b", Boolean.TRUE);
    Serializable s = MVEL.compileExpression("a || b");
    assertEquals(Boolean.TRUE, MVEL.executeExpression(s, vars));
    vars.put("b", Boolean.FALSE);
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(s, vars));
    vars.put("a", Boolean.FALSE);
    assertSame(Boolean.FALSE, MVEL.executeExpression(s, vars));
    vars.put("b", Unknown.UNKNOWN);
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(s, vars));
    vars.put("a", Boolean.TRUE);
    assertEquals(Boolean.TRUE, MVEL.executeExpression(s, vars));
  }
  public void testOrImmediate() {
    Map<String, Object> vars = new HashMap<String, Object>();
    vars.put("a", Unknown.UNKNOWN);
    vars.put("b", Boolean.TRUE);
    assertEquals(Boolean.TRUE, MVEL.eval("a || b", vars));
    assertEquals(Boolean.TRUE, MVEL.eval("b || a", vars));
    vars.put("b", Boolean.FALSE);
    assertSame(Unknown.UNKNOWN, MVEL.eval("a || b", vars));
    assertSame(Unknown.UNKNOWN, MVEL.eval("b || a", vars));
  }
  public void testAndImmediate() {
    Map<String, Object> vars = new HashMap<String, Object>();
    vars.put("a", Boolean.FALSE);
    vars.put("b", Boolean.TRUE);
    assertEquals(Boolean.FALSE, MVEL.eval("a && b", vars));
    assertEquals(Boolean.FALSE, MVEL.eval("b && a", vars));
    vars.put("a", Boolean.TRUE);
    assertEquals(Boolean.TRUE, MVEL.eval("a && b", vars));
    assertEquals(Boolean.TRUE, MVEL.eval("b && a", vars));
    vars.put("a", Unknown.UNKNOWN);
    assertEquals(Unknown.UNKNOWN, MVEL.eval("a && b", vars));
    assertEquals(Unknown.UNKNOWN, MVEL.eval("b && a", vars));
  }
  public void testWithSetCompiled() {
    Map<String, Object> vars = new HashMap<String, Object>();
    SetWithUnknown setWithUnknown = new SetWithUnknown("a");
    vars.put("s", setWithUnknown);
    assertEquals(Boolean.TRUE, MVEL.eval("s.contains('a') || s.contains('b')", vars));
    UnknownValue unknown = (UnknownValue)MVEL.eval("s.contains('a') && s.contains('b')", vars);
    assertEquals("b", unknown.getMissing());
    setWithUnknown.setValue("b", false);
    assertEquals(Boolean.FALSE, MVEL.eval("s.contains('a') && s.contains('b')", vars));
  }
  public void testWithSetImmediate() {
    Map<String, Object> vars = new HashMap<String, Object>();
    SetWithUnknown setWithUnknown = new SetWithUnknown("a");
    vars.put("s", setWithUnknown);
    assertEquals(Boolean.TRUE, MVEL.executeExpression(MVEL.compileExpression("s.contains('a') || s.contains('b')"), vars));
    UnknownValue unknown = (UnknownValue)MVEL.executeExpression(MVEL.compileExpression("s.contains('a') && s.contains('b')"), vars);
    assertEquals("b", unknown.getMissing());
    setWithUnknown.setValue("b", false);
    assertEquals(Boolean.FALSE, MVEL.executeExpression(MVEL.compileExpression("s.contains('a') && s.contains('b')"), vars));
  }
  public void testAddImmediate() {
    Map<String, Object> vars = new HashMap<String, Object>();
    vars.put("a", Unknown.UNKNOWN);
    vars.put("b", Integer.valueOf(3));
    vars.put("c", Integer.valueOf(4));
    assertSame(Unknown.UNKNOWN, MVEL.eval("a + b", vars));
    assertSame(Unknown.UNKNOWN, MVEL.eval("b + a", vars));
    assertSame(Unknown.UNKNOWN, MVEL.eval("c + b + a", vars));
    assertEquals(Integer.valueOf(7), MVEL.eval("c + b", vars));
  }
  public void testIntAddCompiled() {
    Map<String, Object> vars = new HashMap<String, Object>();
    vars.put("a", Unknown.UNKNOWN);
    vars.put("b", Integer.valueOf(3));
    vars.put("c", Integer.valueOf(4));
    ParserContext ctx = new ParserContext();
    HashMap<String, Class> types = new HashMap<String, Class>();
    types.put("a", Integer.class);
    types.put("b", Integer.class);
    types.put("c", Integer.class);
    ctx.setVariables(types);
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(MVEL.compileExpression("a + b", ctx), vars));
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(MVEL.compileExpression("b + a", ctx), vars));
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(MVEL.compileExpression("c + b + a", ctx), vars));
    assertEquals(Integer.valueOf(7), MVEL.executeExpression(MVEL.compileExpression("c + b", ctx), vars));
  }
  public void testSubImmediate() {
    Map<String, Object> vars = new HashMap<String, Object>();
    vars.put("a", Unknown.UNKNOWN);
    vars.put("b", Integer.valueOf(3));
    vars.put("c", Integer.valueOf(4));
    assertSame(Unknown.UNKNOWN, MVEL.eval("a - b", vars));
    assertSame(Unknown.UNKNOWN, MVEL.eval("b - a", vars));
    assertSame(Unknown.UNKNOWN, MVEL.eval("c - b - a", vars));
    assertEquals(Integer.valueOf(1), MVEL.eval("c - b", vars));
  }
  public void testIntSubCompiled() {
    Map<String, Object> vars = new HashMap<String, Object>();
    vars.put("a", Unknown.UNKNOWN);
    vars.put("b", Integer.valueOf(3));
    vars.put("c", Integer.valueOf(4));
    ParserContext ctx = new ParserContext();
    HashMap<String, Class> types = new HashMap<String, Class>();
    types.put("a", Integer.class);
    types.put("b", Integer.class);
    types.put("c", Integer.class);
    ctx.setVariables(types);
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(MVEL.compileExpression("a - b", ctx), vars));
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(MVEL.compileExpression("b - a", ctx), vars));
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(MVEL.compileExpression("c - b - a", ctx), vars));
    assertEquals(Integer.valueOf(1), MVEL.executeExpression(MVEL.compileExpression("c - b", ctx), vars));
  }
  public void testIntMultCompiled() {
    Map<String, Object> vars = new HashMap<String, Object>();
    vars.put("a", Unknown.UNKNOWN);
    vars.put("b", Integer.valueOf(3));
    vars.put("c", Integer.valueOf(4));
    ParserContext ctx = new ParserContext();
    HashMap<String, Class> types = new HashMap<String, Class>();
    types.put("a", Integer.class);
    types.put("b", Integer.class);
    types.put("c", Integer.class);
    ctx.setVariables(types);
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(MVEL.compileExpression("a * b", ctx), vars));
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(MVEL.compileExpression("b * a", ctx), vars));
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(MVEL.compileExpression("c * b * a", ctx), vars));
    assertEquals(Integer.valueOf(12), MVEL.executeExpression(MVEL.compileExpression("c * b", ctx), vars));
  }
  public void testDivImmediate() {
    Map<String, Object> vars = new HashMap<String, Object>();
    vars.put("a", Unknown.UNKNOWN);
    vars.put("b", Integer.valueOf(3));
    vars.put("c", Integer.valueOf(12));
    assertSame(Unknown.UNKNOWN, MVEL.eval("a / b", vars));
    assertSame(Unknown.UNKNOWN, MVEL.eval("b / a", vars));
    assertSame(Unknown.UNKNOWN, MVEL.eval("c / b / a", vars));
    assertEquals(Double.valueOf(4), MVEL.eval("c / b", vars));
  }
  public void testIntDivCompiled() {
    ParserContext ctx = new ParserContext();
    Map<String, Object> vars = new HashMap<String, Object>();
    vars.put("a", Unknown.UNKNOWN);
    vars.put("b", Integer.valueOf(3));
    vars.put("c", Integer.valueOf(12));
    HashMap<String, Class> types = new HashMap<String, Class>();
    types.put("a", Integer.class);
    types.put("b", Integer.class);
    types.put("c", Integer.class);
    ctx.setVariables(types);
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(MVEL.compileExpression("a / b", ctx), vars));
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(MVEL.compileExpression("b / a", ctx), vars));
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(MVEL.compileExpression("c / b / a", ctx), vars));
    assertEquals(Integer.valueOf(4), MVEL.executeExpression(MVEL.compileExpression("c / b", ctx), vars));
  }
  public void testDoubleMultImmediate() {
    Map<String, Object> vars = new HashMap<String, Object>();
    vars.put("a", Unknown.UNKNOWN);
    vars.put("b", Double.valueOf(3));
    vars.put("c", Double.valueOf(4));
    assertSame(Unknown.UNKNOWN, MVEL.eval("a * b", vars));
    assertSame(Unknown.UNKNOWN, MVEL.eval("b * a", vars));
    assertSame(Unknown.UNKNOWN, MVEL.eval("c * b * a", vars));
    assertEquals(Double.valueOf(12), MVEL.eval("c * b", vars));
  }
  public void testDoubleMultCompiled() {
    Map<String, Object> vars = new HashMap<String, Object>();
    vars.put("a", Unknown.UNKNOWN);
    vars.put("b", Double.valueOf(3));
    vars.put("c", Double.valueOf(4));
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(MVEL.compileExpression("a * b"), vars));
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(MVEL.compileExpression("b * a"), vars));
    assertSame(Unknown.UNKNOWN, MVEL.executeExpression(MVEL.compileExpression("c * b * a"), vars));
    assertEquals(Double.valueOf(12), MVEL.executeExpression(MVEL.compileExpression("c * b"), vars));
  }
  public void testContainsImmediate() {
    Map<String, Object> vars = new HashMap<String, Object>();
    SetWithUnknown setWithUnknown = new SetWithUnknown("a");
    vars.put("s", setWithUnknown);
    assertEquals(Boolean.TRUE, MVEL.eval("s contains 'a' || s contains 'b'", vars));
    UnknownValue unknown = (UnknownValue)MVEL.eval("s contains 'a' && s contains 'b'", vars);
    assertEquals("b", unknown.getMissing());
    setWithUnknown.setValue("b", false);
    assertEquals(Boolean.FALSE, MVEL.eval("s contains 'a' && s contains 'b'", vars));
  }
  public void testContainsCompiled() {
    Map<String, Object> vars = new HashMap<String, Object>();
    SetWithUnknown setWithUnknown = new SetWithUnknown("a");
    vars.put("s", setWithUnknown);
    assertEquals(Boolean.TRUE, MVEL.eval("s contains 'a' || s contains 'b'", vars));
    UnknownValue unknown = (UnknownValue)MVEL.executeExpression(MVEL.compileExpression("s contains 'a' && s contains 'b'"), vars);
    assertEquals("b", unknown.getMissing());
    setWithUnknown.setValue("b", false);
    assertEquals(Boolean.FALSE, MVEL.executeExpression(MVEL.compileExpression("s contains 'a' && s contains 'b'"), vars));
  }
}
