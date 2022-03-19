--components for cpu
library ieee;
use ieee.std_logic_1164.all;

entity mux2to1 is
	port(DATAA, DATAB: in std_logic_vector(15 downto 0);
			SEL : in std_logic;
			OUT0 : out std_logic_vector(15 downto 0));
end mux2to1;

architecture behavior of mux2to1 is
begin
	process(DATAA, DATAB, SEL)
	begin
		if (SEL = '0') then
			OUT0 <= DATAA;
		else
			OUT0 <= DATAB;
		end if;
	end process;
end architecture behavior;
---------------------------------------

library ieee;
use ieee.std_logic_1164.all;

entity trapUnit is
	port(opCode: in std_logic_vector(3 downto 0);
			EOR : out std_logic);
end trapUnit;

architecture behavior of trapUnit is
begin
	process(opCode)
	begin
		if (opCode = "1110") then
			EOR <= '1';
		else
			EOR <= '0';
		end if;
	end process;
end architecture behavior;
----------------------------------------------

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY JRSelector IS
generic (
	n : integer :=16 
	);
PORT (--select address that will be sent to PC based on JRopcode
	jumpAD,branchAd,PCP2AD: IN STD_LOGIC_VECTOR(n-1 DOWNTO 0);
	JRopcode: IN STD_LOGIC_VECTOR(1 DOWNTO 0);
	result: OUT STD_LOGIC_VECTOR(n-1 DOWNTO 0)
	);
END JRSelector;

ARCHITECTURE Behavior OF JRSelector IS
BEGIN
 --OPCODE:
 --00: +2 apo to outPc tou IF_ID
 -- 01: JumpADd
 --10: BranchAd
 PROCESS (JRopcode)
 BEGIN --mux4-1
	case JRopcode is
		when "00" => 
			result <= PCP2AD;
		when "01" =>
			result <= jumpAD;
		when "10" => 
			result <= branchAd;
		when others => result <= PCP2AD;
	end case;
 END PROCESS;
END Behavior;

---------------------------------------------------------------------

LIBRARY ieee;
USE ieee.std_logic_1164.all;

ENTITY myOR IS
PORT (in1, in2 : in std_logic;
		out1 : out std_logic);
END myOR;

ARCHITECTURE BEHAVIOR OF myOR IS
BEGIN
	out1 <= in1 or in2;
END ARCHITECTURE BEHAVIOR;

----------------------------------------------------------


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

-------------------------------------------------------------------------


library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity register_MEM_WB is 
	generic (
		n : INTEGER := 16;
		addressSize : INTEGER :=3
		);
		port( Result : IN std_logic_vector(n-1 downto 0);
		RegAD : IN std_logic_vector(addressSize-1 downto 0);
		clk: IN std_logic;
		writeData : OUT std_logic_vector(n-1 downto 0);
		writeAD: OUT std_logic_vector(addressSize-1 downto 0)
		);
end register_MEM_WB;

architecture behavior of register_MEM_WB is 
begin

pc: process(clk)
begin	
	if clk='1' then --rising edge
		writeData <= Result;
		writeAD <= RegAD;
	end if;
end process pc;

end architecture behavior;

-------------------------------------------------------------

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
-------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity register_IF_ID is 
	generic ( 
		n : INTEGER :=16 
		);
		port (
			inPC,inInstruction : IN std_logic_vector(n-1 downto 0);
			clock,IF_Flush,IF_ID_ENABLE : IN std_logic;
			-----------
			outPC,outInstruction : OUT std_logic_vector(n-1 downto 0)
			);
end register_IF_ID;

architecture behavior of register_IF_ID is
begin
pc: process(clock,IF_Flush,IF_ID_ENABLE)
begin	
	if clock='1' and IF_ID_ENABLE='1' then
		outPC <= std_logic_vector(unsigned(inPC) + 2); --go to next instruction
		outInstruction <= inInstruction;
	elsif clock='1' and IF_Flush='1' then  --clear data
		outPC <=(OTHERS => '0');
		outInstruction <= (OTHERS => '0');
	end if;
end process pc;

end architecture behavior;
-------------------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

entity Selector is
	generic( n: INTEGER := 16);
	port(Reg, Memory, WriteBack: in std_logic_vector(n-1 downto 0);
			Operation: in std_logic_vector(1 downto 0);
			Output: out std_logic_vector(n-1 downto 0));
end Selector;

architecture behavior of Selector is
begin
	with Operation select
		Output <= Reg when "00",
					WriteBack when "01",
					Memory when "10",
					"0000000000000000" when "11";
end architecture behavior;

-------------------------------------------------------------------

Library ieee;
USE ieee.STD_LOGIC_1164.all;

ENTITY AluControl is 
port (opCode : IN STD_LOGIC_VECTOR(3 DOWNTO 0);
func : IN STD_LOGIC_VECTOR(2 DOWNTO 0);
output :OUT STD_LOGIC_VECTOR(3 DOWNTO 0)
);
END AluControl;

architecture behavioral Of AluControl IS

begin 
process(opCode,func) begin 
   case opCode is 
      when "0000" => 
	    output(3) <='0';
        output(2 downto 0) <= func(2 downto 0);
        when others => output <=opCode;
    end case;
end process;	
end behavioral;  
-----------------------------------------------
Library ieee;
Use ieee.STD_Logic_1164.all;
ENTITY signExtender IS
  generic (
      n : integer :=16;
      k: integer :=6
     );
    port ( 
      immediate : in std_logic_vector(k-1 downto 0);
      extended : out std_logic_vector(n-1 downto 0)
      );	  
	  END signExtender;
	
Architecture Logicfunc Of signExtender IS
begin 
     extended <=(n-1 downto k => immediate(k-1)) & (immediate);
END Logicfunc;

--------------------------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;

entity myXOR3 is
	port(in1,in2,in3: in std_logic;
			out1: out std_logic);
end myXOR3;

architecture behavior of myXOR3 is
begin
	out1 <= in1 XOR in2 XOR in3;
end behavior;

---------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

entity fullAdder is
	port(in1,in2,cin:in std_logic;
			sum,cout: out std_logic);
end fullAdder;

architecture struct of fullAdder is

component myAND2
	port(in1,in2: in std_logic;
			out1: out std_logic);
end component;

component myOR3
	port(in1,in2,in3: in std_logic;
			out1: out std_logic);
end component;

component myXOR3
	port(in1,in2,in3: in std_logic;
			out1: out std_logic);
end component;

signal S1,S2,S3: std_logic;

begin
	V0: myXOR3 port map(in1,in2,cin,sum);
	V1: myAND2 port map (in1,in2,S1);
	V2: myAND2 port map (in1,cin,S2);
	V3: myAND2 port map (in2,cin,S3);
	V4: myOR3 port map (S1,S2,S3,cout);
end struct;
----------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;

entity myOR3 is
	port(in1,in2,in3: in std_logic;
			out1: out std_logic);
end myOR3;
-----------------------------

library ieee;
use ieee.std_logic_1164.all;

entity myMUX3 is
	port(in1,in2,in3,sel1,sel2: in std_logic;
			out1: out std_logic);
end myMUX3;

architecture struct of myMUX3 is

component myMUX1
	port(in1,in2,sel: in std_logic;
			out1: out std_logic);
end component;

signal f: std_logic;

begin
	V0: myMUX1 port map (in1,in2,sel1,f);
	V1: myMUX1 port map (f,in3,sel2,out1);
end struct;
----------------------------------------------
library ieee;
use ieee.std_logic_1164.all;

entity myMUX1 is
	port(in1,in2,sel: in std_logic;
			out1: out std_logic);
end myMUX1;

architecture behav of myMUX1 is
begin
	out1 <= (in1 AND NOT sel) OR (in2 AND sel);
end behav;

---------------------------------

library ieee;
use ieee.std_logic_1164.all;


entity myAND2 is
	port(in1,in2: in std_logic;
			out1: out std_logic);
end myAND2;

architecture struct of myAND2 is
begin
	out1 <= in1 AND in2;
end struct;


---------------------------------------
library ieee;
use ieee.std_logic_1164.all;

entity myALU1 is
	port(Input1, Input2, Carryin, Ainv, Binv, Op1, Op2 : in std_logic;
			Output, Carryout: out std_logic);
end myALU1;

architecture struct of myALU1 is

component fullAdder
	port(in1,in2,cin:in std_logic;
			sum,cout: out std_logic);
end component;

component myMux1
	port(in1,in2,sel: in std_logic;
			out1: out std_logic);
end component;

component myMux3
	port(in1,in2,in3,sel1,sel2: in std_logic;
			out1: out std_logic);
end component;

component myOR2
	port(in1,in2: in std_logic;
			out1: out std_logic);
end component;

component myAND2
	port(in1,in2: in std_logic;
			out1: out std_logic);
end component;

signal f1,f2,f3,f4,f5 : std_logic;
begin
	V0: myMux1 port map (Input1, NOT Input1, Ainv, f1);	
	V1: myMux1 port map (Input2, NOT Input2, Binv, f2);
	V2: myAND2 port map (f1,f2,f3);
	V3: myOR2 port map (f1,f2,f4);
	V4: fullAdder port map (f1,f2, Carryin, f5, Carryout);
	V5: myMux3 port map (f3,f4,f5,Op1,Op2, Output);
end struct;

-----------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_signed.all;
use ieee.numeric_std.all;

entity myALU is
		port(Input1, Input2: in std_logic_vector(15 downto 0);
				Carryin: in std_logic;
				Operation: in std_logic_vector(3 downto 0);
				Carryout: out std_logic;
				Output: out std_logic_vector(15 downto 0));
end myALU;

architecture struct of myALU is
 component myALU1
	port(Input1, Input2, Carryin, Ainv, Binv, Op1, Op2 : in std_logic;
			Output, Carryout: out std_logic);
end component;

signal CarryOuts, tempOutput : std_logic_vector(15 downto 0);
signal COUT : std_logic;

constant ADD : std_logic_vector(3 downto 0 ) := "0010";
constant SUB : std_logic_vector(3 downto 0 ) := "0011";
constant AND_F : std_logic_vector(3 downto 0 ) := "0000";
constant OR_F : std_logic_vector(3 downto 0 ) := "0001";
constant GEQ : std_logic_vector(3 downto 0 ) := "0101";
constant NOT_F : std_logic_vector(3 downto 0 ) := "0110";

begin

V0: myALU1 port map (Input1(0),Input2(0),Carryin, Operation(3), Operation(2), Operation(0), Operation(1), tempOutput(0), CarryOuts(0));
V1: myALU1 port map (Input1(1),Input2(1),CarryOuts(0), Operation(3), Operation(2), Operation(0), Operation(1), tempOutput(1), CarryOuts(1));
V2: myALU1 port map (Input1(2),Input2(2),CarryOuts(1), Operation(3), Operation(2), Operation(0), Operation(1), tempOutput(2), CarryOuts(2));
V3: myALU1 port map (Input1(3),Input2(3),CarryOuts(2), Operation(3), Operation(2), Operation(0), Operation(1), tempOutput(3), CarryOuts(3));
V4: myALU1 port map (Input1(4),Input2(4),CarryOuts(3), Operation(3), Operation(2), Operation(0), Operation(1), tempOutput(4), CarryOuts(4));
V5: myALU1 port map (Input1(5),Input2(5),CarryOuts(4), Operation(3), Operation(2), Operation(0), Operation(1), tempOutput(5), CarryOuts(5));
V6: myALU1 port map (Input1(6),Input2(6),CarryOuts(5), Operation(3), Operation(2), Operation(0), Operation(1), tempOutput(6), CarryOuts(6));
V7: myALU1 port map (Input1(7),Input2(7),CarryOuts(6), Operation(3), Operation(2), Operation(0), Operation(1), tempOutput(7), CarryOuts(7));
V8: myALU1 port map (Input1(8),Input2(8),CarryOuts(7), Operation(3), Operation(2), Operation(0), Operation(1), tempOutput(8), CarryOuts(8));
V9: myALU1 port map (Input1(9),Input2(9),CarryOuts(8), Operation(3), Operation(2), Operation(0), Operation(1), tempOutput(9), CarryOuts(9));
V10: myALU1 port map (Input1(10),Input2(10),CarryOuts(9), Operation(3), Operation(2), Operation(0), Operation(1), tempOutput(10), CarryOuts(10));
V11: myALU1 port map (Input1(11),Input2(11),CarryOuts(10), Operation(3), Operation(2), Operation(0), Operation(1), tempOutput(11), CarryOuts(11));
V12: myALU1 port map (Input1(12),Input2(12),CarryOuts(11), Operation(3), Operation(2), Operation(0), Operation(1), tempOutput(12), CarryOuts(12));
V13: myALU1 port map (Input1(13),Input2(13),CarryOuts(12), Operation(3), Operation(2), Operation(0), Operation(1), tempOutput(13), CarryOuts(13));
V14: myALU1 port map (Input1(14),Input2(14),CarryOuts(13), Operation(3), Operation(2), Operation(0), Operation(1), tempOutput(14), CarryOuts(14));
V15: myALU1 port map (Input1(15),Input2(15),CarryOuts(14), Operation(3), Operation(2), Operation(0), Operation(1), tempOutput(15), COUT);

process(Operation)

	variable temp: std_logic_vector(15 downto 0);
	variable temp3,temp4: std_logic;
begin
	case Operation is
		when ADD =>
			temp:= tempOutput;
			Carryout <= COUT;
		when SUB =>
			temp:= Input1-Input2;
		when AND_F =>
			temp:= tempOutput;
		when OR_F =>
			temp:= tempOutput;
		when GEQ =>
			temp:= (OTHERS => NOT(Input1(15)));
			temp3:=NOT(Input1(15));
			Carryout <= temp3;
		when NOT_F =>
			temp:= (OTHERS => '0');
			if(Input1 = (15 downto 0 => '0')) then
				temp(0):='1';
			end if;
		when others =>
			temp:= Input1-Input2;
	end case;
Output <= temp;
end process;
end struct;

-----------------------------------------------------

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

----------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;

entity Controller is
	port(opCode : in std_logic_vector(3 downto 0);
			func : in std_logic_vector(2 downto 0);
			flush : in std_logic;
			isMPFC, isJumpD, isReadDigit, isPrintDigit, isR, isLW, isSW, isBranch, isJR : out std_logic);
end Controller;

architecture behavior of Controller is
begin
	get_result : process(flush,func,opCode) begin
		--if flush="1"
		isR <= '0';
		isMPFC <= '0';
		isLW <= '0';
		isSW <= '0';
		isBranch <= '0';
		isReadDigit <= '0';
		isPrintDigit <= '0';
		isJumpD <= '0';
		isJR <= '0';
		--end if
		
		if flush = '0' then
			case opCode is
				when "0000" =>
					isR <='1';
					if (func = "111") then
						isMPFC <= '1';
					end if;
				when "0001" =>
					isLW <= '1';
				when "0010" =>
					isSW <= '1';
				when "0100" =>
					isBranch <= '1';
				when "0110" =>
					isReadDigit <= '1';
				when "0111" =>
					isPrintDigit <= '1';
				when "1111" =>
					isJumpD <= '1';
				when "1101" =>
					isJR <= '1';
				when others =>
					isR <='0';
			end case;
		end if;
	end process;
end architecture behavior;
---------------------------------------------

library IEEE;
use IEEE.std_logic_1164.all;
use IEEE.numeric_std.all;

entity register_ID_EX is 
	generic (
		n : INTEGER := 16;
		adressSize : INTEGER :=3
		);
		port (--inputs: instruction type,alu controller, register file, sign etender,IF_ID register
			clock,isEOR,wasJumpOut,isJump,isJR,isBranch,isR,isMFPC,isLW,isSW,isReadDigit,isPrintDigit : in std_logic;
			ALUFunc : in STD_LOGIC_VECTOR(3 DOWNTO 0);
			R1Reg,R2Reg,immediate16 : IN std_logic_vector(n-1 downto 0);
			R2AD,R1AD : in std_logic_vector(adressSize-1 downto 0);
			jumpShortAddr : IN STD_LOGIC_VECTOR(11 DOWNTO 0);
			----------------------------
			isEOR_IDEX,wasJumpOut_IDEX,isJump_IDEX,isJR_IDEX,isBranch_IDEX,isR_IDEX,isMFPC_IDEX,isLW_IDEX,isSW_IDEX,isReadDigit_IDEX,isPrintDigit_IDEX: out STD_LOGIC;
			ALUFunc_IDEX: out STD_LOGIC_VECTOR(3 DOWNTO 0);
			R1Reg_IDEX,R2Reg_IDEX,immediate16_IDEX: OUT STD_LOGIC_VECTOR(n-1 downto 0);
			R2AD_IDEX,R1AD_IDEX : OUT STD_LOGIC_VECTOR(adressSize-1 downto 0);
			jumpShortAddr_IDEX: OUT STD_LOGIC_VECTOR(11 DOWNTO 0)
			);
end register_ID_EX;

architecture behavior of register_ID_EX is 
begin

pc: process(clock)
begin	
	if clock='1' then
		isEOR_IDEX<= isEOR;
		wasJumpOut_IDEX <= wasJumpOut;
		isJump_IDEX <=isJump;
		isJR_IDEX <= isJR;
		isBranch_IDEX <= isBranch;
		isR_IDEX <= isR;
		isMFPC_IDEX <= isMFPC;
		ALUFunc_IDEX <= ALUFunc;
		R1Reg_IDEX <= R1Reg;
		R2Reg_IDEX <= R2Reg;
		immediate16_IDEX <= immediate16;
		R2AD_IDEX <= R2AD;
		R1AD_IDEX <= R1AD;
		jumpShortAddr_IDEX <= jumpShortAddr;
		isReadDigit_IDEX <= isPrintDigit;
		isLW_IDEX <= isLW;
		isSW_Idex <= isSW;
	end if;
end process pc;
end architecture behavior;

--------------------------------------------------------------

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

----------------------------------------------------------
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


-------------------------------------------
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

--------------------------------------------------------------

LIBRARY ieee;
Use ieee.STD_LOGIC_1164.all;
ENTITY regFile IS 
generic (
       n: integer :=16; 
       k: INTEGER :=3;
       regNum: INTEGER :=8
      );
     port (
          Clock : in std_logic;
          Write1: in std_logic_vector (n-1 downto 0);
          Write1AD,Read1AD,Read2AD: in std_logic_vector (k-1 downto 0);
          Read1,Read2 : out std_logic_vector (n-1 downto 0);
          OUTall: out std_logic_vector(n*regNum-1 downto 0)
        );
END regFile;

ARCHITECTURE LogicFunc of regFile is
--component declaration
     component reg0 is 
	    PORT ( Input : IN STD_LOGIC_VECTOR(n-1 DOWNTO 0);
		       Enable, Clock : IN STD_LOGIC;
			   Output : Out STD_LOGIC_VECTOR(n-1 DOWNTO 0));
	    end component;
		component reg16 is 
		  PORT(Input: IN STD_LOGIC_VECTOR(n-1 DOWNTO 0);
		       Enable,Clock: IN STD_LOGIC;
			   Output: OUT STD_LOGIC_VECTOR(n-1 DOWNTO 0));
		end component;
        component decode3to8 is 
               Port(Input :IN STD_LOGIC_VECTOR(k-1 DOWNTO 0);
                    Output :OUT STD_LOGIC_VECTOR(regNum-1 DOWNTO 0)
                   );
        end component;
        component mux8 is 
               Port (Input0,INput1,Input2,Input3,Input4,Input5,Input6,Input7 :IN STD_LOGIC_VECTOR(n-1 DOWNTO 0);
                    Choice : IN STD_LOGIC_VECTOR(k-1 DOWNTO 0);
                    Output :OUT STD_LOGIC_VECTOR(n-1 DOWNTO 0)
                    );
        end component;

        signal enableSigs: STD_LOGIC_VECTOR(regNum-1 DOWNTO 0);
        signal re0,re1,re2,re3,re4,re5,re6,re7 : STD_LOGIC_VECTOR(n-1 DOWNTO 0);	

BEGIN 
      G0: decode3to8 port map(Write1AD,enableSigs);
      
      G1: reg0 port map(Write1,enableSigs(0),Clock,re0);
 	  G2: reg16 port map(Write1,enableSigs(1),Clock,re1);
	  G3: reg16 port map(Write1,enableSigs(2),Clock,re2);	
	  G4: reg16 port map(Write1,enableSigs(3),Clock,re3);
	  G5: reg16 port map(Write1,enableSigs(4),Clock,re4);
	  G6: reg16 port map(Write1,enableSigs(5),Clock,re5);
	  G7: reg16 port map(Write1,enableSigs(6),Clock,re6);
	  G8: reg16 port map(Write1,enableSigs(7),Clock,re7);
	  
	  G9: mux8 port map(re0,re1,re2,re3,re4,re5,re6,re7,Read1AD,Read1);
	  G10: mux8 port map(re0,re1,re2,re3,re4,re5,re6,re7,Read2AD,Read2);
	  
	  OUTall <=re7 & re6 & re5 & re4 & re3 & re2 & re1 & re0;
END LogicFunc;
--------------------------------------------------
