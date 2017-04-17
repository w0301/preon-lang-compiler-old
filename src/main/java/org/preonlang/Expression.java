package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public abstract class Expression {
    public abstract void writeJava(Writer writer) throws IOException;
}
