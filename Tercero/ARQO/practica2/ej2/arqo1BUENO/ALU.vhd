----------------------------------------------------------------------
-- Fichero: ALU.vhd
-- Codigo original de EC: Luis Cayola y Roberto Garcia
--	Codigo modificado de ARQO: Mario Valdemaro, Garcia Roque y Alexandru Costinel Glont
-- Asignatura: ARQO
-- Grupo de Pr�cticas: 1311
-- Pareja: 01
-- Pr�ctica: 1
----------------------------------------------------------------------

library IEEE;
use IEEE.std_logic_1164.ALL;
use IEEE.std_LOGIC_arith.ALL;
use IEEE.std_logic_unsigned.ALL;

entity ALU is
	port (
		Op1 : in std_logic_vector(31 downto 0); -- Operando
		Op2: in std_logic_vector(31 downto 0); -- Operando
		ALUControl: in std_logic_vector(2 downto 0); -- Operacion
		Res : out std_logic_vector(31 downto 0); -- Resultado
		Z : out std_logic -- Bandera
	);
end AlU;

--Definici�n de la ALU
architecture Compleja of ALU is

signal aux,resta, Resaux: std_logic_vector(31 downto 0);
begin

process(Op1,Op2,resta) --Utilizaremos la se�al resta para evaluar el signo en el slt. 
begin
   if Op1(31)='1' and Op2(31)='0' then --Primero quitamos los posibles casos que producir�an un desbordamiento.
      aux<= x"00000001";

   elsif Op1(31)='0' and Op2(31)='1' then
      aux<= x"00000000";
		   elsif resta(31)='1' then
      aux<= x"00000001";
	else aux<= x"00000000";
   end if; 
end process;
		

process (ALUControl, Op1, Op2, aux)
begin
	
   case (ALUControl) is  --Dependiendo de la se�al ALUControl, la ALU realizar� una operaci�n u otra.
		when "000" => Resaux <= Op1 + Op2;
	  
		when "010" => Resaux <= Op1 - Op2;
	  
		when "100" => Resaux <= Op1 and Op2;
 
		when "101" => Resaux <= Op1 or Op2;     

		when "110"=> Resaux <= Op1 xor Op2;
		
		when "011" => Resaux <=Op2(15 downto 0)& x"0000";

		when "111" => if (resta(31)='0')then
			Resaux <=x"00000000";
			else Resaux<=x"00000001";
			end if;
      when others => Resaux <= x"00000000";
        
   end case;
end process;
	

Res<=Resaux;--Con esta auxiliar evitamos tener que comparar una salida (que no se puede leer).
	
resta <= (Op1 - Op2);
end Compleja;

