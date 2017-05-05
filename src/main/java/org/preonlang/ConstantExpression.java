package org.preonlang;

import java.io.IOException;
import java.io.Writer;

public class ConstantExpression extends Expression {
    private final Type type;
    private final String value;

    public ConstantExpression(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void writeJava(Writer writer) throws IOException {
        if (type != Type.BOOL) writer.write(value);
        else writer.write("True".equals(value) ? "true" : "false");
    }
}
