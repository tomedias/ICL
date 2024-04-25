package main;




import ast.Exp;
import parser.*;
import parser.Parser;
import symbols.Env;
import types.Type;
import types.TypingException;
import TypeChecker.TypeChecker;

import java.io.*;

public class TypecheckerConsole {
    public static void main(String[] args) throws IOException {
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
		Env<Type> envType = new Env<>();
		while (true) {
			try {
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

            }
        }
	}
}
