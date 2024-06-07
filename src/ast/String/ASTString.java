package ast.String;

import ast.Exp;
import symbols.Env;
import types.TypingException;
import values.Value;

public class ASTString implements Exp {

    public String value;
    public ASTString(String value){
        this.value = parseString(value);
    }
    private String parseString(String value){
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < value.length() - 1; i++){
            if(value.charAt(i) == '\\'){
                i++;
                switch(value.charAt(i)){
                    case 'n':
                        sb.append('\n');
                        break;
                    case 't':
                        sb.append('\t');
                        break;
                    case 'r':
                        sb.append('\r');
                        break;
                    case 'b':
                        sb.append('\b');
                        break;
                    case 'f':
                        sb.append('\f');
                        break;
                    case '\\':
                        sb.append('\\');
                        break;
                    case '\'':
                        sb.append('\'');
                        break;
                    case '\"':
                        sb.append('\"');
                        break;
                    case '0':
                        sb.append('\0');
                        break;
                    default:
                        sb.append(value.charAt(i));
                        break;
                }
            }else{
                sb.append(value.charAt(i));
            }
        }
        return sb.toString();
    }


    @Override
    public <T, E> T accept(Visitor<T, E> v, E env) throws TypingException, TypingException {
        return v.visit(this, env);
    }
}
