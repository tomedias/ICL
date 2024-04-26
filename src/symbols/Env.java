package symbols;

import java.util.Hashtable;
import java.util.Map;

public class Env<T> {

    private Map<String,T> table;
    private Env<T> prev;

    public Env() {
        table = new Hashtable<>(20);
    }

    public Env(Env<T> prev) {
        this();
        this.prev = prev;
    }

    public void bind(String id, T val) {
        table.put(id, val);
    }

    public T find(String id) {
        T value = table.get(id);
        if(value != null) {
            return value;
        }
        if(prev != null) {
            return prev.find(id);
        }
        return null;
    }


    public Env<T> beginScope() {
        return new Env<T>(this);
    }

    public Env<T> endScope() {
        if(prev == null) {
            return null;
        }
        table = prev.table;
        return this;
    }




}