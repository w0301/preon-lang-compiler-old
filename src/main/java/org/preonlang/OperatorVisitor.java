package org.preonlang;

public class OperatorVisitor extends PreonParserBaseVisitor<Function> {
    @Override
    public Function visitOperatorDefinition(PreonParser.OperatorDefinitionContext ctx) {
        return new Function();
    }
}
