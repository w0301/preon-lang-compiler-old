package org.preonlang;

import java.io.IOException;
import java.io.Writer;

public class ArgumentIdentifier {
    private final String preonName;

    public ArgumentIdentifier(String preonName) {
        this.preonName = preonName;
    }

    public String getPreonName() {
        return preonName;
    }

    public String getTargetName() {
        return preonName;
    }

    public void writeJava(Writer writer) throws IOException {
        writer.write(getTargetName());
    }
}
