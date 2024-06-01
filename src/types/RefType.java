package types;

public class RefType implements Type{

	public Type refType;


	
	public RefType(Type type) {
		this.refType = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public String toString() {
		return "ref";
	}
}
