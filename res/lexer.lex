package edu.pjwstk.mherman.jps.parser;
  
import java_cup.runtime.Symbol;

import static edu.pjwstk.mherman.jps.parser.Symbols.*;
 


%%
%{ 
	private Symbol createToken(int id) {
		return new Symbol(id, yyline, yycolumn);
	}
	private Symbol createToken(int id, Object o) {
		return new Symbol(id, yyline, yycolumn, o);
	}
%}
 
%public
%class Lexer 
%cup
%line 
%column
%char
%eofval{
	return createToken(EOF);
%eofval}

INTEGER = [0-9]+
BOOLEAN = true|false
IDENTIFIER = [_a-zA-Z][0-9a-zA-Z]*
DOUBLE = [0-9]+\.[0-9]+
STRING = [\"][^\"]*[\"]
CHAR = [\'][^\"][\']
LineTerminator = \r|\n|\r\n 
WHITESPACE = {LineTerminator} | [ \t\f]
  
%% 
 
<YYINITIAL> {
	"+"						{ return createToken(PLUS				); }
	"-"						{ return createToken(MINUS				); }
	"*"						{ return createToken(MULTIPLY			); }
	"/"						{ return createToken(DIVIDE				); }
	"%"						{ return createToken(MODULO				); }
	"("						{ return createToken(LEFT_ROUND_BRACKET	); }
	")"						{ return createToken(RIGHT_ROUND_BRACKET); }
	"as"					{ return createToken(AS); }	
	","						{ return createToken(COMMA); }
	"bag"					{ return createToken(BAG); }
	"struct"				{ return createToken(STRUCT); }
	"=="					{ return createToken(EQUALS); }
	"!="					{ return createToken(NOT_EQUALS); }
	">"						{ return createToken(MORE); }
	"<"						{ return createToken(LESS); }
	">="					{ return createToken(MORE_OR_EQUAL); }
	"<="					{ return createToken(LESS_OR_EQUAL); }
	"all"					{ return createToken(ALL); }
	"any"					{ return createToken(ANY); }
	"where"					{ return createToken(WHERE); }
	"join"					{ return createToken(JOIN); }
	"!"						{ return createToken(NOT); }
	"and"					{ return createToken(AND); }
	"or"					{ return createToken(OR); }	
	"xor"					{ return createToken(XOR); }	
	"."						{ return createToken(DOT); }
	"min"					{ return createToken(MIN); }
	"max"					{ return createToken(MAX); }
	"sum"					{ return createToken(SUM); }
	"avg"					{ return createToken(AVG); }	
	"groupas"				{ return createToken(GROUPAS); }
	"minus"					{ return createToken(MINUS_FUNCTION); }
	"intersect"				{ return createToken(INTERSECT); }
	"unique"				{ return createToken(UNIQUE); }
	"in"					{ return createToken(IN); }

	{WHITESPACE} { }
	{INTEGER} {
		int val;
		try {
			val = Integer.parseInt(yytext());
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return createToken(INTEGER_LITERAL, new Integer(val));
	}
	{DOUBLE} {
		double val;
		try {
			val = Double.parseDouble(yytext());
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return createToken(DOUBLE_LITERAL, new Double(val));
	}
	{BOOLEAN} {
		boolean val;
		try {
			val = Boolean.parseBoolean(yytext());
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return createToken(BOOLEAN_LITERAL, new Boolean(val));
	}
	{STRING} {
		return createToken(STRING_LITERAL, yytext().substring(1, yytext().length()-1));
	} 
	{IDENTIFIER} {
		return createToken(IDENTIFIER_LITERAL, yytext());
	}
}
