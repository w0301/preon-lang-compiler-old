package org.preonlang;

import java.io.IOException;
import java.io.Writer;

public enum Type {
    BOOL, INT, FLOAT, CHAR, STRING, ANY;

    public void writeJava(Writer writer) throws IOException {
        if (this == Type.BOOL) writer.write("boolean");
        else if (this == Type.INT) writer.write("int");
        else if (this == Type.FLOAT) writer.write("double");
        else if (this == Type.CHAR) writer.write("char");
        else if (this == Type.STRING) writer.write("String");
        else throw new RuntimeException("Cannot use specified type for Java.");
    }
}
