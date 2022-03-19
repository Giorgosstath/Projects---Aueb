--Stathopoulos Georgios 3170152
--Panteleimon Ntoulis 3170124

library ieee;
use ieee.std_logic_1164.all;

entity cpu is
	port(keyData, fromData, instr : in std_logic_vector(15 downto 0);
			clock, clock2 : in std_logic;
			dataAD, toData, printCode, printData,instructionAD : out std_logic_vector(15 downto 0);
			regOUT : out std_logic_vector(143 downto 0);
			printEnable, keyEnable, DataWriteFlag : out std_logic);
end cpu;

architecture structural of cpu is
	component mux2to1 is
		port(DATAA, DATAB: in std_logic_vector(15 downto 0);
				SEL : in std_logic;
				OUT0 : out std_logic_vector(15 downto 0));
	end component;
	
	component trapUnit is
		port(opCode: in std_logic_vector(3 downto 0);
			EOR : out std_logic);
	end component;
	
	component JRSelector IS
		generic (
			n : integer :=16 
			);
		PORT (--select address that will be sent to PC based on JRopcode
			jumpAD,branchAd,PCP2AD: IN STD_LOGIC_VECTOR(n-1 DOWNTO 0);
			JRopcode: IN STD_LOGIC_VECTOR(1 DOWNTO 0);
			result: OUT STD_LOGIC_VECTOR(n-1 DOWNTO 0)
			);
	END component;
	
	component myOR2 IS
		PORT (in1, in2 : in std_logic;
				out1 : out std_logic);
	END component;
	
	component reg16b IS 
		PORT (Input : IN std_logic_vector(15 DOWNTO 0);
			Enable,Clock : IN STD_LOGIC;
			Output : OUT STD_LOGIC_VECTOR(15 DOWNTO 0)
			);
	END component;
	
	component register_MEM_WB is 
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
	end component;
	
	component Forwarder is
		generic(addr_size : INTEGER := 3);
		port(R1AD, R2AD, RegAD_EXMEM, RegAD_MEMWB : in std_logic_vector(addr_size-1 downto 0);
				S1, S2 : out std_logic_vector(1 downto 0));
	end component;
	
	component register_IF_ID is 
		generic ( 
			n : INTEGER :=16 
			);
			port (
				inPC,inInstruction : IN std_logic_vector(n-1 downto 0);
				clock,IF_Flush,IF_ID_ENABLE : IN std_logic;
				-----------
				outPC,outInstruction : OUT std_logic_vector(n-1 downto 0)
				);
	end component;
	
	component Selector is
		generic( n: INTEGER := 16);
		port(Reg, Memory, WriteBack: in std_logic_vector(n-1 downto 0);
				Operation: in std_logic_vector(1 downto 0);
				Output: out std_logic_vector(n-1 downto 0));
	end component;
	
	component AluControl is 
		port (opCode : IN STD_LOGIC_VECTOR(3 DOWNTO 0);
		func : IN STD_LOGIC_VECTOR(2 DOWNTO 0);
		output :OUT STD_LOGIC_VECTOR(3 DOWNTO 0)
		);
	END component;
	
	component signExtender IS
		generic (
			n : integer :=16;
			k: integer :=6
		  );
		 port ( 
			immediate : in std_logic_vector(k-1 downto 0);
			extended : out std_logic_vector(n-1 downto 0)
			);	  
	  END component;
	
	component myALU is
		port(Input1, Input2: in std_logic_vector(15 downto 0);
				Carryin: in std_logic;
				Operation: in std_logic_vector(3 downto 0);
				Carryout: out std_logic;
				Output: out std_logic_vector(15 downto 0));
	end component;
	
	component myAND2
		port(in1,in2: in std_logic;
				out1: out std_logic);
	end component;
	
	component hazardUnit is
		port(isJR, isJump, wasJump, mustBranch : in std_logic;
				flush, wasJumpOut : out std_logic;
				JRopCode : out std_logic_vector(1 downto 0));
	end component;
	
	component Controller is
		port(opCode : in std_logic_vector(3 downto 0);
				func : in std_logic_vector(2 downto 0);
				flush : in std_logic;
				isMPFC, isJumpD, isReadDigit, isPrintDigit, isR, isLW, isSW, isBranch, isJR : out std_logic);
	end component;
	
	component register_ID_EX is 
		generic (
			n : INTEGER := 16;
			adressSize : INTEGER :=3
			);
			port (
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
	end component;

	
	component register_EX_MEM is 
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
	end component;
	
	component regFile IS 
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
	END component;
	
	--signal signal declaration
begin
	--F0: component port map (...)
end architecture structural;