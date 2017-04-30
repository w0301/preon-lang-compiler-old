package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.ArrayList;

public class ArgumentExpression extends Expression {
    private final String name;

    public ArgumentExpression(String name) {
        this.name = name;
    }

    @Override
    public List<Expression> getSubExpressions() {
        return new ArrayList<Expression>();
    }

    @Override
    public void writeJava(Writer writer) throws IOException {
        writer.write(name);
    }
}
