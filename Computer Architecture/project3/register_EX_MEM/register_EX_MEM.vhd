--Stathopoulos Georgios 3170152
--Panteleimon Ntoulis 3170124

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity register_EX_MEM is 
	generic ( 
		n : INTEGER :=16;
		adressSize : INTEGER :=3
		);
		port (
			clock,isLW,WriteEnable,ReadDigit,PrintDigit : IN std_logic;
			R2Reg,Result: IN std_logic_vector(n-1 downto 0);
			RegAd: IN std_logic_vector(adressSize-1 downto 0);
			-----------------------
			isLW_EXMEM,WriteEnable_EXMEM,ReadDigit_EXMEM,PrintDigit_EXMEM : OUT std_logic;
			R2Reg_EXMEM, Result_EXMEM: OUT std_logic_vector(n-1 downto 0);
			RegAD_EXMEM : OUT std_logic_vector(adressSize-1 downto 0)
			);
end register_EX_MEM;

architecture behavior of register_EX_MEM is 
begin

pc: process(clock)
begin
	if clock='1' then
		RegAD_EXMEM <= RegAD;
		R2Reg_EXMEM <= R2Reg;
		Result_EXMEM <= Result;
		isLW_EXMEM <= isLW;
		WriteEnable_EXMEM <= WriteEnable;
		ReadDigit_EXMEM <= ReadDigit;
		PrintDigit_EXMEM <= PrintDigit;
	end if;
end process pc;

end architecture behavior;