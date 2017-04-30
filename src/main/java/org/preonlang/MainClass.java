package org.preonlang;

import java.io.IOException;
import java.io.PrintWriter;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.preonlang.antlr.*;

public class MainClass {
    public static void main(String[] args) throws IOException {
        final PreonLexer lexer = new PreonLexer(new ANTLRInputStream(
            "fn1 : Int -> Int\n" +
            "fn1 a = if a == 1 + 2 then 2 else 3 ;\n" +
            "fn2 : Int -> Int\n" +
            "fn2 a = add a 12 ;\n" +
            "(== 5l) : Int -> Int -> Bool\n" +
            "(==) a b = eq a b ;"
        ));
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final PreonParser parser = new PreonParser(tokens);

        System.out.println("==== Java ====");
        tokens.seek(0);
        final File file = new FileVisitor().visitFile(parser.file());
        final PrintWriter writer = new PrintWriter(System.out);
        file.writeJava(writer);
        writer.flush();
        System.out.println("==== Java ====");

        System.out.println();

        System.out.println("==== AST ====");
        tokens.seek(0);
        final ParseTree tree = parser.file();
        System.out.println(tree.toStringTree(parser));
        System.out.println("==== AST ====");
    }
}
