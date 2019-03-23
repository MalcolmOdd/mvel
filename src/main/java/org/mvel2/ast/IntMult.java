package org.mvel2.ast;

import org.mvel2.Operator;
import org.mvel2.ParserContext;


public class IntMult extends IntOp implements IntOptimized {
  public IntMult(ASTNode left, ASTNode right, ParserContext pCtx) {
    super(Operator.MULT, left, right, pCtx);
  }

  @Override
  protected int doOp(int leftVal, int rightVal) {
    return leftVal * rightVal;
  }
}