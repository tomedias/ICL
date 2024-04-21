package main;

import java.io.ByteArrayInputStream;

import TypeChecker.TypeChecker;
import ast.Exp;
import parser.*;
import interpreter.*;
import parser.Parser;
import symbols.Env;
import types.IntType;
import types.Type;
import types.TypingException;
import values.Value;

public class Console {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		Parser parser = new Parser(System.in);
		Env<Value> env = new Env<>();
		Env<Type> envType = new Env<>();
		env.bind("xpto", new values.IntValue(10));
		envType.bind("xpto", IntType.singleton);
		while (true) {
			try {
				Exp e = parser.Start();
				TypeChecker.typeChecker(e,envType);
				System.out.println(Interpreter.interpret(e,env));
				//CodeGen.writeToFile(e,"output.j");

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
	public static boolean accept(String s) throws ParseException {
		Parser parser = new Parser(new ByteArrayInputStream(s.getBytes()));
		try {
			parser.Start();
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
}
