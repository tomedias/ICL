package target;

public class ICheckcast extends Instruction{
    public ICheckcast(String arg) {
        this.op = "checkcast";
        this.args = new String[1];
        args[0] = arg;
    }
}
