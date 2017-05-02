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
        private final Type[] argumentTypes;

        public Signature(String javaCode, Type returnType, Type... argumentTypes) {
            this.javaCode = javaCode;
            this.returnType = returnType;
            this.argumentTypes = argumentTypes;
        }

        public boolean hasTypes(Type returnType, Type... argumentTypes) {
            return this.returnType == returnType && Arrays.equals(this.argumentTypes, argumentTypes);
        }

        public String getJavaCode() {
            return javaCode;
        }

        public Type getReturnType() {
            return returnType;
        }

        public List<Type> getArgumentTypes() {
            return Arrays.asList(argumentTypes);
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean hasTypes(Type returnType, Type... argumentTypes) {
        for (Signature signature : signatures) {
            if (signature.hasTypes(returnType, argumentTypes)) return true;
        }
        return false;
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
