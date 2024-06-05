package target;

public class IInvokeInterface extends Instruction{
    public IInvokeInterface(String name, String argSize){
        this.op = "invokeinterface";
        this.args = new String[2];
        this.args[0] = name;
        this.args[1] = argSize;
    }
}
