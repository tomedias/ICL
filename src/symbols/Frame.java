package symbols;

import ast.ASTVar;
import values.FrameValue;
import values.Value;

import java.util.List;

public class Frame extends Env<FrameValue>{
    private int frame_id;
    private static final String FRAME_NAME_FORMAT = "frame_%d";
    private String name;
    private List<Frame> allFrames;


    public Frame(int frame_id, List<Frame> allFrames) {
        this.frame_id = frame_id;
        this.name = String.format(FRAME_NAME_FORMAT, frame_id);
        this.allFrames = allFrames;
        this.prev = null;
    }

    public Frame(int frame_id, Frame prev) {
        super(prev);
        this.frame_id = frame_id;
        this.name = String.format(FRAME_NAME_FORMAT, frame_id);
        this.allFrames = prev.allFrames;
    }


    @Override
    public Frame beginScope() {
        Frame newFrame = new Frame(allFrames.size(), this);
        allFrames.add(newFrame);
        return newFrame;
    }

    @Override
    public Frame getPrev() {
        return (Frame)super.prev;
    }

    public int getFrame_id() {
        return frame_id;
    }

    public String getName() {
        return name;
    }

    public List<Frame> getAllFrames() {
        return allFrames;
    }

    public List<FrameValue> getVars() {
        return table.values().stream().toList();
    }


}
