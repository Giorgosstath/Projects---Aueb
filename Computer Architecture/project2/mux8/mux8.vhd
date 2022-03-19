--Stathopoulos Georgios 3170152
--Panteleimon Ntoulis 3170124

LIBRARY ieee;
Use ieee.STD_LOGIC_1164.all;
ENTITY mux8 IS 
generic ( 
        n : integer :=16
		);
	Port (Input0,Input1,Input2,Input3,Input4,Input5,Input6,Input7 : IN STD_LOGIC_VECTOR(n-1 DOWNTO 0);
		Choice: IN STD_LOGIC_VECTOR(2 DOWNTO 0);
		Output :OUT STD_LOGIC_VECTOR(n-1 DOWNTO 0)
		);
END mux8;

Architecture Logicfunc Of mux8 IS
begin
--selecting Output with a 3bit signal
     with Choice select
     Output <=Input0 when "000",
     Input1 when "001",
     Input2 when "010",
     Input3 when "011",
     Input4 when "100",
     Input5 when "101",
     Input6 when "110",
     Input7 when "111",
     "0000000000000000" when OTHERS;
END Logicfunc;