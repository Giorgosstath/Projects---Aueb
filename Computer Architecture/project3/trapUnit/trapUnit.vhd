--Stathopoulos Georgios 3170152
--Panteleimon Ntoulis 3170124

library ieee;
use ieee.std_logic_1164.all;

entity trapUnit is
	port(opCode: in std_logic_vector(3 downto 0);
			EOR : out std_logic);
end trapUnit;

architecture behavior of trapUnit is
begin
	process(opCode)
	begin
		if (opCode = "1110") then
			EOR <= '1';
		else
			EOR <= '0';
		end if;
	end process;
end architecture behavior;