package org.preonlang;

import java.util.List;

public class Function {
    private final String name;
    private final List<String> arguments;
    private final Expression expression;

    public Function(String name, List<String> arguments, Expression expression) {
        this.name = name;
        this.arguments = arguments;
        this.expression = expression;
    }

    public String getPreonName() {
        return name;
    }

    public String getTargetName() {
        // TODO : change name if operator symbols are in
        return name;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public Type getArgumentType(String argument) {
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
