package target;

public class IGoToIfFalse extends Instruction{
    public IGoToIfFalse(String label) {
        op = "ifeq";
        args = new String[] {label};
    }
}
