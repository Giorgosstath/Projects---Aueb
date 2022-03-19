--Stathopoulos Georgios 3170152
--Panteleimon Ntoulis 3170124

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
--component declaration
   component reg is 
      Port (Input,Clock,Enable: IN STD_LOGIC ; 
            Output : OUT STD_LOGIC);
   end component;
--making 16bit register from 1bit registers
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