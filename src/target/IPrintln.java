package target;

public class IPrintln extends Instruction
{
    public IPrintln() {
        op = "invokevirtual";
        args = new String[1];
        args[0] = "java/io/PrintStream/println(Ljava/lang/String;)V";
    }
}
