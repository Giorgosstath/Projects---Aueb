-- Stathopoulos Georgios 3170152
-- Ntoulis Panteleimon 3170124

library ieee;
use ieee.std_logic_1164.all;

entity myMUX3 is
	port(in1,in2,in3,sel1,sel2: in std_logic;
			out1: out std_logic);
end myMUX3;

architecture struct of myMUX3 is

component myMUX1
	port(in1,in2,sel: in std_logic;
			out1: out std_logic);
end component;

signal f: std_logic;

begin
	V0: myMUX1 port map (in1,in2,sel1,f);
	V1: myMUX1 port map (f,in3,sel2,out1);
end struct;