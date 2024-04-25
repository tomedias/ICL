package main;




import ast.Exp;
import parser.*;
import parser.Parser;
import symbols.Env;
import types.Type;
import types.TypingException;
import TypeChecker.TypeChecker;

public class TypecheckerConsole {
    public static void main(String[] args) {
		Parser parser = new Parser(System.in);
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
