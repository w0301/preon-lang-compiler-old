package org.preonlang;

import java.util.stream.Collectors;

public class OperatorVisitor extends PreonParserBaseVisitor<Function> {
    @Override
    public Function visitOperatorDefinition(PreonParser.OperatorDefinitionContext ctx) {
        return new Function(
            ctx.operatorNameIdentifier().getText(),
            ctx.identifier().stream().map(id -> id.getText()).collect(Collectors.toList()),

            // TODO : find native expression by name and use that one
            ctx.nativeDeclaration() != null ?
                new NativeExpression() :
                new ExpressionVisitor().visitExpression(ctx.expression())
        );
    }
}
