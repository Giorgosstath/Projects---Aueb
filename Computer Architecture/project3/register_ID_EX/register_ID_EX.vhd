--Stathopoulos Georgios 3170152
--Panteleimon Ntoulis 3170124

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