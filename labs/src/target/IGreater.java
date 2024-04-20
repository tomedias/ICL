package target;

public class IGreater extends Instruction
{
    public IGreater(int i) {
        op = "ifgt";
        args = new String[] {Integer.toString(i)};
    }
}
