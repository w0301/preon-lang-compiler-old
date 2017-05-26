package org.preonlang;

import java.io.IOException;
import java.io.Writer;

public class Type {
    public static final Type BOOL = new Type("Bool", "boolean");
    public static final Type INT = new Type("Int", "int");
    public static final Type FLOAT = new Type("Float", "double");
    public static final Type CHAR = new Type("Char", "char");
    public static final Type STRING = new Type("String", "String");
    public static final Type ANY = new Type(null, null);

    public static final Type[] PRIMITIVE_TYPES = new Type[] {
        BOOL, INT, FLOAT, CHAR, STRING
    };

    private final String name;
    private final String javaCode;

    private Type(String name, String javaCode) {
        this.name = name;
        this.javaCode = javaCode;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == getClass() &&
            ((name != null && name.equals(((Type)obj).getName())) ||
                (name == null && name == ((Type)obj).getName()));
    }

    public String getName() {
        return name;
    }

    public void writeJava(Writer writer) throws IOException {
        if (javaCode == null) throw new RuntimeException("Cannot use specified type for Java.");
        writer.write(javaCode);
    }

    public static Type fromName(String name) {
        for (Type type : PRIMITIVE_TYPES) {
            if (type.name != null && type.name.equals(name)) return type;
        }
        return Type.ANY;
    }
}
