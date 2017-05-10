package org.preonlang;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.preonlang.antlr.*;
import org.preonlang.transform.*;

public class MainClass {
    public static void main(String[] args) throws IOException {
        final PreonLexer lexer = new PreonLexer(new ANTLRInputStream(
            "fn1 : Int -> Int\n" +
            "fn1 a = if True then 2 else 3 ;\n" +
            "fn2 : Int -> Int\n" +
            "fn2 a = fn1 12 ;\n" +
            "main : Int\n" +
            "main = fn2 132 ;\n"
        ));
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final PreonParser parser = new PreonParser(tokens);

        tokens.seek(0);
        final TransformResult transformedProgram = applyTransformers(
            new FileVisitor().visitFile(parser.file()),
            f -> new ArgumentToFunctionCallTransformer(),
            f -> new CheckTypesAndNamesTransformer(f.getFunctions())
        );

        if (transformedProgram.hasError()) {
            System.out.println("==== Compile errors ====");
            for (TransformError error : transformedProgram.getErrors()) {
                System.out.println("Error: " + error.getMessage());
            }
            System.out.println("==== Compile errors ====");
        }
        else {
            System.out.println("==== Java ====");

            final PrintWriter writer = new PrintWriter(System.out);
            transformedProgram.getFile().writeJava(writer);
            writer.flush();

            System.out.println("==== Java ====");
        }
    }

    private static TransformResult applyTransformers(File file, java.util.function.Function<File, ProgramTreeTransformer>... transformers) {
        List<TransformError> errors = new ArrayList<>();
        for (java.util.function.Function<File, ProgramTreeTransformer> transformer : transformers) {
            ProgramTreeTransformer t = transformer.apply(file);
            file = t.transform(file);
            errors.addAll(t.getErrors());
        }
        return new TransformResult(file, errors);
    }

    private static class TransformResult {
        private final File file;
        private final List<TransformError> errors;

        public TransformResult(File file, List<TransformError> errors) {
            this.file = file;
            this.errors = errors;
        }

        public File getFile() {
            return file;
        }

        public List<TransformError> getErrors() {
            return errors;
        }

        public boolean hasError() {
            return !errors.isEmpty();
        }
    }
}
