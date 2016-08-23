onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate /procesador_tb/Clk
add wave -noupdate /procesador_tb/Reset
add wave -noupdate /procesador_tb/I_Addr
add wave -noupdate /procesador_tb/I_DataIn
add wave -noupdate /procesador_tb/D_Addr
add wave -noupdate /procesador_tb/D_WrEn
add wave -noupdate /procesador_tb/D_DataOut
add wave -noupdate /procesador_tb/D_DataIn
add wave -noupdate -radix decimal /procesador_tb/Clk
add wave -noupdate -radix decimal /procesador_tb/Reset
add wave -noupdate -radix hexadecimal -childformat {{/procesador_tb/I_Addr(31) -radix hexadecimal} {/procesador_tb/I_Addr(30) -radix hexadecimal} {/procesador_tb/I_Addr(29) -radix hexadecimal} {/procesador_tb/I_Addr(28) -radix hexadecimal} {/procesador_tb/I_Addr(27) -radix hexadecimal} {/procesador_tb/I_Addr(26) -radix hexadecimal} {/procesador_tb/I_Addr(25) -radix hexadecimal} {/procesador_tb/I_Addr(24) -radix hexadecimal} {/procesador_tb/I_Addr(23) -radix hexadecimal} {/procesador_tb/I_Addr(22) -radix hexadecimal} {/procesador_tb/I_Addr(21) -radix hexadecimal} {/procesador_tb/I_Addr(20) -radix hexadecimal} {/procesador_tb/I_Addr(19) -radix hexadecimal} {/procesador_tb/I_Addr(18) -radix hexadecimal} {/procesador_tb/I_Addr(17) -radix hexadecimal} {/procesador_tb/I_Addr(16) -radix hexadecimal} {/procesador_tb/I_Addr(15) -radix hexadecimal} {/procesador_tb/I_Addr(14) -radix hexadecimal} {/procesador_tb/I_Addr(13) -radix hexadecimal} {/procesador_tb/I_Addr(12) -radix hexadecimal} {/procesador_tb/I_Addr(11) -radix hexadecimal} {/procesador_tb/I_Addr(10) -radix hexadecimal} {/procesador_tb/I_Addr(9) -radix hexadecimal} {/procesador_tb/I_Addr(8) -radix hexadecimal} {/procesador_tb/I_Addr(7) -radix hexadecimal} {/procesador_tb/I_Addr(6) -radix hexadecimal} {/procesador_tb/I_Addr(5) -radix hexadecimal} {/procesador_tb/I_Addr(4) -radix hexadecimal} {/procesador_tb/I_Addr(3) -radix hexadecimal} {/procesador_tb/I_Addr(2) -radix hexadecimal} {/procesador_tb/I_Addr(1) -radix hexadecimal} {/procesador_tb/I_Addr(0) -radix hexadecimal}} -subitemconfig {/procesador_tb/I_Addr(31) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(30) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(29) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(28) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(27) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(26) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(25) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(24) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(23) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(22) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(21) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(20) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(19) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(18) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(17) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(16) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(15) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(14) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(13) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(12) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(11) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(10) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(9) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(8) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(7) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(6) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(5) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(4) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(3) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(2) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(1) {-height 15 -radix hexadecimal} /procesador_tb/I_Addr(0) {-height 15 -radix hexadecimal}} /procesador_tb/I_Addr
add wave -noupdate -radix decimal -childformat {{/procesador_tb/I_DataIn(31) -radix decimal} {/procesador_tb/I_DataIn(30) -radix decimal} {/procesador_tb/I_DataIn(29) -radix decimal} {/procesador_tb/I_DataIn(28) -radix decimal} {/procesador_tb/I_DataIn(27) -radix decimal} {/procesador_tb/I_DataIn(26) -radix decimal} {/procesador_tb/I_DataIn(25) -radix decimal} {/procesador_tb/I_DataIn(24) -radix decimal} {/procesador_tb/I_DataIn(23) -radix decimal} {/procesador_tb/I_DataIn(22) -radix decimal} {/procesador_tb/I_DataIn(21) -radix decimal} {/procesador_tb/I_DataIn(20) -radix decimal} {/procesador_tb/I_DataIn(19) -radix decimal} {/procesador_tb/I_DataIn(18) -radix decimal} {/procesador_tb/I_DataIn(17) -radix decimal} {/procesador_tb/I_DataIn(16) -radix decimal} {/procesador_tb/I_DataIn(15) -radix decimal} {/procesador_tb/I_DataIn(14) -radix decimal} {/procesador_tb/I_DataIn(13) -radix decimal} {/procesador_tb/I_DataIn(12) -radix decimal} {/procesador_tb/I_DataIn(11) -radix decimal} {/procesador_tb/I_DataIn(10) -radix decimal} {/procesador_tb/I_DataIn(9) -radix decimal} {/procesador_tb/I_DataIn(8) -radix decimal} {/procesador_tb/I_DataIn(7) -radix decimal} {/procesador_tb/I_DataIn(6) -radix decimal} {/procesador_tb/I_DataIn(5) -radix decimal} {/procesador_tb/I_DataIn(4) -radix decimal} {/procesador_tb/I_DataIn(3) -radix decimal} {/procesador_tb/I_DataIn(2) -radix decimal} {/procesador_tb/I_DataIn(1) -radix decimal} {/procesador_tb/I_DataIn(0) -radix decimal}} -subitemconfig {/procesador_tb/I_DataIn(31) {-height 15 -radix decimal} /procesador_tb/I_DataIn(30) {-height 15 -radix decimal} /procesador_tb/I_DataIn(29) {-height 15 -radix decimal} /procesador_tb/I_DataIn(28) {-height 15 -radix decimal} /procesador_tb/I_DataIn(27) {-height 15 -radix decimal} /procesador_tb/I_DataIn(26) {-height 15 -radix decimal} /procesador_tb/I_DataIn(25) {-height 15 -radix decimal} /procesador_tb/I_DataIn(24) {-height 15 -radix decimal} /procesador_tb/I_DataIn(23) {-height 15 -radix decimal} /procesador_tb/I_DataIn(22) {-height 15 -radix decimal} /procesador_tb/I_DataIn(21) {-height 15 -radix decimal} /procesador_tb/I_DataIn(20) {-height 15 -radix decimal} /procesador_tb/I_DataIn(19) {-height 15 -radix decimal} /procesador_tb/I_DataIn(18) {-height 15 -radix decimal} /procesador_tb/I_DataIn(17) {-height 15 -radix decimal} /procesador_tb/I_DataIn(16) {-height 15 -radix decimal} /procesador_tb/I_DataIn(15) {-height 15 -radix decimal} /procesador_tb/I_DataIn(14) {-height 15 -radix decimal} /procesador_tb/I_DataIn(13) {-height 15 -radix decimal} /procesador_tb/I_DataIn(12) {-height 15 -radix decimal} /procesador_tb/I_DataIn(11) {-height 15 -radix decimal} /procesador_tb/I_DataIn(10) {-height 15 -radix decimal} /procesador_tb/I_DataIn(9) {-height 15 -radix decimal} /procesador_tb/I_DataIn(8) {-height 15 -radix decimal} /procesador_tb/I_DataIn(7) {-height 15 -radix decimal} /procesador_tb/I_DataIn(6) {-height 15 -radix decimal} /procesador_tb/I_DataIn(5) {-height 15 -radix decimal} /procesador_tb/I_DataIn(4) {-height 15 -radix decimal} /procesador_tb/I_DataIn(3) {-height 15 -radix decimal} /procesador_tb/I_DataIn(2) {-height 15 -radix decimal} /procesador_tb/I_DataIn(1) {-height 15 -radix decimal} /procesador_tb/I_DataIn(0) {-height 15 -radix decimal}} /procesador_tb/I_DataIn
add wave -noupdate -radix decimal -childformat {{/procesador_tb/D_Addr(31) -radix decimal} {/procesador_tb/D_Addr(30) -radix decimal} {/procesador_tb/D_Addr(29) -radix decimal} {/procesador_tb/D_Addr(28) -radix decimal} {/procesador_tb/D_Addr(27) -radix decimal} {/procesador_tb/D_Addr(26) -radix decimal} {/procesador_tb/D_Addr(25) -radix decimal} {/procesador_tb/D_Addr(24) -radix decimal} {/procesador_tb/D_Addr(23) -radix decimal} {/procesador_tb/D_Addr(22) -radix decimal} {/procesador_tb/D_Addr(21) -radix decimal} {/procesador_tb/D_Addr(20) -radix decimal} {/procesador_tb/D_Addr(19) -radix decimal} {/procesador_tb/D_Addr(18) -radix decimal} {/procesador_tb/D_Addr(17) -radix decimal} {/procesador_tb/D_Addr(16) -radix decimal} {/procesador_tb/D_Addr(15) -radix decimal} {/procesador_tb/D_Addr(14) -radix decimal} {/procesador_tb/D_Addr(13) -radix decimal} {/procesador_tb/D_Addr(12) -radix decimal} {/procesador_tb/D_Addr(11) -radix decimal} {/procesador_tb/D_Addr(10) -radix decimal} {/procesador_tb/D_Addr(9) -radix decimal} {/procesador_tb/D_Addr(8) -radix decimal} {/procesador_tb/D_Addr(7) -radix decimal} {/procesador_tb/D_Addr(6) -radix decimal} {/procesador_tb/D_Addr(5) -radix decimal} {/procesador_tb/D_Addr(4) -radix decimal} {/procesador_tb/D_Addr(3) -radix decimal} {/procesador_tb/D_Addr(2) -radix decimal} {/procesador_tb/D_Addr(1) -radix decimal} {/procesador_tb/D_Addr(0) -radix decimal}} -subitemconfig {/procesador_tb/D_Addr(31) {-height 15 -radix decimal} /procesador_tb/D_Addr(30) {-height 15 -radix decimal} /procesador_tb/D_Addr(29) {-height 15 -radix decimal} /procesador_tb/D_Addr(28) {-height 15 -radix decimal} /procesador_tb/D_Addr(27) {-height 15 -radix decimal} /procesador_tb/D_Addr(26) {-height 15 -radix decimal} /procesador_tb/D_Addr(25) {-height 15 -radix decimal} /procesador_tb/D_Addr(24) {-height 15 -radix decimal} /procesador_tb/D_Addr(23) {-height 15 -radix decimal} /procesador_tb/D_Addr(22) {-height 15 -radix decimal} /procesador_tb/D_Addr(21) {-height 15 -radix decimal} /procesador_tb/D_Addr(20) {-height 15 -radix decimal} /procesador_tb/D_Addr(19) {-height 15 -radix decimal} /procesador_tb/D_Addr(18) {-height 15 -radix decimal} /procesador_tb/D_Addr(17) {-height 15 -radix decimal} /procesador_tb/D_Addr(16) {-height 15 -radix decimal} /procesador_tb/D_Addr(15) {-height 15 -radix decimal} /procesador_tb/D_Addr(14) {-height 15 -radix decimal} /procesador_tb/D_Addr(13) {-height 15 -radix decimal} /procesador_tb/D_Addr(12) {-height 15 -radix decimal} /procesador_tb/D_Addr(11) {-height 15 -radix decimal} /procesador_tb/D_Addr(10) {-height 15 -radix decimal} /procesador_tb/D_Addr(9) {-height 15 -radix decimal} /procesador_tb/D_Addr(8) {-height 15 -radix decimal} /procesador_tb/D_Addr(7) {-height 15 -radix decimal} /procesador_tb/D_Addr(6) {-height 15 -radix decimal} /procesador_tb/D_Addr(5) {-height 15 -radix decimal} /procesador_tb/D_Addr(4) {-height 15 -radix decimal} /procesador_tb/D_Addr(3) {-height 15 -radix decimal} /procesador_tb/D_Addr(2) {-height 15 -radix decimal} /procesador_tb/D_Addr(1) {-height 15 -radix decimal} /procesador_tb/D_Addr(0) {-height 15 -radix decimal}} /procesador_tb/D_Addr
add wave -noupdate -radix decimal /procesador_tb/D_WrEn
add wave -noupdate -radix decimal /procesador_tb/D_DataOut
add wave -noupdate -radix decimal /procesador_tb/D_DataIn
add wave -noupdate -radix decimal -childformat {{/procesador_tb/UUT/miregs/regs(0) -radix decimal} {/procesador_tb/UUT/miregs/regs(1) -radix decimal} {/procesador_tb/UUT/miregs/regs(2) -radix decimal} {/procesador_tb/UUT/miregs/regs(3) -radix decimal} {/procesador_tb/UUT/miregs/regs(4) -radix decimal} {/procesador_tb/UUT/miregs/regs(5) -radix decimal} {/procesador_tb/UUT/miregs/regs(6) -radix decimal} {/procesador_tb/UUT/miregs/regs(7) -radix decimal} {/procesador_tb/UUT/miregs/regs(8) -radix decimal} {/procesador_tb/UUT/miregs/regs(9) -radix decimal} {/procesador_tb/UUT/miregs/regs(10) -radix decimal} {/procesador_tb/UUT/miregs/regs(11) -radix decimal} {/procesador_tb/UUT/miregs/regs(12) -radix decimal} {/procesador_tb/UUT/miregs/regs(13) -radix decimal} {/procesador_tb/UUT/miregs/regs(14) -radix decimal} {/procesador_tb/UUT/miregs/regs(15) -radix decimal} {/procesador_tb/UUT/miregs/regs(16) -radix decimal} {/procesador_tb/UUT/miregs/regs(17) -radix decimal} {/procesador_tb/UUT/miregs/regs(18) -radix decimal} {/procesador_tb/UUT/miregs/regs(19) -radix decimal} {/procesador_tb/UUT/miregs/regs(20) -radix decimal} {/procesador_tb/UUT/miregs/regs(21) -radix decimal} {/procesador_tb/UUT/miregs/regs(22) -radix decimal} {/procesador_tb/UUT/miregs/regs(23) -radix decimal} {/procesador_tb/UUT/miregs/regs(24) -radix decimal} {/procesador_tb/UUT/miregs/regs(25) -radix decimal} {/procesador_tb/UUT/miregs/regs(26) -radix decimal} {/procesador_tb/UUT/miregs/regs(27) -radix decimal} {/procesador_tb/UUT/miregs/regs(28) -radix decimal} {/procesador_tb/UUT/miregs/regs(29) -radix decimal} {/procesador_tb/UUT/miregs/regs(30) -radix decimal} {/procesador_tb/UUT/miregs/regs(31) -radix decimal}} -subitemconfig {/procesador_tb/UUT/miregs/regs(0) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(1) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(2) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(3) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(4) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(5) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(6) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(7) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(8) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(9) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(10) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(11) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(12) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(13) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(14) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(15) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(16) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(17) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(18) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(19) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(20) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(21) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(22) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(23) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(24) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(25) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(26) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(27) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(28) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(29) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(30) {-height 15 -radix decimal} /procesador_tb/UUT/miregs/regs(31) {-height 15 -radix decimal}} /procesador_tb/UUT/miregs/regs
add wave -noupdate /procesador_tb/UUT/auxPc
add wave -noupdate /procesador_tb/UUT/auxEq
add wave -noupdate -divider registros
add wave -noupdate /procesador_tb/UUT/miregs/Clk
add wave -noupdate /procesador_tb/UUT/miregs/Reset
add wave -noupdate /procesador_tb/UUT/miregs/A1
add wave -noupdate /procesador_tb/UUT/miregs/Rd1
add wave -noupdate /procesador_tb/UUT/miregs/A2
add wave -noupdate /procesador_tb/UUT/miregs/Rd2
add wave -noupdate /procesador_tb/UUT/miregs/A3
add wave -noupdate /procesador_tb/UUT/miregs/Wd3
add wave -noupdate /procesador_tb/UUT/miregs/We3
add wave -noupdate -childformat {{/procesador_tb/UUT/miregs/regs(0) -radix decimal} {/procesador_tb/UUT/miregs/regs(1) -radix decimal} {/procesador_tb/UUT/miregs/regs(2) -radix decimal} {/procesador_tb/UUT/miregs/regs(3) -radix decimal} {/procesador_tb/UUT/miregs/regs(4) -radix decimal} {/procesador_tb/UUT/miregs/regs(5) -radix decimal} {/procesador_tb/UUT/miregs/regs(6) -radix decimal} {/procesador_tb/UUT/miregs/regs(7) -radix decimal} {/procesador_tb/UUT/miregs/regs(8) -radix decimal} {/procesador_tb/UUT/miregs/regs(9) -radix decimal} {/procesador_tb/UUT/miregs/regs(10) -radix decimal} {/procesador_tb/UUT/miregs/regs(11) -radix decimal} {/procesador_tb/UUT/miregs/regs(12) -radix decimal} {/procesador_tb/UUT/miregs/regs(13) -radix decimal} {/procesador_tb/UUT/miregs/regs(14) -radix decimal} {/procesador_tb/UUT/miregs/regs(15) -radix decimal} {/procesador_tb/UUT/miregs/regs(16) -radix decimal} {/procesador_tb/UUT/miregs/regs(17) -radix decimal} {/procesador_tb/UUT/miregs/regs(18) -radix decimal} {/procesador_tb/UUT/miregs/regs(19) -radix decimal} {/procesador_tb/UUT/miregs/regs(20) -radix decimal} {/procesador_tb/UUT/miregs/regs(21) -radix decimal} {/procesador_tb/UUT/miregs/regs(22) -radix decimal} {/procesador_tb/UUT/miregs/regs(23) -radix decimal} {/procesador_tb/UUT/miregs/regs(24) -radix decimal} {/procesador_tb/UUT/miregs/regs(25) -radix decimal} {/procesador_tb/UUT/miregs/regs(26) -radix decimal} {/procesador_tb/UUT/miregs/regs(27) -radix decimal} {/procesador_tb/UUT/miregs/regs(28) -radix decimal} {/procesador_tb/UUT/miregs/regs(29) -radix decimal} {/procesador_tb/UUT/miregs/regs(30) -radix decimal} {/procesador_tb/UUT/miregs/regs(31) -radix decimal}} -expand -subitemconfig {/procesador_tb/UUT/miregs/regs(0) {-radix decimal} /procesador_tb/UUT/miregs/regs(1) {-radix decimal} /procesador_tb/UUT/miregs/regs(2) {-radix decimal} /procesador_tb/UUT/miregs/regs(3) {-radix decimal} /procesador_tb/UUT/miregs/regs(4) {-radix decimal} /procesador_tb/UUT/miregs/regs(5) {-radix decimal} /procesador_tb/UUT/miregs/regs(6) {-radix decimal} /procesador_tb/UUT/miregs/regs(7) {-radix decimal} /procesador_tb/UUT/miregs/regs(8) {-radix decimal} /procesador_tb/UUT/miregs/regs(9) {-radix decimal} /procesador_tb/UUT/miregs/regs(10) {-radix decimal} /procesador_tb/UUT/miregs/regs(11) {-radix decimal} /procesador_tb/UUT/miregs/regs(12) {-radix decimal} /procesador_tb/UUT/miregs/regs(13) {-radix decimal} /procesador_tb/UUT/miregs/regs(14) {-radix decimal} /procesador_tb/UUT/miregs/regs(15) {-radix decimal} /procesador_tb/UUT/miregs/regs(16) {-radix decimal} /procesador_tb/UUT/miregs/regs(17) {-radix decimal} /procesador_tb/UUT/miregs/regs(18) {-radix decimal} /procesador_tb/UUT/miregs/regs(19) {-radix decimal} /procesador_tb/UUT/miregs/regs(20) {-radix decimal} /procesador_tb/UUT/miregs/regs(21) {-radix decimal} /procesador_tb/UUT/miregs/regs(22) {-radix decimal} /procesador_tb/UUT/miregs/regs(23) {-radix decimal} /procesador_tb/UUT/miregs/regs(24) {-radix decimal} /procesador_tb/UUT/miregs/regs(25) {-radix decimal} /procesador_tb/UUT/miregs/regs(26) {-radix decimal} /procesador_tb/UUT/miregs/regs(27) {-radix decimal} /procesador_tb/UUT/miregs/regs(28) {-radix decimal} /procesador_tb/UUT/miregs/regs(29) {-radix decimal} /procesador_tb/UUT/miregs/regs(30) {-radix decimal} /procesador_tb/UUT/miregs/regs(31) {-radix decimal}} /procesador_tb/UUT/miregs/regs
add wave -noupdate -divider alu
add wave -noupdate /procesador_tb/UUT/miALU/Op1
add wave -noupdate /procesador_tb/UUT/miALU/Op2
add wave -noupdate /procesador_tb/UUT/miALU/ALUControl
add wave -noupdate /procesador_tb/UUT/miALU/Res
add wave -noupdate /procesador_tb/UUT/miALU/Z
add wave -noupdate /procesador_tb/UUT/miALU/aux
add wave -noupdate /procesador_tb/UUT/miALU/resta
add wave -noupdate /procesador_tb/UUT/miALU/Resaux
add wave -noupdate -divider Control
add wave -noupdate -radix binary /procesador_tb/UUT/miUC/OPCode
add wave -noupdate /procesador_tb/UUT/miUC/Funct
add wave -noupdate /procesador_tb/UUT/miUC/MemToReg
add wave -noupdate /procesador_tb/UUT/miUC/MemWrite
add wave -noupdate /procesador_tb/UUT/miUC/Branch
add wave -noupdate /procesador_tb/UUT/miUC/ALUControlUC
add wave -noupdate /procesador_tb/UUT/miUC/ALUSrc
add wave -noupdate /procesador_tb/UUT/miUC/RegDest
add wave -noupdate /procesador_tb/UUT/miUC/RegWrite
add wave -noupdate /procesador_tb/UUT/miUC/Jump
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {1683389 ps} 0}
quietly wave cursor active 1
configure wave -namecolwidth 255
configure wave -valuecolwidth 100
configure wave -justifyvalue left
configure wave -signalnamewidth 0
configure wave -snapdistance 10
configure wave -datasetprefix 0
configure wave -rowmargin 4
configure wave -childrowmargin 2
configure wave -gridoffset 0
configure wave -gridperiod 1
configure wave -griddelta 40
configure wave -timeline 0
configure wave -timelineunits ns
update
WaveRestoreZoom {1535637 ps} {1888675 ps}
