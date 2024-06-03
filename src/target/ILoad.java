package target;

public class ILoad extends Instruction {
    public ILoad(String arg) {
        this.op = String.format("aload_%s", arg);
        this.args = null;
    }

}
