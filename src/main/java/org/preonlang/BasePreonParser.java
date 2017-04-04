package org.preonlang;

import java.util.HashMap;
import org.antlr.v4.runtime.*;

public abstract class BasePreonParser extends Parser {
    private final HashMap<String, Integer> precedencies = new HashMap<>();

    public BasePreonParser(TokenStream input) {
        super(input);

        // adding testing precedencies
        // TODO : get precedencies from contructor argument which is
        // created from another parser run
        putPrecedence("+", 1);
        putPrecedence("-", 1);
        putPrecedence("*", 2);
        putPrecedence("/", 2);
        putPrecedence("^", 3);
        putPrecedence("<", 4);
        putPrecedence(">", 4);
        putPrecedence("<=", 4);
        putPrecedence(">=", 4);
        putPrecedence("||", 5);
        putPrecedence("&&", 6);
    }

    public int getPrecedence(String operator) {
        if (!precedencies.containsKey(operator)) return 6;

        int val = precedencies.get(operator);
        if (val < 1) return 1;
        if (val > 6) return 6;
        return val;
    }

    public int getPrecedence(Token token) {
        return getPrecedence(token.getText());
    }

    public void putPrecedence(String operator, int value) {
        precedencies.put(operator, value);
    }
}
