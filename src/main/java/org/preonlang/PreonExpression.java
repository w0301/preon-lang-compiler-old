package org.preonlang;

import java.util.List;

public abstract class PreonExpression extends Expression {
    public PreonExpression(List<ArgumentIdentifier> arguments) {
        super(arguments);
    }
}
