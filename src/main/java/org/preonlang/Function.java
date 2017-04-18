package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public abstract class Function {
    public abstract String getName();
    public abstract boolean hasTypes(Type returnType, Type... argumentTypes);

    public abstract void writeJava(Writer writer) throws IOException;
}
