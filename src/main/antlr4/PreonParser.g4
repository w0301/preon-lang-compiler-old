parser grammar PreonParser;

options { tokenVocab=PreonLexer; }

identifier
  : IDENTIFIER
  ;

constant
  : INTEGER
  ;

operator
  : OPERATOR
  ;

closedFunctionExpression
  : constant
  | identifier
  | LPAREN expression RPAREN
  ;

closedOperatorExpression
  : closedFunctionExpression
  | closedOperatorExpression operator closedOperatorExpression
  ;

expression
  : closedFunctionExpression
  | closedOperatorExpression
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
