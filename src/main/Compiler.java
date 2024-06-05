package main;

import ast.Exp;
import compiler.CodeGen;
import parser.ParseException;
import parser.Parser;
import parser.TokenMgrError;

import symbols.Frame;

import types.TypingException;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.nio.file.Path;
import java.util.ArrayList;

public class Compiler {
    public static void main(String[] args) throws IOException {
        Parser parser = null;
        try {
//            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//            String filename = in.readLine();
            File file = new File("main/test");
            parser = new Parser(new BufferedReader(new FileReader(file)));

            Frame env = new Frame(0, new ArrayList<>());
            Exp e = parser.Start();
            CodeGen.writeToFile(e,"main/out",env);

            for( Frame frame: env.getAllFrames()){
                CodeGen.dumpFrames(frame, "main");
            }
            CodeGen.dump_ref_int();
            CodeGen.dumpClosures();
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
