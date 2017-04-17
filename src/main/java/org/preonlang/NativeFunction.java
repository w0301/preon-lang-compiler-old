package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NativeFunction extends Function {
    private final String name;
    private final Type returnType;
    private final List<Type> argumentTypes;

    private final String javaCode;

    private static final Map<String, NativeFunction> nativeFunctions;

    static {
        NativeFunction[] functions = new NativeFunction[] {
            new NativeFunction("nativeNumbersAdd", "arg0 + arg1", Type.INT, Type.INT, Type.INT)
        };
        nativeFunctions = Stream.of(functions).collect(Collectors.toMap(f -> f.getName(), f -> f));
    }

    public static Collection<NativeFunction> getNativeFunctions() {
        return nativeFunctions.values();
    }

    public static NativeFunction getNativeFunction(String name) {
        return nativeFunctions.get(name);
    }

    public NativeFunction(String name, String javaCode, Type returnType, Type... argumentTypes) {
        this.name = name;
        this.javaCode = javaCode;
        this.returnType = returnType;
        this.argumentTypes = Arrays.asList(argumentTypes);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Type getReturnType() {
        return returnType;
    }

    @Override
    public int getArgumentsCount() {
        return argumentTypes.size();
    }

    @Override
    public List<String> getArgumentNames() {
        return IntStream.range(0, getArgumentsCount())
                        .mapToObj(i -> "arg" + i)
                        .collect(Collectors.toList());
    }

    @Override
    public Type getArgumentType(String name) {
        return argumentTypes.get(Integer.parseInt(name.substring(3)));
    }

    @Override
    public void writeJava(Writer writer) throws IOException {
        writer.write("\tpublic static ");
        getReturnType().writeJava(writer);
        writer.write(" " + getName() + "(");
        for (int i = 0; i < getArgumentsCount(); i++) {
            final String name = "arg" + i;
            getArgumentType(name).writeJava(writer);
            writer.write(" " + name);
            if (i != getArgumentsCount() - 1) writer.write(", ");
        }
        writer.write(") {\n\t\treturn " + javaCode + ";\n\t}");
    }
}
