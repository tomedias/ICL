package ast;

import types.Type;

public record Argument(String name, Type type) {
    public Argument {
        if (name == null || type == null) {
            throw new IllegalArgumentException("ast.Argument cannot be null");
        }
    }
}
