package org.preonlang;

import org.preonlang.antlr.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class MainClass {
    public static void main(String[] args) {
        PreonLexer lexer = new PreonLexer(new ANTLRInputStream(
            "fn1 : Int -> Int\n" +
            "fn1 a = if a == 1 + 2 then 2 else 3 ;\n" +
            "fn2 : Int -> Int\n" +
            "fn2 a = add a 12 ;\n" +
            "(== 5l) : Int -> Int -> Bool\n" +
            "(==) a b = eq a b ;"
        ));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PreonParser parser = new PreonParser(tokens);

        ParseTree tree = parser.file();
        System.out.println(tree.toStringTree(parser));
        System.out.println("Done");
    }
}
