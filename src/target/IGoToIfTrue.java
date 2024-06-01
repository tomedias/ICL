package target;

public class IGoToIfTrue extends Instruction{
    public IGoToIfTrue(String label) {
        op = "ifne";
        args = new String[] {label};
    }
}
