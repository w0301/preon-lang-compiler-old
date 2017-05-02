package org.preonlang.transform;

import java.util.stream.Collectors;
import org.preonlang.ArgumentExpression;
import org.preonlang.ConditionExpression;
import org.preonlang.ConstantExpression;
import org.preonlang.Expression;
import org.preonlang.File;
import org.preonlang.Function;
import org.preonlang.FunctionCallExpression;
import org.preonlang.NativeFunction;
import org.preonlang.PreonFunction;

public abstract class ProgramTreeTransformer {
    public File transform(File file) {
        return new File(
            file.getFunctions().stream()
                .map(f -> transformFunction(f, new TransformContext(file)))
                .collect(Collectors.toList())
        );
    }

    protected Function transformFunction(Function function, TransformContext context) {
        if (function instanceof PreonFunction) return transformPreonFunction((PreonFunction) function, context);
        if (function instanceof NativeFunction) return transformNativeFunction((NativeFunction) function, context);

        throw new RuntimeException("Unknown function type: " + function.getClass().getName());
    }

    protected PreonFunction transformPreonFunction(PreonFunction function, TransformContext context) {
        return new PreonFunction(
            function.getName(), function.getArgumentNames(),
            function.getReturnType(), function.getArgumentTypes(),
            transformExpression(function.getExpression(), context.withNewParent(function))
        );
    }

    protected NativeFunction transformNativeFunction(NativeFunction function, TransformContext context) {
        // we want to reuse these instances by default
        return function;
    }

    protected Expression transformExpression(Expression expression, TransformContext context) {
        if (expression instanceof FunctionCallExpression) return transformFunctionCallExpression((FunctionCallExpression) expression, context);
        if (expression instanceof ConditionExpression) return transformConditionExpression((ConditionExpression) expression, context);
        if (expression instanceof ConstantExpression) return transformConstantExpression((ConstantExpression) expression, context);
        if (expression instanceof ArgumentExpression) return transformArgumentExpression((ArgumentExpression) expression, context);

        throw new RuntimeException("Unknown expression type: " + expression.getClass().getName());
    }

    protected Expression transformFunctionCallExpression(FunctionCallExpression expression, TransformContext context) {
        return new FunctionCallExpression(
            expression.getName(),
            expression.getArguments().stream().map(a -> transformExpression(a, context.withNewParent(expression))).collect(Collectors.toList())
        );
    }

    protected Expression transformConditionExpression(ConditionExpression expression, TransformContext context) {
        return new ConditionExpression(
            transformExpression(expression.getConditionExpression(), context.withNewParent(expression)),
            transformExpression(expression.getThenExpression(), context.withNewParent(expression)),
            transformExpression(expression.getElseExpression(), context.withNewParent(expression))
        );
    }

    protected Expression transformConstantExpression(ConstantExpression expression, TransformContext context) {
        return new ConstantExpression(expression.getType(), expression.getValue());
    }

    protected Expression transformArgumentExpression(ArgumentExpression expression, TransformContext context) {
        return new ArgumentExpression(expression.getName());
    }
}
