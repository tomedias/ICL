package symbols;

import types.FunType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record FunctionInterface (String name, String parameterType, String retType,Frame frame,Frame functionFrame){
    public static int interfaceId = 0;
    public static Map<FunType, FunctionInterface> map = new HashMap<>();
}