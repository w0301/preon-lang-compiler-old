package org.preonlang;

import java.util.List;

public class Function {
    private final FunctionIdentifier identifier;
    private final List<ArgumentIdentifier> arguments;
    private final Expression expression;

    public Function(FunctionIdentifier identifier, List<ArgumentIdentifier> arguments, Expression expression) {
        this.identifier = identifier;
        this.arguments = arguments;
        this.expression = expression;
    }

    public FunctionIdentifier getIdentifier() {
        return identifier;
    }

    public List<ArgumentIdentifier> getArguments() {
        return arguments;
    }

    public Type getArgumentType(ArgumentIdentifier argument) {
        // TODO : get type from expression (or cache hashmap)
        return Type.ANY;
    }

    public Type getReturnType() {
        return expression.getType();
    }

    public Expression getExpression() {
        return expression;
    }
}
