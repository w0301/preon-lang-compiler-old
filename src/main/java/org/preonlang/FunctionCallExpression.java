package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import org.preonlang.util.IdentifierUtils;

public class FunctionCallExpression extends Expression {
    private final String name;
    private final List<Expression> arguments;

    public FunctionCallExpression(String name, List<Expression> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    @Override
    public List<? extends ProgramNode> getSubNodes() {
        return arguments;
    }

    @Override
    public void writeJava(Writer writer) throws IOException {
        writer.write(IdentifierUtils.getTargetName(name));

        writer.write("(");
        for (Expression arg : arguments) {
            arg.writeJava(writer);
            if (arg != arguments.get(arguments.size() - 1)) writer.write(",");
        }
        writer.write(")");
    }
}
