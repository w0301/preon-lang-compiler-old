package org.preonlang.transform;

import java.util.Collections;
import org.preonlang.FunctionCallExpression;
import org.preonlang.ArgumentExpression;
import org.preonlang.Expression;
import org.preonlang.PreonFunction;

/**
 * This transformer change any expressions that might be function calls
 * to function calls if there is no argument with such name defined is current
 * function.
 */
public class ArgumentToFunctionCallTransformer extends ProgramTreeTransformer {
    @Override
    protected Expression transformArgumentExpression(ArgumentExpression expression, TransformContext context) {
        final PreonFunction function = context.getParent(PreonFunction.class);
        if (function != null && !function.getArgumentNames().contains(expression.getName())) {
            return new FunctionCallExpression(expression.getName(), Collections.emptyList());
        }
        return super.transformArgumentExpression(expression, context);
    }
}
