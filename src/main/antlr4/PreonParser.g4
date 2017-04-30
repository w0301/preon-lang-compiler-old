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

type
  : TYPE
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

  | operatorExpression {getPrecedence(_input.LT(1)) == 9 && isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | <assoc=right> operatorExpression {getPrecedence(_input.LT(1)) == 9}? operator operatorExpression

  | operatorExpression {getPrecedence(_input.LT(1)) == 8 && isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | <assoc=right> operatorExpression {getPrecedence(_input.LT(1)) == 8}? operator operatorExpression

  | operatorExpression {getPrecedence(_input.LT(1)) == 7 && isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | <assoc=right> operatorExpression {getPrecedence(_input.LT(1)) == 7}? operator operatorExpression

  | operatorExpression {getPrecedence(_input.LT(1)) == 6 && isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | <assoc=right> operatorExpression {getPrecedence(_input.LT(1)) == 6}? operator operatorExpression

  | operatorExpression {getPrecedence(_input.LT(1)) == 5 && isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | <assoc=right> operatorExpression {getPrecedence(_input.LT(1)) == 5}? operator operatorExpression

  | operatorExpression {getPrecedence(_input.LT(1)) == 4 && isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | <assoc=right> operatorExpression {getPrecedence(_input.LT(1)) == 4}? operator operatorExpression

  | operatorExpression {getPrecedence(_input.LT(1)) == 3 && isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | <assoc=right> operatorExpression {getPrecedence(_input.LT(1)) == 3}? operator operatorExpression

  | operatorExpression {getPrecedence(_input.LT(1)) == 2 && isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | <assoc=right> operatorExpression {getPrecedence(_input.LT(1)) == 2}? operator operatorExpression

  | operatorExpression {getPrecedence(_input.LT(1)) == 1 && isLeftAssociative(_input.LT(1))}? operator operatorExpression
  | <assoc=right> operatorExpression {getPrecedence(_input.LT(1)) == 1}? operator operatorExpression
  ;

functionCallExpression
  : identifier (closedExpression)+
  ;

expression
  : closedExpression
  | operatorExpression
  | functionCallExpression
  ;

functionNameIdentifier
  : identifier
  ;

argumentType
  : type
  ;

functionDefinition
  : functionNameIdentifier COLON (argumentType DEFINITION_ARG_SEPARATOR)* type
    functionNameIdentifier (identifier)* EQ expression DEFINITION_DELIM
  ;

operatorPrecedence
  : OPERATOR_PRECEDENCE
  ;

operatorNameIdentifier
  : operator
  ;

operatorDefinition
  : LPAREN operatorNameIdentifier operatorPrecedence RPAREN COLON argumentType DEFINITION_ARG_SEPARATOR argumentType DEFINITION_ARG_SEPARATOR type
    LPAREN operatorNameIdentifier RPAREN identifier identifier EQ expression DEFINITION_DELIM
  ;

file
  : (functionDefinition | operatorDefinition)* EOF
  ;
