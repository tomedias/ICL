package types;

public class IntType implements Type {

	public static final IntType singleton = new IntType();
	
	private IntType() {}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public String toString() {
		return "int";
	}

}
