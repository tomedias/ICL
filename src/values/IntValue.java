package values;

import types.BoolType;
import types.IntType;
import types.Type;

public class IntValue implements Value {
	private int value;
	
	public IntValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof IntValue && value == ((IntValue)obj).getValue();
	}

	public Type getType(){
		return IntType.singleton;
	}

	
}
