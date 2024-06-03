package target;

public class IStore extends Instruction {
    public IStore(String arg) {
        this.op = String.format("astore_%s", arg);
        this.args = null;
    }
}
