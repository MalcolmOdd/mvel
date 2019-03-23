package org.mvel2.ast;

import org.mvel2.Operator;
import org.mvel2.ParserContext;
import org.mvel2.Unknown;
import org.mvel2.integration.VariableResolverFactory;

public abstract class IntOp extends BinaryOperation implements IntOptimized {

  public IntOp(int op, ASTNode left, ASTNode right, ParserContext pCtx) {
    super(op, pCtx);
    this.left = left;
    this.right = right;
  }

  @Override
  public Object getReducedValueAccelerated(Object ctx, Object thisValue, VariableResolverFactory factory) {
    return perform(left.getReducedValueAccelerated(ctx, thisValue, factory), 
        right.getReducedValueAccelerated(ctx, thisValue, factory));
  }

  @Override
  public Object getReducedValue(Object ctx, Object thisValue, VariableResolverFactory factory) {
    return perform(left.getReducedValue(ctx, thisValue, factory), 
        right.getReducedValue(ctx, thisValue, factory));
  }
  
  protected abstract int doOp(int leftVal, int rightVal);

  private Object perform(Object leftVal, Object rightVal) {
    if (leftVal instanceof Unknown) return leftVal;
    if (rightVal instanceof Unknown) return rightVal;
    return doOp(((Integer) leftVal).intValue(), ((Integer) rightVal).intValue());
  }

  @Override
  public Class getEgressType() {
    return Integer.class;
  }
}