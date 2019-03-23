package org.mvel2.ast;

import org.mvel2.Operator;
import org.mvel2.ParserContext;


public class IntDiv extends IntOp {
  public IntDiv(ASTNode left, ASTNode right, ParserContext pCtx) {
    super(Operator.DIV, left, right, pCtx);
  }

  @Override
  protected int doOp(int leftVal, int rightVal) {
    return leftVal / rightVal;
  }
}