package target;

public class ILdc extends Instruction{


    public ILdc(String value){
        this.op = "ldc";
        this.args = new String[1];
        this.args[0] = value;
    }


}
