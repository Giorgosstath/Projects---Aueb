--Stathopoulos Georgios 3170152
--Panteleimon Ntoulis 3170124

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