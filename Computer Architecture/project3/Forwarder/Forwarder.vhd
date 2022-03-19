--Stathopoulos Georgios 3170152
--Panteleimon Ntoulis 3170124

library ieee;
use ieee.std_logic_1164.all;

--optimaze pipeline in case of hazards (read from EX_MEM or MEM_WB registers)
entity Forwarder is
	generic(addr_size : INTEGER := 3);
	port(R1AD, R2AD, RegAD_EXMEM, RegAD_MEMWB : in std_logic_vector(addr_size-1 downto 0);
			S1, S2 : out std_logic_vector(1 downto 0));
end entity Forwarder;
	
architecture behavior of Forwarder is
begin
	process(RegAD_EXMEM, RegAD_MEMWB, R1AD, R2AD)
	begin
		S1 <= "00";  --select R1AD
		S2 <= "00";  --select R2AD
		
		if(R1AD = RegAD_EXMEM) then
			S1 <="10";   --select RegAD_EXMEM
		elsif (R1AD = RegAD_MEMWB) then
			S1 <="01";   --select RegAD_MEMWB
		end if;
		
		if(R2AD=RegAD_EXMEM) then
			S2 <="10";    --select RegAD_EXMEM
		elsif (R2AD = RegAD_MEMWB) then
			S2 <= "01";    --select RegAD_MEMWB
		end if;
	end process;
end architecture behavior;