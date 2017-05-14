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
    protected PreonFunction transformPreonFunction(PreonFunction function, TransformContext context) {
        final PreonFunction res = super.transformPreonFunction(function, context);
        if (res.getReturnType() != res.getExpression().getType()) {
            addError(new TransformError("Function '" + function.getName() +
                "' is declared to return '" + res.getReturnType().getName() + "' type but is returning '" +
                res.getExpression().getType().getName() +"' type."));
        }
        return res;
    }

    @Override
    protected Expression transformFunctionCallExpression(FunctionCallExpression expression, TransformContext context) {
        Type calledType = Type.ANY;
        final Function calledFunction = functions.get(expression.getName());
        if (calledFunction == null) {
            addError(new TransformError("Function '" + expression.getName() + "' is not defined."));
            return super.transformFunctionCallExpression(expression, context);
        }

        final List<Expression> arguments = expression.getArguments()
            .stream()
            .map(a -> transformExpression(a, context.withNewParent(expression)))
            .collect(Collectors.toList());

        if (calledFunction instanceof PreonFunction) {
            calledType = ((PreonFunction) calledFunction).getReturnType();
            if (!((PreonFunction) calledFunction).hasArgumentTypes(arguments.stream().map(a -> a.getType()).collect(Collectors.toList()))) {
                addError(new TransformError("Supplied arguments for function '" + expression.getName() + "' have bad types."));
                return super.transformFunctionCallExpression(expression, context);
            }
        }
        else {
            calledType = ((NativeFunction) calledFunction).getReturnType(
                arguments.stream().map(a -> a.getType()).collect(Collectors.toList())
            );
            if (calledType == Type.ANY) {
                addError(new TransformError("Supplied arguments for function '" + expression.getName() + "' have bad types."));
                return super.transformFunctionCallExpression(expression, context);
            }
        }

        return new FunctionCallExpression(calledType, expression.getName(), arguments);
    }

    @Override
    protected Expression transformConditionExpression(ConditionExpression expression, TransformContext context) {
        final Expression condExp = transformExpression(expression.getConditionExpression(), context.withNewParent(expression));
        final Expression thenExp = transformExpression(expression.getThenExpression(), context.withNewParent(expression));
        final Expression elseExp = transformExpression(expression.getElseExpression(), context.withNewParent(expression));

        if (condExp.getType() != Type.BOOL) {
            addError(new TransformError("Supplied expression for if condition is not boolean."));
            return super.transformConditionExpression(expression, context);
        }
        if (thenExp.getType() != elseExp.getType()) {
            addError(new TransformError("Supplied expressions for then and else branches are not of same type."));
            return super.transformConditionExpression(expression, context);
        }

        return new ConditionExpression(condExp, thenExp, elseExp);
    }

    @Override
    protected Expression transformArgumentExpression(ArgumentExpression expression, TransformContext context) {
        final PreonFunction function = context.getParent(PreonFunction.class);
        final Type type = function.getArgumentType(expression.getName());
        if (type == Type.ANY) {
            addError(new TransformError("Not defined argument usage of '" + expression.getName() + "' in function '" + function.getName() + "'."));
            return super.transformArgumentExpression(expression, context);
        }
        return new ArgumentExpression(type, expression.getName());
    }
}
