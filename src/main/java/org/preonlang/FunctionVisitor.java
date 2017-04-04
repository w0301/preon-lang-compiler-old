package org.preonlang;

public class FunctionVisitor extends PreonParserBaseVisitor<Function> {
    @Override
    public Function visitFunctionDefinition(PreonParser.FunctionDefinitionContext ctx) {
        return new Function();
    }
}
