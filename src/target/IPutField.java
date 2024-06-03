package target;

public class IPutField extends Instruction{
    public IPutField(String frame_name, String field_name, String field_type) {

        this.op = "putfield";
        this.args = new String[]{String.format("%s/%s %s",frame_name,field_name, field_type)};
    }
}
