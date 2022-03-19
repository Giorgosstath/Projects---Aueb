library ieee;
use ieee.std_logic_1164.all;

entity myOR2 is
	port(in1,in2: in std_logic;
			out1: out std_logic);
end myOR2;

architecture behav of myOR2 is
begin
	out1 <= in1 OR in2;
end behav;