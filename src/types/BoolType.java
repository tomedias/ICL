package types;

public class BoolType implements Type {

    public static final BoolType singleton = new BoolType();

    private BoolType() {}

	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public String toString() {
		return "bool";
	}
}
