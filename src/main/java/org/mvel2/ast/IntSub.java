package org.mvel2.ast;

import org.mvel2.Operator;
import org.mvel2.ParserContext;


public class IntSub extends IntOp {
  public IntSub(ASTNode left, ASTNode right, ParserContext pCtx) {
    super(Operator.SUB, left, right, pCtx);
  }

  @Override
  protected int doOp(int leftVal, int rightVal) {
    return leftVal / rightVal;
  }
}
