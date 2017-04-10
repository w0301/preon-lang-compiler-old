package org.preonlang;

import java.util.stream.Collectors;

public class FunctionVisitor extends PreonParserBaseVisitor<Function> {
    @Override
    public Function visitFunctionDefinition(PreonParser.FunctionDefinitionContext ctx) {
        return new Function(
            ctx.functionNameIdentifier().getText(),
            ctx.identifier().stream().map(id -> id.getText()).collect(Collectors.toList()),

            // TODO : find native expression by name and use that one
            ctx.nativeDeclaration() != null ?
                new NativeExpression() :
                new ExpressionVisitor().visitExpression(ctx.expression())
        );
    }
}
