package types;

public class RefType implements Type{
    public static final RefType singleton = new RefType();
	
	private RefType() {}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public String toString() {
		return "reference";
	}
}
