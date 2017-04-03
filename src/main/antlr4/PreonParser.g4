parser grammar PreonParser;

options { tokenVocab=PreonLexer; }

integerValue
  : INTEGER
  ;

floatValue
  : FLOAT
  ;

boolValue
  : BOOL
  ;

charValue
  : CHAR
  ;

stringValue
  : STRING
  ;

constant
  : integerValue
  | floatValue
  | boolValue
  | charValue
  | stringValue
  ;

identifier
  : IDENTIFIER
  ;

operator
  : OPERATOR
  ;

closedFunctionExpression
  : constant
  | identifier
  | LPAREN expression RPAREN
  ;

operatorExpression
  : closedFunctionExpression
  | operatorExpression operator operatorExpression
  ;

expression
  : operatorExpression
  | closedFunctionExpression
  | identifier closedFunctionExpression*
  ;

functionDefinition
  : identifier (identifier)* EQ expression SEMICOLON
  ;

operatorPrecedence
  : INTEGER
  ;

operatorDefinition
  : LPAREN operator COLON operatorPrecedence RPAREN (identifier)* EQ expression SEMICOLON
  ;

file
  : (functionDefinition | operatorDefinition)* EOF
  ;
