package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.preonlang.util.IdentifierUtils;

public class NativeFunction extends Function {
    public static class Signature {
        private final String javaCode;
        private final Type returnType;
        private final List<Type> argumentTypes;

        public Signature(String javaCode, Type returnType, Type... argumentTypes) {
            this.javaCode = javaCode;
            this.returnType = returnType;
            this.argumentTypes = Arrays.asList(argumentTypes);
        }

        public boolean hasTypes(List<Type> argumentTypes) {
            return this.argumentTypes.equals(argumentTypes);
        }

        public String getJavaCode() {
            return javaCode;
        }

        public Type getReturnType() {
            return returnType;
        }

        public List<Type> getArgumentTypes() {
            return argumentTypes;
        }
    }

    private final String name;
    private final List<Signature> signatures;

    private static final Map<String, NativeFunction> nativeFunctions;

    static {
        NativeFunction[] functions = new NativeFunction[] {
            new NativeFunction(
                "+",
                new Signature("arg0 + arg1", Type.INT, Type.INT, Type.INT),
                new Signature("(double)arg0 + arg1", Type.FLOAT, Type.INT, Type.FLOAT),
                new Signature("arg0 + (double)arg1", Type.FLOAT, Type.FLOAT, Type.INT),
                new Signature("arg0 + arg1", Type.FLOAT, Type.FLOAT, Type.FLOAT)
            ),
            new NativeFunction(
                "-",
                new Signature("arg0 - arg1", Type.INT, Type.INT, Type.INT),
                new Signature("(double)arg0 - arg1", Type.FLOAT, Type.INT, Type.FLOAT),
                new Signature("arg0 - (double)arg1", Type.FLOAT, Type.FLOAT, Type.INT),
                new Signature("arg0 - arg1", Type.FLOAT, Type.FLOAT, Type.FLOAT)
            ),
            new NativeFunction(
                "/",
                new Signature("arg0 / arg1", Type.INT, Type.INT, Type.INT),
                new Signature("(double)arg0 / arg1", Type.FLOAT, Type.INT, Type.FLOAT),
                new Signature("arg0 / (double)arg1", Type.FLOAT, Type.FLOAT, Type.INT),
                new Signature("arg0 / arg1", Type.FLOAT, Type.FLOAT, Type.FLOAT)
            ),
            new NativeFunction(
                "*",
                new Signature("arg0 * arg1", Type.INT, Type.INT, Type.INT),
                new Signature("(double)arg0 * arg1", Type.FLOAT, Type.INT, Type.FLOAT),
                new Signature("arg0 * (double)arg1", Type.FLOAT, Type.FLOAT, Type.INT),
                new Signature("arg0 * arg1", Type.FLOAT, Type.FLOAT, Type.FLOAT)
            ),
            new NativeFunction(
                "&&",
                new Signature("arg0 && arg1", Type.BOOL, Type.BOOL, Type.BOOL)
            ),
            new NativeFunction(
                "||",
                new Signature("arg0 || arg1", Type.BOOL, Type.BOOL, Type.BOOL)
            ),
            new NativeFunction(
                "==",
                new Signature("arg0 == arg1", Type.BOOL, Type.INT, Type.INT),
                new Signature("arg0 == arg1", Type.BOOL, Type.FLOAT, Type.FLOAT),
                new Signature("arg0 == arg1", Type.BOOL, Type.BOOL, Type.BOOL),
                new Signature("arg0 == arg1", Type.BOOL, Type.CHAR, Type.CHAR),
                new Signature("arg0.equals(arg1)", Type.BOOL, Type.STRING, Type.STRING)
            ),
            new NativeFunction(
                "<",
                new Signature("arg0 < arg1", Type.BOOL, Type.INT, Type.INT),
                new Signature("arg0 < arg1", Type.BOOL, Type.INT, Type.FLOAT),
                new Signature("arg0 < arg1", Type.BOOL, Type.FLOAT, Type.INT),
                new Signature("arg0 < arg1", Type.BOOL, Type.FLOAT, Type.FLOAT)
            ),
            new NativeFunction(
                "++",
                new Signature("arg0 + arg1", Type.STRING, Type.STRING, Type.STRING),
                new Signature("arg0 + arg1", Type.STRING, Type.STRING, Type.INT),
                new Signature("arg0 + arg1", Type.STRING, Type.STRING, Type.FLOAT),
                new Signature("arg0 + (arg1 ? \"True\" : \"False\")", Type.STRING, Type.STRING, Type.BOOL)
            ),
            new NativeFunction(
                "toInt",
                new Signature("(int)arg0", Type.INT, Type.FLOAT)
            ),
            new NativeFunction(
                "toFloat",
                new Signature("(double)arg0", Type.FLOAT, Type.INT)
            )
        };
        nativeFunctions = Stream.of(functions).collect(Collectors.toMap(f -> f.getName(), f -> f));
    }

    public static Collection<NativeFunction> getNativeFunctions() {
        return nativeFunctions.values();
    }

    public static NativeFunction getNativeFunction(String name) {
        return nativeFunctions.get(name);
    }

    public NativeFunction(String name, Signature... signatures) {
        this.name = name;
        this.signatures = Arrays.asList(signatures);
    }

    public List<Signature> getSignatures() {
        return signatures;
    }

    public Type getReturnType(List<Type> types) {
        for (Signature sig : signatures) {
            if (sig.hasTypes(types)) return sig.getReturnType();
        }
        return Type.ANY;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void writeJava(Writer writer) throws IOException {
        for (Signature signature : signatures) {
            writer.write("\tpublic static ");
            signature.getReturnType().writeJava(writer);
            writer.write(" " + IdentifierUtils.getTargetName(getName()) + "(");

            List<Type> argumentTypes = signature.getArgumentTypes();
            for (int i = 0; i < argumentTypes.size(); i++) {
                final Type type = argumentTypes.get(i);
                final String name = "arg" + i;

                writer.write("final ");
                type.writeJava(writer);
                writer.write(" " + name);
                if (i != argumentTypes.size() - 1) writer.write(", ");
            }
            writer.write(") {\n\t\treturn " + signature.getJavaCode() + ";\n\t}\n");
        }
    }
}
