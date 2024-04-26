package target;

public class IEqual extends Instruction {

    public IEqual(int i) {
        op = "ifeq";
        args = new String[] {Integer.toString(i)};
    }
}