package org.preonlang.transform;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.preonlang.Type;
import org.preonlang.Expression;
import org.preonlang.Function;
import org.preonlang.FunctionCallExpression;
import org.preonlang.ConditionExpression;
import org.preonlang.ArgumentExpression;
import org.preonlang.NativeFunction;
import org.preonlang.PreonFunction;

public class CheckTypesAndNamesTransformer extends ProgramTreeTransformer {
    private final Map<String, Function> functions;

    public CheckTypesAndNamesTransformer(List<Function> functions) {
        this.functions = functions.stream().collect(Collectors.toMap(f -> f.getName(), f -> f));
    }

    @Override
    protected Expression transformFunctionCallExpression(FunctionCallExpression expression, TransformContext context) {
        Type calledType = Type.ANY;
        final Function calledFunction = functions.get(expression.getName());
        // TODO : check not existent function

        final List<Expression> arguments = expression.getArguments()
            .stream()
            .map(a -> transformExpression(a, context.withNewParent(expression)))
            .collect(Collectors.toList());

        if (calledFunction instanceof PreonFunction) {
            calledType = ((PreonFunction) calledFunction).getReturnType();
            // TODO : check types
        }
        else {
            calledType = ((NativeFunction) calledFunction).getReturnType(
                arguments.stream().map(a -> a.getType()).collect(Collectors.toList())
            );
            // TODO : check type == Type.ANY
        }

        return new FunctionCallExpression(calledType, expression.getName(), arguments);
    }

    @Override
    protected Expression transformConditionExpression(ConditionExpression expression, TransformContext context) {
        final Expression condExp = transformExpression(expression.getConditionExpression(), context.withNewParent(expression));
        final Expression thenExp = transformExpression(expression.getThenExpression(), context.withNewParent(expression));
        final Expression elseExp = transformExpression(expression.getElseExpression(), context.withNewParent(expression));
        // TODO : check types

        return new ConditionExpression(condExp, thenExp, elseExp);
    }

    @Override
    protected Expression transformArgumentExpression(ArgumentExpression expression, TransformContext context) {
        final PreonFunction function = context.getParent(PreonFunction.class);

        // TODO : check not existent argument
        return new ArgumentExpression(function.getArgumentType(expression.getName()), expression.getName());
    }
}
