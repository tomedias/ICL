package main;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;

import ast.Exp;
import parser.*;

import compiler.*;
import interpreter.*;
import ast.*;
import parser.Parser;
import target.BasicBlock;

public class Console {

	@SuppressWarnings("static-access")
	public static void main(String args[]) {
		Parser parser = new Parser(System.in);

		while (true) {
			try {
				Exp e = parser.Start();
				System.out.println("Parse OK!" );
				System.out.println(Interpreter.interpret(e));
				CodeGen.writeToFile(e,"output.j");

			} catch (TokenMgrError e) {
				System.out.println("Lexical Error!");
				e.printStackTrace();
				parser.ReInit(System.in);
			} catch (ParseException e) {
				System.out.println("Syntax Error!");
				e.printStackTrace();
				parser.ReInit(System.in);
			} catch (FileNotFoundException e) {
                throw new RuntimeException(e);
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
