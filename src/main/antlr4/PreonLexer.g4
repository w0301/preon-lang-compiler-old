lexer grammar PreonLexer;

fragment WHITESPACE
  : [ \t]+
  ;

INTEGER
  : [1-9] [0-9]*
  | WHITESPACE [\-] [1-9] [0-9]*
  ;

FLOAT
  : [0-9]+ '.' [0-9]+
  | WHITESPACE [+\-] [0-9]+ '.' [0-9]+
  ;

BOOL
  : ('True' | 'False')
  ;

CHAR
  : '\'' . '\''
  ;

STRING
  : '"' .*? '"'
  ;

NATIVE
  : 'native'
  ;

IF
  : 'if'
  ;

THEN
  : 'then'
  ;

ELSE
  : 'else'
  ;

IDENTIFIER
  : [a-z] [a-zA-Z0-9]*
  ;

EQ
  : '='
  ;

// + - * / & | > < ^ = , %
// P M S D A O G L K E C Q
OPERATOR
  : [+\-*/&|><^=,%]+
  ;

OPERATOR_PRECEDENCE
  : [1-9] [lr]
  ;

DEFINITION_DELIM
  : ';'
  ;

LPAREN
  : '('
  ;

RPAREN
  : ')'
  ;

WHITESPACE_SKIP
  : [\r\n \t]+ -> channel(HIDDEN)
  ;
