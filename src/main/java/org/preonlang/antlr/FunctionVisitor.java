package org.preonlang.antlr;

import org.preonlang.*;
import java.util.stream.Collectors;

public class FunctionVisitor extends PreonParserBaseVisitor<Function> {
    @Override
    public Function visitFunctionDefinition(PreonParser.FunctionDefinitionContext ctx) {
        return new PreonFunction(
            ctx.functionNameIdentifier().getText(),
            ctx.identifier().stream().map(id -> id.getText()).collect(Collectors.toList()),
            new ExpressionVisitor().visitExpression(ctx.expression())
        );
    }

    @Override
    public Function visitOperatorDefinition(PreonParser.OperatorDefinitionContext ctx) {
        return new PreonFunction(
            ctx.operatorNameIdentifier().getText(),
            ctx.identifier().stream().map(id -> id.getText()).collect(Collectors.toList()),
            new ExpressionVisitor().visitExpression(ctx.expression())
        );
    }
}
