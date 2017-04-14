package org.preonlang;

import java.util.Map;
import java.util.stream.Collectors;

public class FunctionVisitor extends PreonParserBaseVisitor<Function> {
    private final Map<String, FunctionIdentifier> identifiers;

    public FunctionVisitor(Map<String, FunctionIdentifier> identifiers) {
        this.identifiers = identifiers;
    }

    @Override
    public Function visitFunctionDefinition(PreonParser.FunctionDefinitionContext ctx) {
        return new Function(
            getIdentifier(ctx.functionNameIdentifier().getText()),
            ctx.identifier().stream().map(id -> new ArgumentIdentifier(id.getText())).collect(Collectors.toList()),

            // TODO : find native expression by name and use that one
            ctx.nativeDeclaration() != null ?
                new NativeExpression() :
                new ExpressionVisitor().visitExpression(ctx.expression())
        );
    }

    @Override
    public Function visitOperatorDefinition(PreonParser.OperatorDefinitionContext ctx) {
        return new Function(
            getIdentifier(ctx.operatorNameIdentifier().getText()),
            ctx.identifier().stream().map(id -> new ArgumentIdentifier(id.getText())).collect(Collectors.toList()),

            // TODO : find native expression by name and use that one
            ctx.nativeDeclaration() != null ?
                new NativeExpression() :
                new ExpressionVisitor().visitExpression(ctx.expression())
        );
    }

    private FunctionIdentifier getIdentifier(String name) {
        if (!identifiers.containsKey(name))
            throw new RuntimeException("Function/Operator name '" + name + "' is not defined.");
        return identifiers.get(name);
    }
}
