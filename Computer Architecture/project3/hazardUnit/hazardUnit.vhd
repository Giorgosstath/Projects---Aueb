--Stathopoulos Georgios 3170152
--Panteleimon Ntoulis 3170124

library ieee;
use ieee.std_logic_1164.all;

--decides if an instruction must be flushed if a branch or jump happened before.
entity hazardUnit is
	port(isJR, isJump, wasJump, mustBranch : in std_logic;
			flush, wasJumpOut : out std_logic;
			JRopCode : out std_logic_vector(1 downto 0));
end hazardUnit;

architecture behavior of hazardUnit is
begin
	process(isJR, isJump, wasJump, mustBranch)
	begin
		flush <= '0';
		
		if (isJR ='1' or isJump = '1' or wasJump = '1' or mustBranch = '1') then
			flush <= '1';
		end if;
		
		if (isJump = '1' or isJR = '1') then
			JRopCode <= "01";
		elsif (mustBranch = '1') then
			JRopCode <= "10";
		else
			JRopCode <= "00";
		end if;
	end process;
end architecture behavior;