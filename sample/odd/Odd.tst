// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.

// Tests the Mult program, designed to compute R2 = R0 * R1.
// Tests the program by having it multiply several sets of
// R0 and R1 values.

load Odd.asm,
output-file Odd.out,
compare-to Odd.cmp,
output-list RAM[0]%D2.6.2 RAM[1]%D2.6.2;

set RAM[0] 0,   // Sets R0 to some input values
set RAM[1] 100;
repeat 20 {
  ticktock;
}
set RAM[0] 0,   // Restores R0 in case the program changed them
output;

set PC 0,
set RAM[0] 1,   // Sets R0 and R1 to some input values
set RAM[1] 100,
repeat 30 {
  ticktock;
}
set RAM[0] 1,   // Restores R0 and R1 in case the program changed them
output;

set PC 0,
set RAM[0] 113,   // Sets R0 and R1 to some input values
set RAM[1] 100,
repeat 30 {
  ticktock;
}
set RAM[0] 113,   // Restores R0 and R1 in case the program changed them
output;

set PC 0,
set RAM[0] 3,   // Sets R0 and R1 to some input values
set RAM[1] 100,
repeat 30 {
  ticktock;
}
set RAM[0] 3,   // Restores R0 and R1 in case the program changed them
output;

set PC 0,
set RAM[0] 2,   // Sets R0 and R1 to some input values
set RAM[1] 100,
repeat 30 {
  ticktock;
}
set RAM[0] 2,   // Restores R0 and R1 in case the program changed them
output;

set PC 0,
set RAM[0] 6,   // Sets R0 to some input values
set RAM[1] 100,
repeat 30 {
  ticktock;
}
set RAM[0] 6,   // Restores R0 in case the program changed them
output;

set PC 0,
set RAM[0] 1734,   // Sets R0 to some input values
set RAM[1] 100,
repeat 30 {
  ticktock;
}
set RAM[0] 1734,   // Restores R0 in case the program changed them
output;
