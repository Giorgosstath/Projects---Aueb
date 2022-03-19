--file with the components the register file will need
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
                  "10000000" when "111"	,	
                  "00000000" when OTHERS;
END Logicfunc;




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



LIBRARY ieee;
Use ieee.STD_Logic_1164.all;
ENTITY reg IS
	Port (Input,Clock,Enable :IN STD_LOGIC;
		Output:OUT STD_LOGIC);
END reg;
Architecture Logicfunc Of reg IS
signal P1,P2,P3,P4,five,six,afterClock : std_logic;
begin
  P3<=P1 NAND p4;
  P1<=afterClock NAND p3;
  P2<=NOT(afterClock AND P1 AND P4);
  P4<=input NAND P2;
  five <=six NAND P1;
  six<=P2 NAND five;
  afterClock<=Clock AND Enable;
  Output<=five;
END Logicfunc;




LIBRARY ieee;
Use ieee.STD_Logic_1164.all;
ENTITY reg16 IS 
GENERIC ( 
         n: INTEGER :=16
		 );
Port (Input: IN STD_LOGIC_VECTOR(n-1 DOWNTO 0);
      Enable,Clock : IN STD_LOGIC;
      Output : OUT STD_LOGIC_VECTOR(n-1 DOWNTO 0) 
      );
END reg16;

Architecture Logicfunc Of reg16 IS
   component reg is 
      Port (Input,Clock,Enable: IN STD_LOGIC ; 
            Output : OUT STD_LOGIC);
   end component;
begin 
   G0: reg port map(Input(0), Clock, Enable,Output(0)); 
   G1: reg port map(Input(1), Clock, Enable,Output(1));  
   G2: reg port map(Input(2), Clock, Enable,Output(2));   
   G3: reg port map(Input(3), Clock, Enable,Output(3));
   G4: reg port map(Input(4), Clock, Enable,Output(4));
   G5: reg port map(Input(5), Clock, Enable,Output(5));
   G6: reg port map(Input(6), Clock, Enable,Output(6));
   G7: reg port map(Input(7), Clock, Enable,Output(7));
   G8: reg port map(Input(8), Clock, Enable,Output(8));
   G9: reg port map(Input(9), Clock, Enable,Output(9));
   G10: reg port map(Input(10), Clock, Enable,Output(10));
   G11: reg port map(Input(11), Clock, Enable,Output(11));
   G12: reg port map(Input(12), Clock, Enable,Output(12));
   G13: reg port map(Input(13), Clock, Enable,Output(13));
   G14: reg port map(Input(14), Clock, Enable,Output(14));
   G15: reg port map(Input(15), Clock, Enable,Output(15));
END Logicfunc;   