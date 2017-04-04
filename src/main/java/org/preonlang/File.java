package org.preonlang;

import java.util.List;

public class File {
    private final List<Function> functions;

    public File(List<Function> functions) {
        this.functions = functions;
    }

    public List<Function> getFunctions() {
        return functions;
    }
}
