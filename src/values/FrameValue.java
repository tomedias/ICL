package values;

import symbols.Frame;
import types.Type;

public class FrameValue implements Value,Comparable<FrameValue>{
    private Frame frame;
    private int var_id;
    private Type type;

    public FrameValue(Frame frame, int var_id) {
        this.frame = frame;
        this.var_id = var_id;
    }

    public FrameValue(Frame frame, int var_id, Type type) {
        this.frame = frame;
        this.var_id = var_id;
        this.type = type;
    }

    public Frame getFrame() {
        return frame;
    }

    public int getVar_id() {
        return var_id;
    }

    public Type getType() {
        return type;
    }

    @Override
    public int compareTo(FrameValue o) {
        return var_id - o.var_id;
    }
}
