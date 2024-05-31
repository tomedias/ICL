package target;

public class ILabel extends Instruction{
    public ILabel(int label) {
        op = String.format("Label%d:", label);
        args = null;
    }
}
