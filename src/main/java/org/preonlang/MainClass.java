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
        if (args.length < 2) {
            System.out.println("Two command line argumnets are needed - file to compile and output directory path.");
            System.exit(1);
        }

        final PreonLexer lexer = new PreonLexer(CharStreams.fromFileName(args[0]));
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final PreonParser parser = new PreonParser(tokens);
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
            System.exit(1);
        }
        else {
            new java.io.File(args[1]).mkdirs();
            final PrintWriter writer = new PrintWriter(args[1] + "/PreonClass.java");
            transformedProgram.getFile().writeJava(writer);
            writer.flush();

            System.out.println("Compile Preon sourde files to Java successful.");
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
