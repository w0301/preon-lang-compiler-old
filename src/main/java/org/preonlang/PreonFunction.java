package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import org.preonlang.util.IdentifierUtils;

public class PreonFunction extends Function {
    private final String name;
    private final List<String> argumentNames;

    private final Type returnType;
    private final List<Type> argumentTypes;

    private final Expression expression;

    public PreonFunction(String name, List<String> argumentNames, Type returnType,
                         List<Type> argumentTypes, Expression expression) {
        this.name = name;
        this.argumentNames = argumentNames;
        this.returnType = returnType;
        this.argumentTypes = argumentTypes;
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    public Type getReturnType() {
        return returnType;
    }

    public List<Type> getArgumentTypes() {
        return argumentTypes;
    }

    public List<String> getArgumentNames() {
        return argumentNames;
    }

    public Type getArgumentType(String name) {
        final int index = argumentNames.indexOf(name);
        return index < 0 ? Type.ANY : argumentTypes.get(index);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<? extends ProgramNode> getSubNodes() {
        final List<Expression> res = new ArrayList<>();
        res.add(expression);
        return res;
    }

    @Override
    public void writeJava(Writer writer) throws IOException {
        writer.write("\tpublic static ");
        returnType.writeJava(writer);
        writer.write(" " + IdentifierUtils.getTargetName(getName()) + "(");
        for (int i = 0; i < argumentNames.size(); i++) {
            final String name = argumentNames.get(i);
            final Type type = argumentTypes.get(i);

            writer.write("final ");
            type.writeJava(writer);
            writer.write(" " + name);
            if (i != argumentNames.size() - 1) writer.write(", ");
        }
        writer.write(") {\n\t\treturn ");
        getExpression().writeJava(writer);
        writer.write(";\n\t}\n");
    }
}
