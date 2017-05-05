package org.preonlang;

import java.io.IOException;
import java.io.Writer;

public class ArgumentExpression extends Expression {
    private final Type type;
    private final String name;

    public ArgumentExpression(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    public ArgumentExpression(String name) {
        this(Type.ANY, name);
    }

    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void writeJava(Writer writer) throws IOException {
        writer.write(name);
    }
}
