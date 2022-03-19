--Stathopoulos Georgios 3170152
--Panteleimon Ntoulis 3170124

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity register_MEM_WB is 
	generic (
		n : INTEGER := 16;
		addressSize : INTEGER :=3
		);
		port( Result : IN std_logic_vector(n-1 downto 0);
		RegAD : IN std_logic_vector(addressSize-1 downto 0);
		clk: IN std_logic;
		writeData : OUT std_logic_vector(n-1 downto 0);
		writeAD: OUT std_logic_vector(addressSize-1 downto 0)
		);
end register_MEM_WB;

architecture behavior of register_MEM_WB is 
begin

pc: process(clk)
begin	
	if clk='1' then --rising edge
		writeData <= Result;
		writeAD <= RegAD;
	end if;
end process pc;

end architecture behavior;