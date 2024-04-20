package values;

public class StrValue implements Value {
    private String value;

    public StrValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StrValue && value.equals(((StrValue)obj).getValue());
    }


}
