package main;



import ast.Exp;
import parser.*;
import interpreter.*;
import symbols.Env;
import types.TypingException;
import values.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InterpreterConsole {
    public static void main(String[] args) throws IOException {
		Parser parser = null;
		try {
			//BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String filename = "src/main/test";
			parser = new Parser(new BufferedReader(new FileReader(filename)));
			Env<Value> env = new Env<>();
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

		}catch (IOException e) {
			System.out.println("File not found!");
			parser.ReInit(System.in);
		}
	}
}
