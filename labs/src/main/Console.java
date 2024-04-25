package main;

import java.io.*;


import ast.Exp;
import parser.*;
import interpreter.*;
import parser.Parser;
import symbols.Env;
import types.IntType;
import types.Type;
import types.TypingException;
import values.Value;
import TypeChecker.TypeChecker;

public class Console {


	public static void main(String[] args) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		Parser parser = null;
		try{
			String filename = in.readLine();
			System.out.println(in);
			parser = new Parser(new BufferedReader(new FileReader(filename)));
		}catch (IOException e) {
			System.out.println("File not found!");
			parser.ReInit(System.in);
		}

		Env<Value> env = new Env<>();
		Env<Type> envType = new Env<>();
		try {
			Exp e = parser.Start();
			TypeChecker.typeChecker(e,envType);
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
