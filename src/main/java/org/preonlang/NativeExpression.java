package org.preonlang;

import java.io.IOException;
import java.io.Writer;

public class NativeExpression extends Expression {
    private final Type type = Type.ANY;
    private final String code = "";

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void writeJava(Writer writer) throws IOException {
        writer.write("(" + code + ")");
    }
}
