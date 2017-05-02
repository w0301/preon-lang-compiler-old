package org.preonlang.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.preonlang.ProgramNode;

public class TransformContext {
    private final List<ProgramNode> parents;

    public TransformContext(ProgramNode parent) {
        parents = new ArrayList<>();
        parents.add(parent);
    }

    private TransformContext(List<ProgramNode> parents) {
        this.parents = parents;
    }

    public ProgramNode getParent() {
        return getParent(0);
    }

    public ProgramNode getParent(int i) {
        final int index = parents.size() - i - 1;
        if (index < 0 || index >= parents.size()) return null;
        return parents.get(index);
    }

    public ProgramNode getParent(Class<? extends ProgramNode> clazz) {
        for (int i = parents.size() - 1; i >= 0; i--) {
            final ProgramNode node = parents.get(i);
            if (clazz.isInstance(node)) return node;
        }
        return null;
    }

    public boolean hasParent() {
        return getParent() != null;
    }

    public boolean hasParent(int i) {
        return getParent(i) != null;
    }

    public boolean hasParent(Class<? extends ProgramNode> clazz) {
        return getParent(clazz) != null;
    }

    public TransformContext withNewParent(ProgramNode parent) {
        List<ProgramNode> newParents = new ArrayList<>();
        newParents.addAll(parents);
        newParents.add(parent);
        return new TransformContext(newParents);
    }
}
