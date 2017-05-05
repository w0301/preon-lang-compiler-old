package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public abstract class Function extends ProgramNode {
    public abstract String getName();
    public abstract void writeJava(Writer writer) throws IOException;
}
