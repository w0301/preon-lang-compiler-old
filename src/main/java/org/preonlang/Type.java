package org.preonlang;

import java.io.IOException;
import java.io.Writer;

public enum Type {
    BOOL("Bool", "boolean"),
    INT("Int", "int"),
    FLOAT("Float", "double"),
    CHAR("Char", "char"),
    STRING("String", "String"),
    ANY(null, null);

    private final String name;
    private final String javaCode;

    Type(String name, String javaCode) {
        this.name = name;
        this.javaCode = javaCode;
    }

    public void writeJava(Writer writer) throws IOException {
        if (javaCode == null) throw new RuntimeException("Cannot use specified type for Java.");
        writer.write(javaCode);
    }

    public static Type fromName(String name) {
        for (Type type : values()) {
            if (type.name != null && type.name.equals(name)) return type;
        }
        return Type.ANY;
    }
}
