package target;

public class INew extends Instruction{
    public INew(String arg){
        this.op = "new";
        this.args = new String[1];
        args[0] = arg;
    }
}
