package org.preonlang.antlr;

import org.preonlang.*;
import java.util.HashMap;
import org.antlr.v4.runtime.*;

public abstract class BasePreonParser extends Parser {
    private final HashMap<String, Integer> precedencies = new HashMap<>();
    private final HashMap<String, Boolean> associativities = new HashMap<>();

    public BasePreonParser(TokenStream input) {
        super(input);

        // adding testing precedencies
        // TODO : get precedencies from contructor argument which is
        // created from another parser run
        putOperator("+", 2, true);
        putOperator("-", 2, true);
        putOperator("*", 3, true);
        putOperator("/", 3, true);
        putOperator("%", 3, true);
        putOperator("^", 4, true);
        putOperator("<", 1, true);
        putOperator(">", 1, true);
        putOperator("<=", 1, true);
        putOperator(">=", 1, true);
        putOperator("==", 1, true);
        putOperator("/=", 1, true);
        putOperator("||", 5, true);
        putOperator("&&", 6, true);
    }

    public int getPrecedence(String operator) {
        if (!precedencies.containsKey(operator)) return 9;

        int val = precedencies.get(operator);
        if (val < 1) return 1;
        if (val > 9) return 9;
        return val;
    }

    public int getPrecedence(Token token) {
        return getPrecedence(token.getText());
    }

    public boolean isLeftAssociative(String operator) {
        if (!associativities.containsKey(operator)) return true;
        return associativities.get(operator);
    }

    public boolean isLeftAssociative(Token token) {
        return isLeftAssociative(token.getText());
    }

    public void putOperator(String operator, int prec, boolean assoc) {
        precedencies.put(operator, prec);
        associativities.put(operator, assoc);
    }
}
