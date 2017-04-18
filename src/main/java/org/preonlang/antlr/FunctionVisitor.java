package org.preonlang.antlr;

import java.util.List;
import java.util.stream.Collectors;
import org.preonlang.*;

public class FunctionVisitor extends PreonParserBaseVisitor<Function> {
    @Override
    public Function visitFunctionDefinition(PreonParser.FunctionDefinitionContext ctx) {
        return createFunction(
            ctx.functionNameIdentifier().get(0).getText(),
            ctx.functionNameIdentifier().get(1).getText(),
            ctx.type(), ctx.identifier(), ctx.argumentType(), ctx.expression()
        );
    }

    @Override
    public Function visitOperatorDefinition(PreonParser.OperatorDefinitionContext ctx) {
        return createFunction(
            ctx.operatorNameIdentifier().get(0).getText(),
            ctx.operatorNameIdentifier().get(1).getText(),
            ctx.type(), ctx.identifier(), ctx.argumentType(), ctx.expression()
        );
    }

    private Function createFunction(String name1, String name2, PreonParser.TypeContext type,
                                    List<PreonParser.IdentifierContext> argumentNames,
                                    List<PreonParser.ArgumentTypeContext> argumentTypes,
                                    PreonParser.ExpressionContext expression) {
        if (!name1.equals(name2)) throw new RuntimeException("Missing type annotation for function '" + name2 + "'.");
        return new PreonFunction(
            name1,
            argumentNames.stream().map(id -> id.getText()).collect(Collectors.toList()),
            Type.fromName(type.getText()),
            argumentTypes.stream().map(id -> Type.fromName(id.type().getText())).collect(Collectors.toList()),
            new ExpressionVisitor().visitExpression(expression)
        );
    }
}
