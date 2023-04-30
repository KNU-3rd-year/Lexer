Program MyProgram;

var
	a : string;
	b, b1 : integer;
	c : real;

(* Here the main program block starts *)
begin
	// one line
	{ multi
	line
	comment
	}

	a := 'Hello, Pascal!';
	b := 123;
	b := $A123B; // integer in base16
	c := 123.123;
	c := 123e5;
	c := 123e-5;
	c := c / b;

	writeLn(b, ' ', b, 8);
    if (b >= 42) then
   		b >> 1;
   	else
   		b = a|b;
   	while (true and not false)
   	    writeln ('Hello, world!');
end.