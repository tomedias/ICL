package ast;

public record Pair<T,E> (T t, E e){
    public Pair {
        if (t == null || e == null){
            throw new IllegalArgumentException("ast.Argument cannot be null");
        }
    }
}
