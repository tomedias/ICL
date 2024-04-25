/* Generated By:JavaCC: Do not edit this line. Parser.java */
package parser;
import ast.*;import ast.BoolOP.ASTAnd;import ast.BoolOP.ASTBool;import ast.BoolOP.ASTBoolNegate;import ast.BoolOP.ASTOr;import ast.NumOP.*;import ast.RefOP.ASTAssign;import ast.RefOP.ASTBinding;import ast.RefOP.ASTDereference;import ast.RefOP.ASTReference;import ast.Struct.*;import java.util.ArrayList;

public class Parser implements ParserConstants {

  final public Exp Start() throws ParseException {
  Exp e;
    e = Semicol();
    jj_consume_token(0);
                          {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  final public Exp Semicol() throws ParseException {
  Exp e1,e2;
    e1 = Struct();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case DOTCOMMA:
      jj_consume_token(DOTCOMMA);
      e2 = Semicol();
                                               e1 = new ASTDotComma(e1,e2);
      break;
    default:
      jj_la1[0] = jj_gen;
      ;
    }
      {if (true) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp Struct() throws ParseException {
 Exp e;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LET:
      e = decl();
               {if (true) return e;}
      break;
    case WHILE:
      e = whileDo();
                  {if (true) return e;}
      break;
    case PRINTLN:
      e = println();
                  {if (true) return e;}
      break;
    case PRINT:
      e = print();
                {if (true) return e;}
      break;
    case IF:
      e = ifThenElse();
                     {if (true) return e;}
      break;
    case Num:
    case Bool:
    case MINUS:
    case LPAR:
    case DEREFERENCE:
    case NEW:
    case NEGATE:
    case ID:
      e = assign();
                  {if (true) return e;}
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp ifThenElse() throws ParseException {
  Exp e1, e2, e3=null;
    jj_consume_token(IF);
    e1 = Struct();
    jj_consume_token(THEN);
    e2 = Semicol();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case ELSE:
      jj_consume_token(ELSE);
      e3 = Semicol();
      break;
    default:
      jj_la1[2] = jj_gen;
      ;
    }
    jj_consume_token(END);
                                                                           {if (true) return new ASTIf(e1, e2, e3);}
    throw new Error("Missing return statement in function");
  }

  final public Exp whileDo() throws ParseException {
  Exp e1, e2;
    jj_consume_token(WHILE);
    e1 = Struct();
    jj_consume_token(DO);
    e2 = Semicol();
    jj_consume_token(END);
                                                   {if (true) return new ASTWhile(e1, e2);}
    throw new Error("Missing return statement in function");
  }

  final public Exp decl() throws ParseException {
  Token x; Exp e1, e2; ArrayList<ASTBinding> bindings = new ArrayList<ASTBinding>();
    jj_consume_token(LET);
    label_1:
    while (true) {
      x = jj_consume_token(ID);
      jj_consume_token(ATTR);
      e1 = Struct();
        bindings.add(new ASTBinding(new ASTVar(x.image), e1));
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ID:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_1;
      }
    }
    jj_consume_token(IN);
    e2 = Struct();
                               {if (true) return new ASTLet(bindings, e2);}
    throw new Error("Missing return statement in function");
  }

  final public Exp print() throws ParseException {
  Exp e;
    jj_consume_token(PRINT);
    e = Struct();
                         {if (true) return new ASTPrint(e);}
    throw new Error("Missing return statement in function");
  }

  final public Exp println() throws ParseException {
  Exp e;
    jj_consume_token(PRINTLN);
    e = Struct();
                           {if (true) return new ASTPrintln(e);}
    throw new Error("Missing return statement in function");
  }

  final public Exp assign() throws ParseException {
   Exp e1,e2;
    e1 = logic();
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case ASSIGN:
        ;
        break;
      default:
        jj_la1[4] = jj_gen;
        break label_2;
      }
      jj_consume_token(ASSIGN);
      e2 = logic();
                                        {if (true) return new ASTAssign(e1,e2);}
    }
     {if (true) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp logic() throws ParseException {
  Exp e1, e2;
    e1 = boolOP();
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
      case OR:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_3;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case AND:
        jj_consume_token(AND);
        e2 = boolOP();
                            e1 = new ASTAnd(e1, e2);
        break;
      case OR:
        jj_consume_token(OR);
        e2 = boolOP();
                           e1 = new ASTOr(e1, e2);
        break;
      default:
        jj_la1[6] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
      {if (true) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp boolOP() throws ParseException {
  Exp e1, e2;
    e1 = Expr();
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LESS:
      case GREATER:
      case EQUAL:
      case NOT_EQUAL:
        ;
        break;
      default:
        jj_la1[7] = jj_gen;
        break label_4;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case EQUAL:
        jj_consume_token(EQUAL);
        e2 = Expr();
                            e1 = new ASTEqual(e1, e2);
        break;
      case LESS:
        jj_consume_token(LESS);
        e2 = Expr();
                           e1 = new ASTLess(e1, e2);
        break;
      case GREATER:
        jj_consume_token(GREATER);
        e2 = Expr();
                              e1 = new ASTGreater(e1, e2);
        break;
      case NOT_EQUAL:
        jj_consume_token(NOT_EQUAL);
        e2 = Expr();
                                e1 = new ASTNotEqual(e1, e2);
        break;
      default:
        jj_la1[8] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
      {if (true) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp Expr() throws ParseException {
  Exp e1, e2;
    e1 = Term();
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
      case MINUS:
        ;
        break;
      default:
        jj_la1[9] = jj_gen;
        break label_5;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS:
        jj_consume_token(PLUS);
        e2 = Term();
                            e1 = new ASTAdd(e1,e2);
        break;
      case MINUS:
        jj_consume_token(MINUS);
        e2 = Term();
                             e1 = new ASTSub(e1,e2);
        break;
      default:
        jj_la1[10] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
       {if (true) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp Term() throws ParseException {
  Exp e1, e2;
    e1 = Fact();
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TIMES:
      case DIV:
        ;
        break;
      default:
        jj_la1[11] = jj_gen;
        break label_6;
      }
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case TIMES:
        jj_consume_token(TIMES);
        e2 = Fact();
                             e1 = new ASTMult(e1,e2);
        break;
      case DIV:
        jj_consume_token(DIV);
        e2 = Fact();
                           e1 = new ASTDiv(e1,e2);
        break;
      default:
        jj_la1[12] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
       {if (true) return e1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp Fact() throws ParseException {
  Token x; Exp e;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case Num:
      x = jj_consume_token(Num);
        {if (true) return new ASTInt(Integer.parseInt(x.image));}
      break;
    case Bool:
      x = jj_consume_token(Bool);
    {if (true) return new ASTBool(Boolean.parseBoolean(x.image));}
      break;
    case ID:
      x = jj_consume_token(ID);
    {if (true) return new ASTVar(x.image);}
      break;
    case MINUS:
      jj_consume_token(MINUS);
      x = jj_consume_token(Num);
    {if (true) return new ASTInt(-(Integer.parseInt(x.image)));}
      break;
    case DEREFERENCE:
      jj_consume_token(DEREFERENCE);
      e = Fact();
  {if (true) return new ASTDereference(e);}
      break;
    case NEW:
      jj_consume_token(NEW);
      e = Fact();
  {if (true) return new ASTReference(e);}
      break;
    case NEGATE:
      jj_consume_token(NEGATE);
      e = Fact();
  {if (true) return new ASTBoolNegate(e);}
      break;
    case LPAR:
      jj_consume_token(LPAR);
      e = Semicol();
      jj_consume_token(RPAR);
 {if (true) return e;}
      break;
    default:
      jj_la1[13] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public ParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[14];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x400000,0xce0a5800,0x0,0x0,0x1000000,0x60,0x60,0x780,0x780,0x6000,0x6000,0x18000,0x18000,0x6025800,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x49,0x4,0x40,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x48,};
   }

  /** Constructor with InputStream. */
  public Parser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Parser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 14; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 14; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Parser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 14; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 14; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Parser(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 14; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 14; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[40];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 14; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 40; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
