--Stathopoulos Georgios 3170152
--Panteleimon Ntoulis 3170124

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY reg0 IS 
GENERIC ( 
         n : INTEGER :=16 
        );
     PORT (Input : IN STD_LOGIC_VECTOR(n-1 DOWNTO 0);
           Enable,Clock : IN STD_LOGIC;
           Output: OUT STD_LOGIC_VECTOR(n-1 DOWNTO 0));
END reg0;

ARCHITECTURE Behavior OF reg0 IS
BEGIN 
  Output <=(OTHERS => '0');
END Behavior;