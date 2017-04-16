package org.preonlang.antlr;

import org.preonlang.*;

public class ExpressionVisitor extends PreonParserBaseVisitor<Expression> {
    @Override
    public Expression visitExpression(PreonParser.ExpressionContext ctx) {
        return null;
    }
}
