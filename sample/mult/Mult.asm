// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Start R2 at 0.
@2//@R2
M=0

// Jump into the first STEP if R0 > 0.
@0//@R0
D=M
@8//@STEP
D;JGT

// If it didn't jump, go to END.
@19//@END
0;JMP

// Adds R1 to R2 and removes 1 from R0.
// If R0 is more than 0 we step again.
(STEP)
    // Get R2.
    @2//@R2
    D=M

    // Add R1 to it.
    @1//@R1
    D=D+M

    // And write the result back to R2.
    @2//@R2
    M=D

    // Reduce R0 by 1.
    @0//@R0
    D=M-1
    M=D

    // If R0 is still > 0 then loop.
    @8//@STEP
    D;JGT

//(END)
    @19//@END
    0;JMP