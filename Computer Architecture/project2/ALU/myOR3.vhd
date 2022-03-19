library ieee;
use ieee.std_logic_1164.all;

entity myOR3 is
	port(in1,in2,in3: in std_logic;
			out1: out std_logic);
end myOR3;

architecture struc of myOR3 is
begin
	out1 <= in1 OR in2 OR in3;
end struc;