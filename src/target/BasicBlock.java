package target;

import java.util.ArrayList;
import java.util.List;

public class BasicBlock {
	
	List<Instruction> instructions;

	int label_count = 0;
	
	public BasicBlock() {
		
		instructions = new ArrayList<>();
	}
	
	
	public void addInstruction(Instruction i) {
		instructions.add(i);
	}
	
	@Override
	public String toString() {
		StringBuilder block = new StringBuilder(instructions.size()*5);
		
		for (Instruction i : instructions) {
			block.append(i + "\n");
		}
		
		return block.toString();
	}

	public LateInstruction delayOperation() {
		LateInstruction delayed = new LateInstruction(this, instructions.size());
		instructions.add(new EmptyInstruction());
		return delayed;
	}
	public String emitLabel() {
		instructions.add(new ILabel(label_count++));
		return String.format("Label%s", label_count-1);
	}
	public StringBuilder build() {
		StringBuilder block = new StringBuilder(instructions.size()*5);
		build(block);
		return block;
	}
	
	public void build(StringBuilder sb) {
	
		for (Instruction i : instructions) {
			sb.append(i + "\n");
		}

	}


}
