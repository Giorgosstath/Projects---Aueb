-- Stathopoulos Georgios 3170152
-- Ntoulis Panteleimon 3170124

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