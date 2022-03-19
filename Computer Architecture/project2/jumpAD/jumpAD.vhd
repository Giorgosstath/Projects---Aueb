--Stathopoulos Georgios 3170152
--Panteleimon Ntoulis 3170124

LIBRARY ieee;
Use ieee.STD_LOGIC_1164.all;
use ieee.numeric_std.all;
ENTITY jumpAD IS
generic (
      n : integer :=16;
	  k: integer :=13
	  );
	  port (
	    jumpADR : in std_logic_vector (k-1 downto 0);
		 instrP2AD : in std_logic_vector (n-1 downto 0);
		 EjumpAD : out std_logic_vector (n-1 downto 0)
		 );
END jumpAD;

Architecture Logicfunc Of jumpAD IS
     signal extended,multed: std_logic_vector(n-1 DOWNTO 0);
begin 
    extended <= (n-1 downto k => jumpADR(k-1)) & (jumpADR);
	 process(instrP2AD)
	 begin
		multed <= extended(n-2 downto 0) & '0';
		EjumpAD <= std_logic_vector( unsigned(multed) + unsigned(instrP2AD));
	end process;
end Logicfunc;