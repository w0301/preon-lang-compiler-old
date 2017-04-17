package org.preonlang;

import java.io.IOException;
import java.io.Writer;

public class ConditionExpression extends Expression {
    private final Expression conditionExpression;
    private final Expression thenExpression;
    private final Expression elseExpression;

    public ConditionExpression(Expression conditionExpression, Expression thenExpression, Expression elseExpression) {
        this.conditionExpression = conditionExpression;
        this.thenExpression = thenExpression;
        this.elseExpression = elseExpression;
    }

    public Expression getConditionExpression() {
        return conditionExpression;
    }

    public Expression getThenExpression() {
        return thenExpression;
    }

    public Expression getElseExpression() {
        return elseExpression;
    }

    @Override
    public void writeJava(Writer writer) throws IOException {
        writer.write("(");
        conditionExpression.writeJava(writer);
        writer.write(") ? (");
        thenExpression.writeJava(writer);
        writer.write(") : (");
        elseExpression.writeJava(writer);
        writer.write(")");
    }
}
