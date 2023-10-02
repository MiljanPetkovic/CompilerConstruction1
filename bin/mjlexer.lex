package rs.ac.bg.etf.pp1; 

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT 

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }


"program"   { return new_symbol(sym.PROG, yytext());}
"break"   	{ return new_symbol(sym.BREAK, yytext());}
"findAny"	{ return new_symbol(sym.SEARCH, yytext());}
"continue"  { return new_symbol(sym.CONTINUE, yytext());}
"const"		{ return new_symbol(sym.CONST, yytext());}
"print" 	{ return new_symbol(sym.PRINT, yytext()); }
"return" 	{ return new_symbol(sym.RETURN, yytext()); }
"void" 		{ return new_symbol(sym.VOID, yytext()); }
"read"		{ return new_symbol(sym.READ, yytext()); }
"new"		{ return new_symbol(sym.NEW, yytext()); }
"true" 		{ return new_symbol(sym.BOOL_CONST, 1); }
"false" 	{ return new_symbol(sym.BOOL_CONST, 0); }
"'"."'"  	{ return new_symbol(sym.CHAR_CONST, new Character (yytext().charAt(1))); }
"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"=" 		{ return new_symbol(sym.EQUAL, yytext()); }
";" 		{ return new_symbol(sym.SEMI, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"(" 		{ return new_symbol(sym.LPAREN, yytext()); }
")" 		{ return new_symbol(sym.RPAREN, yytext()); }
"{" 		{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }
"[" 		{ return new_symbol(sym.LSBRACE, yytext()); }
"]"			{ return new_symbol(sym.RSBRACE, yytext()); }
"*"			{ return new_symbol(sym.STAR, yytext()); }
"-"			{ return new_symbol(sym.MINUS, yytext()); }
"/"			{ return new_symbol(sym.SLASH, yytext()); }
"%"			{ return new_symbol(sym.PERCENT, yytext()); }
"!"			{ return new_symbol(sym.EXCLAMATION, yytext()); }
"<"			{ return new_symbol(sym.LESS, yytext()); }
">"			{ return new_symbol(sym.GREATER, yytext()); }
"|"			{ return new_symbol(sym.VERTICALBAR, yytext()); }
"&"			{ return new_symbol(sym.AMPERSAND, yytext()); }
"."			{ return new_symbol(sym.DOT, yytext()); }



"//" {yybegin(COMMENT);}
<COMMENT> . {yybegin(COMMENT);}
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

[0-9]+  { return new_symbol(sym.NUMBER, new Integer (yytext())); }
([a-z]|[A-Z])[a-zA-Z0-9_]* 	{return new_symbol (sym.IDENT, yytext()); }

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)); }
 



