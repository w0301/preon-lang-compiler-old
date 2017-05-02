package org.preonlang;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class File extends ProgramNode {
    private final List<Function> functions;

    public File(List<Function> functions) {
        this.functions = functions;
    }

    @Override
    public List<? extends ProgramNode> getSubNodes() {
        return functions;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void writeJava(Writer writer) throws IOException {
        writer.write("package preonlang;\n\n");
        writer.write("public class PreonClass {\n");

        // writing main function
        writer.write("\tpublic static void main(final String[] args) {\n");
        writer.write("\t\tSystem.out.println(preon_main());\n");
        writer.write("\t}\n");

        // writing other functions
        for (Function function : functions) {
            writer.write("\n");
            function.writeJava(writer);
        }

        writer.write("}\n");
    }
}
