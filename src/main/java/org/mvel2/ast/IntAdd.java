package org.mvel2.ast;

import org.mvel2.Operator;
import org.mvel2.ParserContext;

public class IntAdd extends IntOp {
  public IntAdd(ASTNode left, ASTNode right, ParserContext pCtx) {
    super(Operator.ADD, left, right, pCtx);
  }

  @Override
  protected int doOp(int leftVal, int rightVal) {
    return leftVal + rightVal;
  }
}
