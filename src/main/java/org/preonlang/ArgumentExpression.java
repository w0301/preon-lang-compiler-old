package org.preonlang;

import java.io.IOException;
import java.io.Writer;

public class ArgumentExpression extends PreonExpression {
    private final String name;

    public ArgumentExpression(String name) {
        this.name = name;
    }

    @Override
    public Type getType() {
        // TODO
        return Type.ANY;
    }

    @Override
    public void writeJava(Writer writer) throws IOException {
        writer.write(name);
    }
}
