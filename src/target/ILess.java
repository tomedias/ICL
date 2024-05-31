package target;

public class ILess extends Instruction
{
    public ILess(String label) {
        op = "if_icmplt";
        args = new String[] {label};
    }
}
