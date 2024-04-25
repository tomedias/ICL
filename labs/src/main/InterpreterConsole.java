package main;



import ast.Exp;
import parser.*;
import interpreter.*;
import parser.Parser;
import symbols.Env;
import types.TypingException;
import values.Value;

public class InterpreterConsole {
    public static void main(String[] args) {
		Parser parser = new Parser(System.in);
		Env<Value> env = new Env<>();
		while (true) {
			try {
				Exp e = parser.Start();
				System.out.println(Interpreter.interpret(e,env));
			} catch (TokenMgrError e) {
				System.out.println("Lexical Error!");
				parser.ReInit(System.in);
			} catch (ParseException e) {
				System.out.println("Syntax Error!");
				parser.ReInit(System.in);
			} catch (TypingException e) {
				System.out.println(e.getMessage());
				parser.ReInit(System.in);

            }
        }
	}
}
