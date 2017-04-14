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

conditionExpression
  : IF expression THEN expression ELSE expression
  ;

closedExpression
  : constant
  | identifier
  | conditionExpression
  | LPAREN expression RPAREN
  ;

operatorExpression
  : closedExpression
  // left associative operators
  | operatorExpression {getPrecedence(_input.LT(1)) == 9 && isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | operatorExpression {getPrecedence(_input.LT(1)) == 8 && isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | operatorExpression {getPrecedence(_input.LT(1)) == 7 && isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | operatorExpression {getPrecedence(_input.LT(1)) == 6 && isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | operatorExpression {getPrecedence(_input.LT(1)) == 5 && isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | operatorExpression {getPrecedence(_input.LT(1)) == 4 && isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | operatorExpression {getPrecedence(_input.LT(1)) == 3 && isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | operatorExpression {getPrecedence(_input.LT(1)) == 2 && isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | operatorExpression {getPrecedence(_input.LT(1)) == 1 && isLeftAssociative(_input.LT(1))}? operator operatorExpression
  // right associative operators
  | operatorExpression {getPrecedence(_input.LT(1)) == 9 && !isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | operatorExpression {getPrecedence(_input.LT(1)) == 8 && !isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | operatorExpression {getPrecedence(_input.LT(1)) == 7 && !isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | operatorExpression {getPrecedence(_input.LT(1)) == 6 && !isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | operatorExpression {getPrecedence(_input.LT(1)) == 5 && !isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | operatorExpression {getPrecedence(_input.LT(1)) == 4 && !isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | operatorExpression {getPrecedence(_input.LT(1)) == 3 && !isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | operatorExpression {getPrecedence(_input.LT(1)) == 2 && !isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | operatorExpression {getPrecedence(_input.LT(1)) == 1 && !isLeftAssociative(_input.LT(1))}? operator operatorExpression
  ;

expression
  : closedExpression
  | operatorExpression
  | identifier (closedExpression)+
  ;

nativeDeclaration
  : NATIVE
  ;

functionNameIdentifier
  : identifier
  ;

functionDefinition
  : functionNameIdentifier (identifier)* EQ (expression | nativeDeclaration) DEFINITION_DELIM
  ;

operatorPrecedence
  : INTEGER
  ;

operatorNameIdentifier
  : operator
  ;

operatorDefinition
  : LPAREN operatorNameIdentifier operatorPrecedence RPAREN identifier identifier EQ (expression | nativeDeclaration) DEFINITION_DELIM
  ;

file
  : (functionDefinition | operatorDefinition)* EOF
  ;
