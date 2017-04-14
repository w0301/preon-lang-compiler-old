package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.ArrayList;

public class FunctionCallExpression extends PreonExpression {
    private final FunctionIdentifier identifier;
    private final List<Expression> arguments;

    public FunctionCallExpression(FunctionIdentifier identifier, List<Expression> arguments) {
        super(new ArrayList<ArgumentIdentifier>());
        this.identifier = identifier;
        this.arguments = arguments;
    }

    @Override
    public Type getType() {
        // TODO
        return Type.ANY;
    }

    @Override
    public void writeJava(Writer writer) throws IOException {
        identifier.writeJava(writer);

        writer.write("(");
        for (Expression arg : arguments) {
            arg.writeJava(writer);
            if (arg != arguments.get(arguments.size() - 1)) writer.write(",");
        }
        writer.write(")");
    }
}
