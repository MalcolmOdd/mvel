package org.mvel2.tests.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.mvel2.MVEL;
import org.mvel2.Unknown;

import junit.framework.TestCase;

public class UnknownTests extends TestCase {
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
}
