package org.preonlang;

import java.io.IOException;
import java.io.Writer;

public class FunctionIdentifier {
    private final String preonName;

    public FunctionIdentifier(String preonName) {
        this.preonName = preonName;
    }

    public String getPreonName() {
        return preonName;
    }

    public String getTargetName() {
        // TODO : transform to target name (change operator symobls etc.)
        return preonName;
    }

    public void writeJava(Writer writer) throws IOException {
        writer.write(getTargetName());
    }
}
