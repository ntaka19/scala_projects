%%

%type Tokens.Token   // トークンの型
DIGITS=0|[1-9][0-9]*
NONDIGITS = [0][0-9]+
ORIGINAL_DIGITS = [0-9]+
%% 

"if"                                 { Tokens.IF }
[a-z][a-z0-9]*                       { Tokens.ID(yytext()) }
`if`				     {Tokens.ID("if")}   
 {DIGITS}                             { Tokens.NUM(yytext().toInt) }
 {NONDIGITS}			      {Base.error()}
{ORIGINAL_DIGITS}"."[0-9]*|[0-9]*"."{ORIGINAL_DIGITS}  { Tokens.REAL(yytext().toDouble) }
[\ \t\n]+                            { yylex() }
<<EOF>>                              { Tokens.EOF }
