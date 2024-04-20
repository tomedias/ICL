package target;

public class ILess extends Instruction
{
    public ILess(int i) {
        op = "iflt";
        args = new String[] {Integer.toString(i)};
    }
}
