package org.preonlang;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileVisitor extends PreonParserBaseVisitor<File> {
    @Override
    public File visitFile(PreonParser.FileContext ctx) {
        FunctionVisitor functionVisitor = new FunctionVisitor();
        Stream<Function> functions = ctx
            .functionDefinition()
            .stream()
            .map(function -> function.accept(functionVisitor));

        OperatorVisitor operatorVisitor = new OperatorVisitor();
        Stream<Function> operators = ctx
            .operatorDefinition()
            .stream()
            .map(operator -> operator.accept(operatorVisitor));

        return new File(Stream.concat(functions, operators).collect(Collectors.toList()));
    }
}
