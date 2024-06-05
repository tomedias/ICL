package symbols;

import ast.Exp;
import types.FunType;
import types.Type;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
public class FunctionManager{
    public record FunctionInterface(String name, List<String> parameterType, String retType){}
    public record Function(int id, Frame frame, Frame functionFrame, FunctionInterface functionInterface,
                           Exp body){}
    private int currentFunctionID;
    private int getCurrentFunctionInterfaceID;
    public static FunctionManager singleton = new FunctionManager();
    private final Map<FunType, FunctionInterface> functionInterfaces;
    private final List<Function> functions;

    public FunctionManager(){
        this.currentFunctionID = 0;
        this.getCurrentFunctionInterfaceID = 0;
        this.functionInterfaces = new HashMap<>();
        this.functions = new LinkedList<>();
    }

    public  Map<FunType, FunctionInterface> getFunctionInterfaces() {
        return functionInterfaces;
    }

    public FunctionInterface getFunctionInterface(FunType type){
        FunctionInterface functionInterface = functionInterfaces.get(type);
        if(functionInterface == null){
            String functionInterfaceString = String.valueOf(getCurrentFunctionInterfaceID++);
            List<String> parameterTypes = new LinkedList<>();
            for(Type parameter : type.args){
                parameterTypes.add(parameter.getJvmType());
            }
            String returnType = type.returnType.getJvmType();
            functionInterface = new FunctionInterface(functionInterfaceString, parameterTypes, returnType);
            functionInterfaces.put(type, functionInterface);
        }
        return functionInterface;
    }

    public Function addFunction(FunctionInterface functionInterface, Frame parentFrame, Frame functionFrame,Exp body){
        Function function = new Function(currentFunctionID++, parentFrame, functionFrame, functionInterface, body);
        functions.add(function);
        return function;
    }

    public List<Function> getFunctions() {
        return functions;
    }
}
