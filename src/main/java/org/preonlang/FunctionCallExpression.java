package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class FunctionCallExpression extends PreonExpression {
    private final String name;
    private final List<ArgumentExpression> arguments;

    public FunctionCallExpression(String name, List<ArgumentExpression> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    public Type getType() {
        // TODO
        return Type.ANY;
    }

    @Override
    public void writeJava(Writer writer) throws IOException {
        writer.write(name + "(");
        for (ArgumentExpression arg : arguments) {
            arg.writeJava(writer);
            if (arg != arguments.get(arguments.size() -1)) writer.write(",");
        }
        writer.write(")");
    }
}
