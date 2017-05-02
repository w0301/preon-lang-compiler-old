package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public abstract class ProgramNode {
    public List<? extends ProgramNode> getSubNodes() {
        return new ArrayList<>();
    }

    public abstract void writeJava(Writer writer) throws IOException;
}
