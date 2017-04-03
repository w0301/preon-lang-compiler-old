lexer grammar PreonLexer;

fragment WHITESPACE
  : [ \t]+
  ;

INTEGER
  : [1-9] [0-9]*
  | WHITESPACE [+\-] [1-9] [0-9]*
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

IDENTIFIER
  : [a-z] [a-zA-Z0-9]*
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
