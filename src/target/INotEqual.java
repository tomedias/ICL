package target;

public class INotEqual extends Instruction
{
    public INotEqual(String label) {
        op = "if_icmpne";
        args = new String[] {label};
    }
}
