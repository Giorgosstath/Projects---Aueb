--Stathopoulos Georgios 3170152
--Panteleimon Ntoulis 3170124

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