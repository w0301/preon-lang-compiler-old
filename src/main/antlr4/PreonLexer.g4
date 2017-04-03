lexer grammar PreonLexer;

fragment WHITESPACE
  : [ \t]+
  ;

IDENTIFIER
  : [a-z] [a-zA-Z0-9]*
  ;

INTEGER
  : [1-9] [0-9]*
  | WHITESPACE [+\-] [1-9] [0-9]*
  ;

EQ
  : '='
  ;

OPERATOR
  : [+\-*/&|~><!^=]+
  ;

SEMICOLON
  : ';'
  ;

COLON
  : ','
  ;

LPAREN
  : '('
  ;

RPAREN
  : ')'
  ;

WHITESPACE_SKIP
  : [ \t\r\n]+ -> channel(HIDDEN)
  ;
