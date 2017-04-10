package org.preonlang;

import java.io.IOException;
import java.io.Writer;

public abstract class Expression {
    public abstract Type getType();
    public abstract void writeJava(Writer writer) throws IOException;
}
