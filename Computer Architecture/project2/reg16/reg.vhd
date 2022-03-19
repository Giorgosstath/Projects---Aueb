LIBRARY ieee;
Use ieee.STD_Logic_1164.all;
ENTITY reg IS
Port (Input,Clock,Enable :IN STD_LOGIC;
		Output : OUT STD_LOGIC);
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
