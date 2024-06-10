package main;

import TypeChecker.TypeChecker;
import ast.Exp;
import compiler.CodeGen;
import parser.ParseException;
import parser.Parser;
import parser.TokenMgrError;

import symbols.Env;
import symbols.Frame;

import types.Type;
import types.TypingException;


import java.io.*;

import java.util.ArrayList;

public class Compiler {
    public static void main(String[] args) throws IOException {
        Parser parser = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String filename = in.readLine();
            File file = new File(filename);
            parser = new Parser(new BufferedReader(new FileReader(file)));

            Frame env = new Frame(0, new ArrayList<>());
            Env<Type> envType = new Env<>();
            Exp e = parser.Start();
            TypeChecker.typeChecker(e,envType);
            CodeGen.writeToFile(e,"output/out.j",env);
            for( Frame frame: env.getAllFrames()){
                CodeGen.dumpFrames(frame, "output");
            }
            CodeGen.dump_ref_int();
            CodeGen.dump_ref_ref();
            CodeGen.dump_interfaces();
            CodeGen.dumpFunctions();
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
            System.out.println(e.getMessage());
            parser.ReInit(System.in);
        }

    }
}
