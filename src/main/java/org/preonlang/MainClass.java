package org.preonlang;

import org.preonlang.antlr.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class MainClass {
    public static void main(String[] args) {
        PreonLexer lexer = new PreonLexer(new ANTLRInputStream(
            "fn =\n if a == 1 + 2 then\n 2 else\n 3\n ;\n\n" +
            "fn a = add a 12 ;\n" +
            "(== 1l) a b = eq a b ;"
        ));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PreonParser parser = new PreonParser(tokens);

        ParseTree tree = parser.file();
        System.out.println(tree.toStringTree(parser));
        System.out.println("Done");
    }
}
