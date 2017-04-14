package org.preonlang;

import java.util.stream.Collectors;

public class FunctionIdentifierVisitor extends PreonParserBaseVisitor<FunctionIdentifier> {
    @Override
    public FunctionIdentifier visitFunctionDefinition(PreonParser.FunctionDefinitionContext ctx) {
        return new FunctionIdentifier(ctx.functionNameIdentifier().getText());
    }

    @Override
    public FunctionIdentifier visitOperatorDefinition(PreonParser.OperatorDefinitionContext ctx) {
        return new FunctionIdentifier(ctx.operatorNameIdentifier().getText());
    }
}
