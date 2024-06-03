package target;

public class IGetField extends Instruction {
    public IGetField(String frame_name, String field_name, String field_type) {
        this.op = "getfield";
        this.args = new String[]{String.format("%s/%s %s",frame_name,field_name, field_type)};
    }
}
