# Interpretation and compilation of Programming Languages 2023-24

## ICL Project Part 2

This project contains:

- The interpreter for our language
- The type-checker for our language
- The compiler for our language
- All mandatory features
- Strings with escape characters

### Running the Program

It is expected that you have at least Java 8 installed to run this program

We compile the program by running:


```bash                                                      
./generate.sh # Run this command to generate all jars      
```

This creates the following jars:

- Console.jar: Where before executing an operation the Type-Checker is run to guarantee there are no errors in the code written, and then it runs the Interpreter.

- Typechecker.jar: Where only the TypeChecker runs.

- Interpreter.jar: Where only the Interpreter runs.

- Compiler.jar: Where only the Compiler runs.

We created a special script for running the compiler, which is:

```bash
./runCompiler.sh # Run this to run the compiler and generate the classes on output/compiled
```

To run the other jars manually:

```bash
java -jar <Program>.jar
```
#### All programs expect a file path as an user input (not as an argument)              

### Parsing

Currently, the program is able to parse the following expressions:

```
semicol ::= struct (';' semicol)?

struct ::= decl | whiledo | println | print | ifthenelse | logic | fun | funcall

decl ::= 'let' ( id '=' struct )+ 'in' struct

whiledo ::= 'while' struct 'do' semicol 'end'

println ::= 'println' struct

print ::= 'print' struct

fun ::= 'fun' '(' id ':' type (',' id ':' type)* ')' '->' semicol ':' type 'end'

funcall ::= id '(' struct (',' struct)* ')'

ifthenelse ::= 'if' struct 'then' semicol ('else' semicol)? 'end' 

logic ::= boolop | logic '&&' logic | logic '||' logic | '~' logic

boolop ::= exp | exp '<' exp | exp '>' exp |  exp '<=' exp | exp '>=' exp |exp '==' exp | exp '!=' exp

exp ::= id | bool | num | exp '+' exp | exp '-' exp | exp '*' exp | exp '/' exp | '-' exp | '(' exp ')' | new E | E := E | !E | E;E | ()

bool ::= true | false

num ::= ['1'-'9']\['0'-'9'\]*

id ::= ["a"-"z","A"-"Z"] ( ["a"-"z","A"-"Z","0"-"9","_"] )*

string ::= "\"" ("\\\"" | ~["\""])* "\""

type ::= 'int' | 'bool' | 'string' | 'void' | ref_int | ref_ref | ref_bool | ref_string
```

### Architecture

We implemented the functionality using visitor style


