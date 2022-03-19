-- Stathopoulos Georgios 3170152
-- Ntoulis Panteleimon 3170124


library ieee;
use ieee.std_logic_1164.all;

entity myXOR3 is
	port(in1,in2,in3: in std_logic;
			out1: out std_logic);
end myXOR3;

architecture behavior of myXOR3 is
begin
	out1 <= in1 XOR in2 XOR in3;
end behavior;