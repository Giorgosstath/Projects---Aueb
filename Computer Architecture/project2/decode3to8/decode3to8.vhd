--Stathopoulos Georgios 3170152
--Panteleimon Ntoulis 3170124

LIBRARY ieee;
Use ieee.STD_LOGIC_1164.all;
ENTITY decode3to8 IS
	Port (Input : IN STD_LOGIC_VECTOR(2 DOWNTO 0);
      Output :OUT STD_LOGIC_VECTOR(7 DOWNTO 0)
     );
END decode3to8;

Architecture Logicfunc Of decode3to8 IS
begin
      with Input select 
        Output <= "00000001" when "000",
                  "00000010" when "001",
                  "00000100" when "010",
                  "00001000" when "011",
                  "00010000" when "100",
                  "00100000" when "101",	
                  "01000000" when "110",
                  "10000000" when "111",		
                  "00000000" when OTHERS;
END Logicfunc;