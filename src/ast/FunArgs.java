package ast;

import types.Type;

public record FunArgs(String name, Type type){
    public FunArgs {
        if (name == null || type == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
    }
}
