parser grammar PreonParser;

options {
  tokenVocab=PreonLexer;
  superClass=BasePreonParser;
}

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

closedExpression
  : constant
  | identifier
  | LPAREN expression RPAREN
  ;

operatorExpression6
  : operatorExpression6 ({getPrecedence(_input.LT(1)) == 6}? operator operatorExpression6)
  | closedExpression
  ;

operatorExpression5
  : operatorExpression5 ({getPrecedence(_input.LT(1)) == 5}? operator operatorExpression5)
  | operatorExpression6
  ;

operatorExpression4
  : operatorExpression4 ({getPrecedence(_input.LT(1)) == 4}? operator operatorExpression4)
  | operatorExpression5
  ;

operatorExpression3
  : operatorExpression3 ({getPrecedence(_input.LT(1)) == 3}? operator operatorExpression3)
  | operatorExpression4
  ;

operatorExpression2
  : operatorExpression2 ({getPrecedence(_input.LT(1)) == 2}? operator operatorExpression2)
  | operatorExpression3
  ;

operatorExpression1
  : operatorExpression1 ({getPrecedence(_input.LT(1)) == 1}? operator operatorExpression1)
  | operatorExpression2
  ;

expression
  : closedExpression
  | operatorExpression1
  | identifier (closedExpression)*
  ;

nativeDeclaration
  : NATIVE
  ;

functionDefinition
  : identifier (identifier)* EQ (expression | nativeDeclaration) DEFINITION_DELIM
  ;

operatorPrecedence
  : INTEGER
  ;

operatorDefinition
  : LPAREN operator operatorPrecedence RPAREN identifier identifier EQ (expression | nativeDeclaration) DEFINITION_DELIM
  ;

file
  : (functionDefinition | operatorDefinition)* EOF
  ;
