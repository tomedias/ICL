package target;

public class ILessEqual extends Instruction
{
    public ILessEqual(String label) {
        op = "if_icmple";
        args = new String[] {label};
    }
}
