package values;

import types.StringType;
import types.Type;

public class StringValue implements Value{
    public String value;
    public StringValue(String value){
        this.value = value;
    }
    public String toString(){
        return value;
    }
    public boolean equals(Object obj){
        if(obj instanceof StringValue){
            return value.equals(((StringValue)obj).value);
        }
        return false;
    }

    @Override
    public Type getType() {
        return StringType.singleton;
    }
}
