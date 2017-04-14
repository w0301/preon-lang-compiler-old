package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public abstract class Expression {
    private final List<ArgumentIdentifier> arguments;

    public Expression(List<ArgumentIdentifier> arguments) {
        this.arguments = arguments;
    }

    public List<ArgumentIdentifier> getArguments() {
        return arguments;
    }

    public abstract Type getType();
    public abstract void writeJava(Writer writer) throws IOException;
}
