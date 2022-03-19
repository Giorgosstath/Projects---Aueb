--Stathopoulos Georgios 3170152
--Panteleimon Ntoulis 3170124

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY reg16b IS --sends address at IF_ID register (pc register)
	PORT (Input : IN std_logic_vector(15 DOWNTO 0);
		Enable,Clock : IN STD_LOGIC;
		Output : OUT STD_LOGIC_VECTOR(15 DOWNTO 0)
		);
END reg16b;

ARCHITECTURE Behavior OF reg16b IS
BEGIN
	PROCESS (Clock)
	BEGIN
		IF Clock'Event AND Clock= '0'  THEN
			IF Enable ='1' THEN
				Output <= Input;
			END IF;
		END IF;
	END PROCESS;
END Behavior;