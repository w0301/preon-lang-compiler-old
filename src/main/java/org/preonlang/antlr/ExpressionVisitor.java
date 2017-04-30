package org.preonlang.antlr;

import java.util.stream.Collectors;
import org.preonlang.*;

public class ExpressionVisitor extends PreonParserBaseVisitor<Expression> {
    @Override
    public Expression visitExpression(PreonParser.ExpressionContext ctx) {
        if (ctx.closedExpression() != null) return visitClosedExpression(ctx.closedExpression());
        if (ctx.operatorExpression() != null) return visitOperatorExpression(ctx.operatorExpression());
        if (ctx.functionCallExpression() != null) return visitFunctionCallExpression(ctx.functionCallExpression());

        throw new RuntimeException("Parsing not implemented for given context: " + ctx.getText());
    }

    @Override
    public Expression visitClosedExpression(PreonParser.ClosedExpressionContext ctx) {
        if (ctx.constant() != null) {
            if (ctx.constant().integerValue() != null) return new ConstantExpression(Type.INT, ctx.constant().getText());
            if (ctx.constant().floatValue() != null) return new ConstantExpression(Type.FLOAT, ctx.constant().getText());
            if (ctx.constant().boolValue() != null) return new ConstantExpression(Type.BOOL, ctx.constant().getText());
            if (ctx.constant().charValue() != null) return new ConstantExpression(Type.CHAR, ctx.constant().getText());
            if (ctx.constant().stringValue() != null) return new ConstantExpression(Type.STRING, ctx.constant().getText());
        }
        else if (ctx.identifier() != null) {
            return new ArgumentExpression(ctx.identifier().getText());
        }
        else if (ctx.conditionExpression() != null) {
            return new ConditionExpression(
                visitExpression(ctx.conditionExpression().expression(0)),
                visitExpression(ctx.conditionExpression().expression(1)),
                visitExpression(ctx.conditionExpression().expression(2))
            );
        }
        else if (ctx.expression() != null) {
            return visitExpression(ctx.expression());
        }

        throw new RuntimeException("Parsing not implemented for given context: " + ctx.getText());
    }

    @Override
    public Expression visitOperatorExpression(PreonParser.OperatorExpressionContext ctx) {
        if (ctx.closedExpression() != null) return visitClosedExpression(ctx.closedExpression());

        return new FunctionCallExpression(
            ctx.operator().getText(),
            ctx.operatorExpression().stream().map(o -> visitOperatorExpression(o)).collect(Collectors.toList())
        );
    }

    @Override
    public Expression visitFunctionCallExpression(PreonParser.FunctionCallExpressionContext ctx) {
        return new FunctionCallExpression(
            ctx.identifier().getText(),
            ctx.closedExpression().stream().map(e -> visitClosedExpression(e)).collect(Collectors.toList())
        );
    }
}
