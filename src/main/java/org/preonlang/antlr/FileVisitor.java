package org.preonlang.antlr;

import java.util.Map;
import org.preonlang.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileVisitor extends PreonParserBaseVisitor<File> {
    @Override
    public File visitFile(PreonParser.FileContext ctx) {
        FunctionIdentifierVisitor functionIdentifierVisitor = new FunctionIdentifierVisitor();
        Stream<FunctionIdentifier> functionIdentifiers = ctx
            .functionDefinition()
            .stream()
            .map(function -> function.accept(functionIdentifierVisitor));
        Stream<FunctionIdentifier> operatorIdentifiers = ctx
            .operatorDefinition()
            .stream()
            .map(operator -> operator.accept(functionIdentifierVisitor));
        Map<String, FunctionIdentifier> identifiers = Stream.concat(functionIdentifiers, operatorIdentifiers)
            .collect(Collectors.toMap(id -> id.getPreonName(), id -> id));

        FunctionVisitor functionVisitor = new FunctionVisitor(identifiers);
        Stream<Function> functions = ctx
            .functionDefinition()
            .stream()
            .map(function -> function.accept(functionVisitor));
        Stream<Function> operators = ctx
            .operatorDefinition()
            .stream()
            .map(operator -> operator.accept(functionVisitor));

        return new File(Stream.concat(functions, operators).collect(Collectors.toList()));
    }
}
