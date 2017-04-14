package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NativeExpression extends Expression {
    private final Type type;
    private final List<Type> argumentTypes;
    private final String javaCode;

    public NativeExpression() {
        this("", Type.ANY);
    }

    public NativeExpression(String javaCode, Type returnType, Type... argumentTypes) {
        super(IntStream.range(0, argumentTypes.length).mapToObj(i -> new ArgumentIdentifier("arg" + i)).collect(Collectors.toList()));
        this.javaCode = javaCode;
        this.type = returnType;
        this.argumentTypes = Arrays.asList(argumentTypes);
    }

    @Override
    public Type getType() {
        return type;
    }

    public List<Type> getArgumentTypes() {
        return argumentTypes;
    }

    @Override
    public void writeJava(Writer writer) throws IOException {
        writer.write("(" + javaCode + ")");
    }
}
