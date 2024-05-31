package target;

public class IGreater extends Instruction
{
    public IGreater(String label) {
        op = "if_icmpgt";
        args = new String[] {label};
    }
}
