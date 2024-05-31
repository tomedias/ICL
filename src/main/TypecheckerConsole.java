package main;




import ast.Exp;
import parser.*;
import symbols.Env;
import types.Type;
import types.TypingException;
import TypeChecker.TypeChecker;

import java.io.*;

public class TypecheckerConsole {
    public static void main(String[] args) {
		Parser parser = null;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String filename = in.readLine();
			parser = new Parser(new BufferedReader(new FileReader(filename)));
			Env<Type> envType = new Env<>();
			Exp e = parser.Start();
			TypeChecker.typeChecker(e,envType);
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
