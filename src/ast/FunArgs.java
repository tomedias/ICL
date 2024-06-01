package ast;

import types.Type;

public record FunArgs<T, E extends Type>(T name, E type){
    public FunArgs {
        if (name == null || type == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
    }
}
