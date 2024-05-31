package target;

public class IEqual extends Instruction {

    public IEqual(String label) {
        op = "if_icmpeq";
        args = new String[] {label};
    }
}