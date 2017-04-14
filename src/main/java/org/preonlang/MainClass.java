package org.preonlang;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class MainClass {
    public static void main(String[] args) {
        PreonLexer lexer = new PreonLexer(new ANTLRInputStream(
            "fn = if a == 1 + 2 then 2 else 3 ;"
        ));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PreonParser parser = new PreonParser(tokens);

        ParseTree tree = parser.file();
        System.out.println(tree.toStringTree(parser));
        System.out.println("Done");
    }
}
