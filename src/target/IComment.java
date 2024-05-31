package target;

public class IComment extends Instruction {
    public IComment(String comment) {
        op = " ;";
        args = new String[] {comment};
    }
}
