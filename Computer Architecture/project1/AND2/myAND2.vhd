-- Stathopoulos Georgios 3170152
-- Ntoulis Panteleimon 3170124

library ieee;
use ieee.std_logic_1164.all;

entity myAND2 is
	port(in1,in2: in std_logic;
			out1: out std_logic);
end myAND2;

architecture struct of myAND2 is
begin
	out1 <= in1 AND in2;
end struct;