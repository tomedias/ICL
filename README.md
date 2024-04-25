# Interpretation and compilation of Programming Languages 2023-24

## ICL Project Part 1

This project contains:

- The interpeter for our language
- The type-checker for our language
- All language features except functions.

### Running the Program

It is expected that you have at least java 8 installed to run this program

There are three java programs:

- Console.jar: Where before executing an operation the Type-Checker is ran to garantee there are no errors in the code written, and then it runs the Interpreter.

- Typechecker.jar: Where only the Typechecker runs.

- Interpreter.jar: Where only the Interpreter runs.

You can generate all jars again by running:

```bash
./generate.sh
```

To actually run the generated code you will need to

```bash
java -jar <Program>.jar
```

### Parsing

Currently the program is able to parse the following forms expression:

```
semicol ::= struct (';' semicol)?

struct ::= decl | whiledo | println | print | ifthenelse | logic

decl ::= 'let' ( id '=' struct )+ 'in' struct

whiledo ::= 'while' struct 'do' semicol 'end'

println ::= 'println' struct

print ::= 'print' struct

ifthenelse ::= 'if' struct 'then' semicol ('else' semicol)? 'end' |

logic ::= boolop | logic '&&' logic | logic '||' logic | '~' logic

boolop ::= exp | exp '<' exp | exp '>' exp | exp '==' exp | exp '!=' exp

exp ::= id | bool | num | exp '+' exp | exp '-' exp | exp '*' exp | exp '/' exp | '-' exp | '(' exp ')' | new E | E := E | !E | E;E | ()

bool ::= true | false

num ::= ['1'-'9']\['0'-'9'\]*

id ::= ["a"-"z","A"-"Z"] ( ["a"-"z","A"-"Z","0"-"9","_"] )*
```

### Architecture

We implemented the functionality using visitor style

### Whats left to do

Currently our language doesn't support type casting, in the future it would be nice to support that.
Currently our language only supports Integer types, for math operations it would be nice to add floats or doubles.

### Reading from files

Later we would like to pass a file as a argument to the main function and if the file exists we would like to interpret it so we can "Save programs"
