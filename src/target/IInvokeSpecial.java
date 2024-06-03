package target;

public class IInvokeSpecial extends Instruction {
    public IInvokeSpecial(String arg) {
        this.op = "invokespecial";
        this.args = new String[1];
        args[0] = String.format("%s/<init>()V", arg);
    }
}
