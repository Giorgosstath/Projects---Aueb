--Stathopoulos Georgios 3170142
--Panteleimon Ntoulis 3170124

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