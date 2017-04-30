package org.preonlang.antlr;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.preonlang.*;
import org.preonlang.NativeFunction;

public class FileVisitor extends PreonParserBaseVisitor<File> {
    @Override
    public File visitFile(PreonParser.FileContext ctx) {
        final FunctionVisitor functionVisitor = new FunctionVisitor();
        final Stream<Function> nativeFunctions = NativeFunction.getNativeFunctions()
            .stream()
            .map(f -> (Function)f);
        final Stream<Function> functions = ctx
            .functionDefinition()
            .stream()
            .map(function -> function.accept(functionVisitor));
        final Stream<Function> operators = ctx
            .operatorDefinition()
            .stream()
            .map(operator -> operator.accept(functionVisitor));

        return new File(Stream.concat(Stream.concat(nativeFunctions, functions), operators).collect(Collectors.toList()));
    }
}
