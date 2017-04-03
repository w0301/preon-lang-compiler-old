package org.preonlang;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class MainClass {
    public static void main(String[] args) {
        PreonLexer lexer = new PreonLexer(new ANTLRInputStream(
            "fn1 a = sqrt (add 321.34 False) ;" +
            "\n" +
            "fn2 a = mul (add True 3) (7 + 3+ -4 - (add -1 2)) ;" +
            "\n" +
            "fn3 a = 7 + (add 18 2) ;" +
            "\n" +
            "(|>, 10) a b = eq 'a' \"str\" ;"
        ));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PreonParser parser = new PreonParser(tokens);

        ParseTree tree = parser.file();
        System.out.println(tree.toStringTree(parser));
        System.out.println("Done");
    }
}
