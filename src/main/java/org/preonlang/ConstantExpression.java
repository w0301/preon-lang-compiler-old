package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class ConstantExpression extends PreonExpression {
    private final Type type;
    private final String value;

    public ConstantExpression(Type type, String value) {
        super(new ArrayList<ArgumentIdentifier>());
        this.type = type;
        this.value = value;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void writeJava(Writer writer) throws IOException {
        if (type != Type.BOOL) writer.write(value);
        else writer.write("True".equals(value) ? "true" : "false");
    }
}