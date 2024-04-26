package values;

import types.RefType;
import types.Type;

public class RefValue implements Value{
    private Value value;
	
	public RefValue(Value value) {
		this.value = value;
	}

	public Value getValue() {
		return value;
	}
    
    public void setValue(Value value){
        this.value = value;
    }

	@Override
	public String toString() {
		return "Ref " + getValue();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof RefValue && ((RefValue)value).getValue() == ((RefValue)obj).getValue();
	}



}
