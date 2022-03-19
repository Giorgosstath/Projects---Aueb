library ieee;
use ieee.std_logic_1164.all;

entity myMUX1 is
	port(in1,in2,sel: in std_logic;
			out1: out std_logic);
end myMUX1;

architecture behav of myMUX1 is
begin
	out1 <= (in1 AND NOT sel) OR (in2 AND sel);
end behav;