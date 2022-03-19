library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_signed.all;

entity myALU1 is
	port(Input1, Input2, Carryin, Ainv, Binv, Op1, Op2 : in std_logic;
			Output, Carryout: out std_logic);
end myALU1;

architecture struct of myALU1 is

component fullAdder
	port(in1,in2,cin:in std_logic;
			sum,cout: out std_logic);
end component;

component myMux1
	port(in1,in2,sel: in std_logic;
			out1: out std_logic);
end component;

component myMux3
	port(in1,in2,in3,sel1,sel2: in std_logic;
			out1: out std_logic);
end component;

component myOR2
	port(in1,in2: in std_logic;
			out1: out std_logic);
end component;

component myAND2
	port(in1,in2: in std_logic;
			out1: out std_logic);
end component;

signal f1,f2,f3,f4,f5 : std_logic;
begin
	V0: myMux1 port map (Input1, NOT Input1, Ainv, f1);	
	V1: myMux1 port map (Input2, NOT Input2, Binv, f2);
	V2: myAND2 port map (f1,f2,f3);
	V3: myOR2 port map (f1,f2,f4);
	V4: fullAdder port map (f1,f2, Carryin, f5, Carryout);
	V5: myMux3 port map (f3,f4,f5,Op1,Op2, Output);
end struct;
