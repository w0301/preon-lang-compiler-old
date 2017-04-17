package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.preonlang.util.IdentifierUtils;

public class PreonFunction extends Function {
    private final String name;
    private final List<String> argumentNames;
    private final Expression expression;

    private Type returnType;
    private final Map<String, Type> argumentTypes = new HashMap<>();

    public PreonFunction(String name, List<String> argumentNames, Expression expression) {
        this.name = name;
        this.argumentNames = argumentNames;
        this.expression = expression;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Type getReturnType() {
        return returnType;
    }

    @Override
    public int getArgumentsCount() {
        return argumentNames.size();
    }

    @Override
    public List<String> getArgumentNames() {
        return argumentNames;
    }

    @Override
    public Type getArgumentType(String name) {
        return argumentTypes.get(name);
    }

    public Expression getExpression() {
        return expression;
    }

    public void setReturnType(Type type) {
        this.returnType = type;
    }

    public void setArgumentType(String name, Type type) {
        argumentTypes.put(name, type);
    }

    @Override
    public void writeJava(Writer writer) throws IOException {
        writer.write("\tpublic static ");
        getReturnType().writeJava(writer);
        writer.write(" " + IdentifierUtils.getTargetName(getName()) + "(");
        for (String name : argumentNames) {
            getArgumentType(name).writeJava(writer);
            writer.write(" " + name);
            if (name != argumentNames.get(argumentNames.size() - 1)) writer.write(", ");
        }
        writer.write(") {\n\t\treturn ");
        // TODO : write expression here!
        writer.write(";\n\t}");
    }
}
