// This file is NOT part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.

// Given an input R0 that is not 0 and not overflow
// Store -1 in R1 if odd
// Store 0 in R1 if even

D=1 //D register is 1 --- compare last bit
@0//@R0 //A points to R0 address
D=D&M //D=0001 & M[R0]
@1//@R1 //Store the (negative) last bit in R1
M=-D //0->0  1->-1

(END)
	@5//@END
	0;JMP