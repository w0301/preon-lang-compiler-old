package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public abstract class Function {
    public abstract String getName();
    public abstract Type getReturnType();

    public abstract int getArgumentsCount();
    public abstract List<String> getArgumentNames();
    public abstract Type getArgumentType(String name);

    public abstract void writeJava(Writer writer) throws IOException;
}
