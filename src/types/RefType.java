package types;

public class RefType implements Type{

	public Type refType;
	public String jvm_ref;

	
	public RefType(Type type,String jvm_ref) {
		this.refType = type;
		this.jvm_ref = jvm_ref;
	}
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


	@Override
	public String getJvmType() {
		return "L"+jvm_ref+";";
	}
}
