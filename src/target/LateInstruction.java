package target;

public class LateInstruction {
    private final BasicBlock basicBlock;
    private final int i;

    LateInstruction(BasicBlock basicBlock, int i) {
        this.basicBlock = basicBlock;
        this.i = i;
    }

    public void set(Instruction opcode) {
        basicBlock.instructions.set(i, opcode);
    }
}
