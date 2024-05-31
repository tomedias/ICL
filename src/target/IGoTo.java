package target;

public class IGoTo extends Instruction{
    public IGoTo(String label) {
        op = "goto";
        args = new String[] {label};
    }
}
