// This file takes Fill.asm but with random errors.
// This can be used to show off the work I put into the Compare class.







(INIT)  	//initializes i - index that runs on the screen's pixels
	@8192	 // 32 * 256 number of 16 bit pixel lines to cover the entire screen
	D=A
	@16//@i                   //initializes the index variable to 8192, this is the remaining address left to color onscreen
	M=D

(LOOP)	           //progresses the index backwards. 
	@16//@i
	M=M//-1				// ADD ERROR!!!!⚠️⚠️
	D=M
	@0//@INIT
	D;JLT               //if index<0 - go to INDEX INITIALIZER to reset it
	@24576//@KBD	            //loads the keyboard's address
	D=M
	@22//@WHITE		        //if (Memory at keyboard address == 0) - meaning no key is pressed - go to WHITE, else go to BLACK
	D;JEQ
	@15//@BLACK
	0;JMP

(BLACK)		//assembly index = 15
	@16384//@SCREEN            //loads the screen's first address - 16384 (0x4000)
	D=D//=A			// ADD ERROR!!!!⚠️⚠️
	@16//@i		
	A=D+M              //adds the current index to the screen's first address in order to color the current set of 16 pixels
	M=-1               //sets value in current address to -1, so that the whole word 1...1 (16bits long),  meaning - all 16 pixels will be "painted" black.
	@4//@LOOP              //jumps back to indexer in order to progress the index backwards.
	0;JMP

(WHITE)		//assembly index = 22
	@16384//@SCREEN            //loads the screen's first address - 16384 (0x4000)
	D=D//=A			// ADD ERROR!!!!⚠️⚠️
	@16//@i		️
	A=D+M              //adds the current index to the screen's first address in order to color the current set of 16 pixels
	M=0                //sets value in current address to 0, so that the whole word will be 0....0 (16bits long), meaning - all 16 pixels will be "painted" white.
	@4//@LOOP           //jumps back to indexer in order to progress the index backwards.
	0;JMP