package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class ArgumentExpression extends PreonExpression {
    private final ArgumentIdentifier identifier;

    public ArgumentExpression(ArgumentIdentifier identifier) {
        super(new ArrayList<ArgumentIdentifier>());
        this.identifier = identifier;
    }

    @Override
    public Type getType() {
        // TODO
        return Type.ANY;
    }

    @Override
    public void writeJava(Writer writer) throws IOException {
        identifier.writeJava(writer);
    }
}
