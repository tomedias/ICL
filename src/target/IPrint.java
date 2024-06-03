package target;

public class IPrint extends Instruction
{
    public IPrint() {
        op = "invokevirtual";
        args = new String[1];
        args[0] = "java/io/PrintStream/print(Ljava/lang/String;)V";
    }
}
