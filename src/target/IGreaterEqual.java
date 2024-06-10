package target;

public class IGreaterEqual extends Instruction
{
    public IGreaterEqual(String label) {
        op = "if_icmpge";
        args = new String[] {label};
    }
}
