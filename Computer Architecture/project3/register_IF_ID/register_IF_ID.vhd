--Stathopoulos Georgios 3170152
--Panteleimon Ntoulis 3170124

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity register_IF_ID is 
	generic ( 
		n : INTEGER :=16 
		);
		port (
			inPC,inInstruction : IN std_logic_vector(n-1 downto 0);
			clock,IF_Flush,IF_ID_ENABLE : IN std_logic;
			-----------
			outPC,outInstruction : OUT std_logic_vector(n-1 downto 0)
			);
end register_IF_ID;

architecture behavior of register_IF_ID is
begin
pc: process(clock,IF_Flush,IF_ID_ENABLE)
begin	
	if clock='1' and IF_ID_ENABLE='1' then
		outPC <= std_logic_vector(unsigned(inPC) + 2); --go to next instruction
		outInstruction <= inInstruction;
	elsif clock='1' and IF_Flush='1' then  --clear data
		outPC <=(OTHERS => '0');
		outInstruction <= (OTHERS => '0');
	end if;
end process pc;

end architecture behavior;