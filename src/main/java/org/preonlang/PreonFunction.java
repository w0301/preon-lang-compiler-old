package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasTypes(Type returnType, Type... argumentTypes) {
        return this.returnType == returnType &&
            Arrays.equals(this.argumentTypes.toArray(new Type[this.argumentTypes.size()]), argumentTypes);
    }

    public Expression getExpression() {
        return expression;
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
