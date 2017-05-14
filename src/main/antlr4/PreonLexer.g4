lexer grammar PreonLexer;

fragment WHITESPACE
  : [ \t]+
  ;

INTEGER
  : [1-9] [0-9]*
  | [0]
  | WHITESPACE [\-] [1-9] [0-9]*
  ;

FLOAT
  : [0-9]+ '.' [0-9]+
  | WHITESPACE [\-] [0-9]+ '.' [0-9]+
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

TYPE
  : [A-Z] [a-zA-Z0-9]*
  ;

EQ
  : '='
  ;

COLON
  : ':'
  ;

DEFINITION_ARG_SEPARATOR
  : '->'
  ;

LPAREN
  : '('
  ;

RPAREN
  : ')'
  ;

DEFINITION_DELIM
  : ';'
  ;

// + - * / & | > < ^ = , %
// P M S D A O G L K E C Q
OPERATOR
  : [+\-*/&|><^=,%]+
  ;

OPERATOR_PRECEDENCE
  : [1-9] [lr]
  ;

WHITESPACE_SKIP
  : [\r\n \t]+ -> channel(HIDDEN)
  ;
