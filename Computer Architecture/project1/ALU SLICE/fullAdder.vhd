library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_signed.all;

entity fullAdder is
	port(in1,in2,cin:in std_logic;
			sum,cout: out std_logic);
end fullAdder;

architecture struct of fullAdder is

component myAND2
	port(in1,in2: in std_logic;
			out1: out std_logic);
end component;

component myOR3
	port(in1,in2,in3: in std_logic;
			out1: out std_logic);
end component;

component myXOR3
	port(in1,in2,in3: in std_logic;
			out1: out std_logic);
end component;

signal S1,S2,S3: std_logic;

begin
	V0: myXOR3 port map(in1,in2,cin,sum);
	V1: myAND2 port map (in1,in2,S1);
	V2: myAND2 port map (in1,cin,S2);
	V3: myAND2 port map (in2,cin,S3);
	V4: myOR3 port map (S1,S2,S3,cout);
end struct;